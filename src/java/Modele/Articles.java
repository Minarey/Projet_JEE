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
    
    private static ArrayList<Article> articles = null;
    
    public Articles()
    {
        articles = Factory.getArticleList();
    }
    
    public static ArrayList<Article> getArticles()
    {
        return articles;
    }
}
