package com.natsume.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderCreateForm {

	@NotNull
	private Integer shippingId;

}
