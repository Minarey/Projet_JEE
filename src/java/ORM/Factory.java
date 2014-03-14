/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ORM;

import POJOs.Article;
import POJOs.Comment;
import POJOs.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alainlesage
 */
public class Factory {
    
    private static ResultSet result = null;
    
    public static ArrayList<User> getUserList()
    {
      return new ArrayList<>();
    }
    
    public static ArrayList<Article> getArticleList()
    {
        ArrayList<Article> articleList = new ArrayList<>(); 
        try {
            result = DataBase.getInstance().getArticleList();
            while (result.next())
            {
                Article article = new Article(  result.getInt("ArticleID"),
                                                result.getDate("pubDate"),
                                                result.getString("title"),
                                                result.getString("content"),
                                                result.getInt("authorID"));
                articleList.add(article);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Factory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articleList;
    }
    
    public static ArrayList<Comment> getCommentList()
    {
      return new ArrayList<>();
    }
}
