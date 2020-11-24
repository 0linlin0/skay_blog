package service.impl;

import entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CommentDao;
import service.CommentService;

/**
 * @auther Skay
 * @date 2020/11/13 16:11
 * @description
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Comment[] get_comments(String articleId) {
        return commentDao.get_comments(articleId);
    }
}
