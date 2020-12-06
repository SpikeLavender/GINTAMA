package com.natsumes.wezard.entity.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
public class CommentForm implements Serializable {

    private static final long serialVersionUID = -6909110934243222416L;
    @NotNull
    private Integer userId;

    @NotNull
    private Integer productId;

    private String username;

    @NotBlank
    private String content;

    private String avatarUrl;

    private Integer giveLike;

    private Integer status;

    private Integer replyId;

    private Integer role;
}
