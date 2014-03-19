/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modele;

import ORM.UserJpaController;
import POJOs.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alainlesage
 */
public class Users {
    
    private List<User> users = null;
    
    private static Users instance;
    
    private UserJpaController jpaController = new UserJpaController();
    
    private Users()
    {
        users = jpaController.findUserEntities();
    }
    
    public static Users getInstance() 
    {
        if (instance == null)
            instance = new Users();
        return instance;
    }
    
    public List<User> getUsers()
    {
        return users;
    }
}
