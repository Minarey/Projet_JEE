/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modele;

import ORM.Factory;
import POJOs.Article;
import java.util.ArrayList;

/**
 *
 * @author alainlesage
 */
public class Articles {
    
    private ArrayList<Article> articles = null;
    
    private static Articles instance;
    
    private Articles()
    {
        articles = Factory.getArticleList();
    }
    
    public static Articles getInstance() 
    {
        if (instance == null)
            instance = new Articles();
        return instance;
    }
    
    public ArrayList<Article> getArticles()
    {
        return articles;
    }
}
