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
     
    private ArrayList<Comment> comments = null;
    
    private static Comments instance;
    
    private Comments()
    {
    }
    
    public static Comments getInstance() 
    {
        if (instance == null)
            instance = new Comments();
        return instance;
    }
    
    public ArrayList<Comment> getComments(int articleID)
    {
        return Factory.getCommentList(articleID);
    }
}
