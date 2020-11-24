package controller;

import entity.Article;
import entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ArticlesService;
import service.CommentService;

import java.util.List;

/**
 * @auther Skay
 * @date 2020/11/13 11:41
 * @description
 */

@Controller
public class ArticlesController {

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private CommentService commentService;

    /**
        * @Author  Skay
        * @Date  2020/11/13 15:36
        * @Param  [model, pageId]
        * @return  java.lang.String
        * @Description  获取全部文章列表
        * @todo
    */
    @RequestMapping(value = "/articles/{pageId}")
    public String get_articles(Model model, @PathVariable String pageId){
        Article[] articles = articlesService.get_articles(pageId);
        model.addAttribute("articles",articles);
        return "articles";
    }

    /**
        * @Author  Skay
        * @Date  2020/11/13 15:37
        * @Param  [model, pageId]
        * @return  java.lang.String
        * @Description  获取文章详情
        * @todo
    */
    @RequestMapping(value = "/articles/detail/{Id}")
    public String get_article(Model model, @PathVariable String Id){
        Article article = articlesService.get_article(Id);
        model.addAttribute("article",article);

        Comment[] comments = commentService.get_comments(Id);
        model.addAttribute("comments",comments);

        return "article";
    }





}
