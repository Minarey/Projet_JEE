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
    
    private static ArrayList<User> users = null;
    
    public Users()
    {
        users = Factory.getUserList();
    }
    
    public static ArrayList<User> getComments()
    {
        return users;
    }
}
