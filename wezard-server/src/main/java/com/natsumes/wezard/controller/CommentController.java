package com.natsumes.wezard.controller;

import com.github.pagehelper.PageInfo;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.CommentForm;
import com.natsumes.wezard.pojo.Comment;
import com.natsumes.wezard.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comment")
    public Response<PageInfo> selectComment(@RequestParam(required = false) Integer userId,
                                            @RequestParam(required = false) Integer productId,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return commentService.selectComment(userId, productId, pageNum, pageSize);
    }

    @PostMapping("/comment")
    public Response addComment(@RequestBody CommentForm commentForm) {
        return commentService.addComment(commentForm);
    }

    @PutMapping("/comment")
    public Response updateComment(@RequestBody Comment comment) {
        return commentService.updateComment(comment);
    }

    @DeleteMapping("/comment/{id}")
    public Response addComment(@PathVariable Integer id) {
        return commentService.deleteComment(id);
    }
}
