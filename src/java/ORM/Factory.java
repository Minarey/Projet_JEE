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
      ArrayList<User> userList = new ArrayList<>(); 
        try {
            result = DataBase.getInstance().getUserList();
            while (result.next())
            {
                User user = new User(  result.getInt("userID"),
                                       result.getString("pseudo"),
                                       result.getString("email"),
                                       result.getString("password"));
                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Factory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
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
    
    public static ArrayList<Comment> getCommentList(int articleID)
    {
      ArrayList<Comment> commentList = new ArrayList<>(); 
        try {
            result = DataBase.getInstance().getCommentList(articleID);
            while (result.next())
            {
                Comment comment = new Comment(  result.getInt("CommentID"),
                                                result.getString("Author"),
                                                result.getString("content"),
                                                result.getInt("newsID"));
                commentList.add(comment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Factory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commentList;
    }
}
