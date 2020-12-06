package com.natsumes.wezard.service;

import com.github.pagehelper.PageInfo;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.CommentForm;
import com.natsumes.wezard.pojo.Comment;

public interface CommentService {

    Response addComment(CommentForm commentForm);

    Response deleteComment(Integer id);

    Response<PageInfo> selectComment(Integer userId, Integer productId, Integer pageNum, Integer pageSize);

    Response updateComment(Comment comment);
}
