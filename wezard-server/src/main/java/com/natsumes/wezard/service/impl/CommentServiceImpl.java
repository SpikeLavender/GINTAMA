package com.natsumes.wezard.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.CommentForm;
import com.natsumes.wezard.enums.ResponseEnum;
import com.natsumes.wezard.mapper.CommentMapper;
import com.natsumes.wezard.pojo.Comment;
import com.natsumes.wezard.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Response addComment(CommentForm commentForm) {

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentForm, comment);

        int i = commentMapper.insert(comment);
        if (i <= 0) {
            return Response.error(ResponseEnum.SYSTEM_ERROR, "添加评论失败");
        }
        return Response.success();
    }

    @Override
    public Response deleteComment(Integer id) {

        commentMapper.deleteByPrimaryKey(id);
        return Response.success();
    }

    @Override
    public Response<PageInfo> selectComment(Integer userId, Integer productId, Integer pageNum, Integer pageSize) {
        Comment comment = new Comment();
        comment.setProductId(productId);
        comment.setUserId(userId);
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectSelective(comment);
        PageInfo pageInfo = new PageInfo<>(comments);
        //todo: 做一些 id 联查的处理等
        return Response.success(pageInfo);
    }

    @Override
    public Response updateComment(Comment comment) {
        int i = commentMapper.updateByPrimaryKeySelective(comment);
        if (i <= 0) {
            return Response.error(ResponseEnum.SYSTEM_ERROR, "更新评论失败");
        }
        return Response.success("更新成功");
    }
}
