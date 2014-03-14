/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modele;

import ORM.Factory;
import POJOs.Comment;
import java.util.ArrayList;

/**
 *
 * @author alainlesage
 */
public class Comments {
    
    private static ArrayList<Comment> comments = null;
    
    public Comments(int articleID)
    {
        comments = Factory.getCommentList(articleID);
    }
    
    public static ArrayList<Comment> getComments()
    {
        return comments;
    }
}
