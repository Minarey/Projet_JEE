/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modele;

import ORM.ArticleJpaController;
import POJOs.Article;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alainlesage
 */
public class Articles {
    
    private List<Article> articles = null;
    
    private static Articles instance;
    
    private ArticleJpaController jpaController = new ArticleJpaController();
    
    private Articles()
    {
        articles = jpaController.findArticleEntities();
    }
    
    public static Articles getInstance() 
    {
        if (instance == null)
            instance = new Articles();
        return instance;
    }
    
    public List<Article> getArticles()
    {
        return articles;
    }
}
