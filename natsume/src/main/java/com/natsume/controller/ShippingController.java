package com.natsume.controller;

import com.natsume.entity.User;
import com.natsume.form.ShippingForm;
import com.natsume.service.ShippingService;
import com.natsume.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.natsume.consts.NatsumeConst.CURRENT_USER;

@RestController
public class ShippingController {

	@Autowired
	private ShippingService shippingService;

	@PostMapping("/shippings")
	public ResponseVo add(@Valid @RequestBody ShippingForm form, HttpSession session) {
		User user = (User) session.getAttribute(CURRENT_USER);
		return shippingService.add(user.getId(), form);
	}

	@DeleteMapping("/shippings/{shippingId}")
	public ResponseVo delete(@PathVariable Integer shippingId, HttpSession session) {
		User user = (User) session.getAttribute(CURRENT_USER);
		return shippingService.delete(user.getId(), shippingId);
	}

	@PutMapping("/shippings/{shippingId}")
	public ResponseVo update(@PathVariable Integer shippingId,
	                         @Valid @RequestBody ShippingForm form,
	                         HttpSession session) {
		User user = (User) session.getAttribute(CURRENT_USER);
		return shippingService.update(user.getId(), shippingId, form);
	}

	@GetMapping("/shippings")
	public ResponseVo list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
	                       @RequestParam(required = false, defaultValue = "10") Integer pageSize,
	                       HttpSession session) {
		User user = (User) session.getAttribute(CURRENT_USER);
		return shippingService.list(user.getId(), pageNum, pageSize);
	}
}
