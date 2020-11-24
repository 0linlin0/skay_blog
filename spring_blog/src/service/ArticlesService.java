package service;

import entity.Article;

/**
 * @auther Skay
 * @date 2020/11/13 14:21
 * @description
 */

public interface ArticlesService {
    public Article[] get_articles(String pageId);

    Article get_article(String id);
}
