package service;

import entity.Article;

public interface IndexService {
    Article[] get_new();

    Article[] get_recommand();
}
