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
import java.util.ArrayList;

/**
 *
 * @author alainlesage
 */
public class Factory {
    
    public static ArrayList<User> UserList(ResultSet result)
    {
      return new ArrayList<User>();
    }
    
    public static ArrayList<Article> ArticleList(ResultSet result)
    {
      return new ArrayList<Article>();
    }
    
    public static ArrayList<Comment> CommentList(ResultSet result)
    {
      return new ArrayList<Comment>();
    }
}
