/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package POJOs;

/**
 *
 * @author alainlesage
 */
public class User {
    
    private Integer userID = null;
    
    private String pseudo = null;
    
    private String email = null;
    
    private String password = null;
    
    public User(String pseudo, String email, String password) 
    {
        setPseudo(pseudo);
        setEmail(email);
        setPassword(password);
    }
    
    public User(Integer userID, String pseudo, String email, String password) 
    {
        this(pseudo, email, password);
        this.setUserID(userID);
    }

    private void setUserID(Integer userID) {
        this.userID = userID;
    }
     
    private void setPseudo(String pseudo) {
        this.pseudo = pseudo; 
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setPassword(String password) {
        this.password = password;
    }
    
    public Integer getUserID() {
        return userID;
    }
     
    private String getPseudo() {
        return pseudo; 
    }

    private String getEmail() {
        return email;
    }

    private String getPassword() {
        return password;
    }
}
