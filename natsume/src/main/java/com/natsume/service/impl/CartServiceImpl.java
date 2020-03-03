package com.natsume.service.impl;

import com.alibaba.fastjson.JSON;
import com.natsume.entity.Cart;
import com.natsume.entity.Product;
import com.natsume.form.CartAddForm;
import com.natsume.form.CartUpdateForm;
import com.natsume.mapper.ProductMapper;
import com.natsume.service.CartService;
import com.natsume.vo.CartProductVo;
import com.natsume.vo.CartVo;
import com.natsume.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.natsume.consts.NatsumeConst.CART_REDIS_KEY_TEMPLATE;
import static com.natsume.enums.ProductStatusEnum.ON_SALE;
import static com.natsume.enums.ResponseEnum.*;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public ResponseVo<CartVo> add(Integer uId, CartAddForm form) {

		Integer quantity = 1;

		Product product = productMapper.selectByPrimaryKey(form.getProductId());

		//判断商品是否存在
		if (product == null) {
			return ResponseVo.error(PRODUCT_NOT_EXIST);
		}

		//商品是否正常在售
		if (!product.getStatus().equals(ON_SALE.getCode())) {
			return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
		}

		//商品库存是否充足
		if (product.getStock() <= 0) {
			return ResponseVo.error(PRODUCT_STOCK_ERROR);
		}

		//写入redis
		//key: cart_1
		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uId);
		String value = opsForHash.get(redisKey, String.valueOf(product.getId()));

		Cart cart;
		if (StringUtils.isEmpty(value)) {
			//没有该商品
			cart = new Cart(product.getId(), quantity, form.getSelected());
		} else {
			//已经有了，数量 +1
			cart = JSON.parseObject(value, Cart.class);
			cart.setQuantity(cart.getQuantity() + 1);
		}

		opsForHash.put(redisKey, String.valueOf(product.getId()), JSON.toJSONString(cart));

		return list(uId);
	}

	@Override
	public ResponseVo<CartVo> list(Integer uId) {

		CartVo cartVo = new CartVo();

		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uId);

		Map<String, String> entries = opsForHash.entries(redisKey);

		boolean selectedAll = true;
		Integer cartTotalQuantity = 0;
		BigDecimal cartTotalPrice = BigDecimal.ZERO;
		List<CartProductVo> cartProductVos = new ArrayList<>();
		for (Map.Entry<String, String> entry : entries.entrySet()) {
			Integer productId = Integer.valueOf(entry.getKey());
			Cart cart = JSON.parseObject(entry.getValue(), Cart.class);
			//todo:需要优化，使用 mysql 里的 in
			Product product = productMapper.selectByPrimaryKey(productId);

			if (product != null) {
				CartProductVo cartProductVo = new CartProductVo(productId,
						cart.getQuantity(),
						product.getName(),
						product.getSubtitle(),
						product.getMainImage(),
						product.getPrice(),
						product.getStatus(),
						product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
						product.getStock(),
						cart.getProductSelected()
				);
				cartProductVos.add(cartProductVo);

				if (!cart.getProductSelected()) {
					selectedAll = false;
				}
				//计算总价，只计算选中的
				if (cart.getProductSelected()) {
					cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
				}
			}

			cartTotalQuantity += cart.getQuantity();
		}
		cartVo.setCartProductVoList(cartProductVos);
		//有一个没选中，就不叫全选
		cartVo.setSelectedAll(selectedAll);
		cartVo.setCartTotalPrice(cartTotalPrice);
		cartVo.setCartTotalQuantity(cartTotalQuantity);
		return ResponseVo.success(cartVo);
	}

	@Override
	public ResponseVo<CartVo> update(Integer uId, Integer productId, CartUpdateForm form) {

		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uId);
		String value = opsForHash.get(redisKey, String.valueOf(productId));

		if (StringUtils.isEmpty(value)) {
			//没有该商品, 报错
			return ResponseVo.error(CART_PRODUCT_NOT_EXIST);
		}
		//已经有了, 修改内容
		Cart cart = JSON.parseObject(value, Cart.class);
		if (form.getQuantity() != null && form.getQuantity() >= 0) {
			cart.setQuantity(form.getQuantity());
		}

		if (form.getSelected() != null) {
			cart.setProductSelected(form.getSelected());
		}

		opsForHash.put(redisKey, String.valueOf(productId), JSON.toJSONString(cart));

		return list(uId);
	}

	@Override
	public ResponseVo<CartVo> delete(Integer uId, Integer productId) {
		String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uId);

		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String value = opsForHash.get(redisKey, String.valueOf(productId));

		if (StringUtils.isEmpty(value)) {
			//没有该商品, 报错
			return ResponseVo.error(CART_PRODUCT_NOT_EXIST);
		}
		//已经有了, 修改内容

		opsForHash.delete(redisKey, String.valueOf(productId));

		return list(uId);
	}

	@Override
	public ResponseVo<CartVo> selectAll(Integer uId) {
		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uId);
		listForCart(uId).forEach(cart -> {
			cart.setProductSelected(true);
			opsForHash.put(redisKey, String.valueOf(cart.getProductId()), JSON.toJSONString(cart));
		});

		return list(uId);
	}

	@Override
	public ResponseVo<CartVo> unSelectAll(Integer uId) {
		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uId);
		listForCart(uId).forEach(cart -> {
			cart.setProductSelected(false);
			opsForHash.put(redisKey, String.valueOf(cart.getProductId()), JSON.toJSONString(cart));
		});

		return list(uId);
	}

	@Override
	public ResponseVo<Integer> sum(Integer uId) {
		Integer sum = listForCart(uId).stream()
				.map(Cart::getQuantity)
				.reduce(0, Integer::sum);
		return ResponseVo.success(sum);
	}

	public List<Cart> listForCart(Integer uId) {
		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uId);

		Map<String, String> entries = opsForHash.entries(redisKey);

		List<Cart> carts = new ArrayList<>();

		for (Map.Entry<String, String> entry : entries.entrySet()) {
			carts.add(JSON.parseObject(entry.getValue(), Cart.class));
		}

		return carts;
	}
}
