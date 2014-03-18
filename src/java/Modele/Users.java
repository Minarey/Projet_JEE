/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modele;

import ORM.Factory;
import POJOs.User;
import java.util.ArrayList;

/**
 *
 * @author alainlesage
 */
public class Users {
    
    private ArrayList<User> articles = null;
    
    private static Users instance;
    
    private Users()
    {
        articles = Factory.getUserList();
    }
    
    public static Users getInstance() 
    {
        if (instance == null)
            instance = new Users();
        return instance;
    }
    
    public ArrayList<User> getUsers()
    {
        return articles;
    }
}
