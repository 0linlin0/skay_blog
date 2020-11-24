package service;

import entity.Comment;

/**
 * @auther Skay
 * @date 2020/11/13 16:10
 * @description
 */
public interface CommentService {
    Comment[] get_comments(String articleId);
}
