package controller;

import entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import service.CommentService;

/**
 * @auther Skay
 * @date 2020/11/13 16:10
 * @description
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment/{articleId}")
    public String get_comments(Model model,@PathVariable String articleId){
        Comment[] comments = commentService.get_comments(articleId);
        model.addAttribute("commants",comments);
        return "article";
    }
}
