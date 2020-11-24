package service.impl;

import entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ArticleDao;
import service.IndexService;

/**
 * @auther Skay
 * @date 2020/11/13 15:52
 * @description
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Article[] get_new() {
        return articleDao.get_newest();
    }

    @Override
    public Article[] get_recommand() {
        return articleDao.get_recommend();
    }
}
