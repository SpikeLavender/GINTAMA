package com.natsume.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
	/**
	 * 成功状态码
	 */
	SUCCESS(200, "成功"),
	/**
	 * 参数为空
	 */
	PARAM_ERROR(400001, "参数错误"),

	USERNAME_EXIST(400002, "用户名已注册"),

	EMAIL_EXIST(400003, "邮箱已注册"),

	USERNAME_OR_PASSWORD_ERROR(403001, "用户名或密码错误"),

	NEED_LOGIN(403002, "用户未登录, 请先登录"),

	PRODUCT_OFF_SALE_OR_DELETE(405001, "商品已下架或删除"),

	PRODUCT_NOT_EXIST(405002, "商品不存在"),

	PRODUCT_STOCK_ERROR(405003, "库存不正确"),

	CART_PRODUCT_NOT_EXIST(405004, "购物车无此商品"),

	DELETE_SHIPPING_FAIL(405006, "删除收货地址失败"),

	SHIPPING_NOT_EXIST(405007, "删除收货地址失败"),

	CART_SELECTED_IS_EMPTY(405008, "请选择商品后下单"),

	ORDER_NOT_EXIST(405009, "订单不存在"),

	ORDER_STATUS_ERROR(405010, "订单状态有误"),

	SYSTEM_ERROR(500, "服务端错误"),

	;

	/**
	 * 状态码
	 */
	private Integer code;
	/**
	 * 异常信息
	 */
	private String desc;

	/**
	 * 异常枚举信息
	 *
	 * @param code 状态码
	 * @param desc  信息
	 */
	ResponseEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
