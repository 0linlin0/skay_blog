package controller;

import entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.IndexService;

/**
 * @auther Skay
 * @date 2020/11/13 15:50
 * @description
 */

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "/index")
    public String get_article(Model model){
        Article[] article_new = indexService.get_new();

        String[] new_articles = new String[article_new.length];
        int i = 0;
        for (Article each_article : article_new){
            new_articles[i] = each_article.getTitle();
        }

        Article[] article_recommand = indexService.get_recommand();
        model.addAttribute("new_articles",new_articles);
        model.addAttribute("article_recommand",article_recommand);




        return "index";
    }

}
