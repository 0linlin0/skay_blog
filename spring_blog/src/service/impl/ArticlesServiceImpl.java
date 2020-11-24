package service.impl;

import entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ArticleDao;
import service.ArticlesService;

/**
 * @auther Skay
 * @date 2020/11/13 14:22
 * @description
 */

@Service
public class ArticlesServiceImpl implements ArticlesService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Article[] get_articles(String pageId) {
        return articleDao.get_all(pageId);
    }

    @Override
    public Article get_article(String id) {
        return articleDao.get_article(id);
    }
}
