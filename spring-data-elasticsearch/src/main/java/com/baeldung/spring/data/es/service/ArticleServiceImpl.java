package com.baeldung.spring.data.es.service;

import com.baeldung.spring.data.es.repository.ArticleRepository;
import com.baeldung.spring.data.es.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    
    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article findOne(String id) {
        return articleRepository.findOne(id);
    }

    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Page<Article> findByAuthorName(String name, Pageable pageable) {
        return articleRepository.findArticleByAuthorsName(name, pageable);
    }

    @Override
    public Page<Article> findByAuthorNameUsingCustomQuery(String name, Pageable pageable) {
        return articleRepository.findArticleByAuthorsNameUsingCustomQuery(name, pageable);
    }

    @Override
    public long count() {
        return articleRepository.count();
    }

    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }
}
