package com.natsumes.form;

import lombok.Data;

@Data
public class CartUpdateForm {

	private Integer quantity; //非必填

	private Boolean selected; //非必填

}
