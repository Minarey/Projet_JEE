/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modele;

import ORM.UserJpaController;
import POJOs.Article;
import POJOs.User;
import java.util.ArrayList;

/**
 *
 * @author alainlesage
 */
public class Users {
    
    private ArrayList<User> users = null;
    
    private static Users instance;
    
    private UserJpaController jpaController;
    
    private Users()
    {
        users = (ArrayList<User>) jpaController.findUserEntities();
    }
    
    public static Users getInstance() 
    {
        if (instance == null)
            instance = new Users();
        return instance;
    }
    
    public ArrayList<User> getUsers()
    {
        return users;
    }
}
