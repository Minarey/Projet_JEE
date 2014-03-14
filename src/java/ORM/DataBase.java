/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ORM;

import POJOs.Article;
import POJOs.Comment;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author alainlesage
 */
public class DataBase {
    
    private String url = "jdbc:mysql://localhost:3306/projet_web_jee";
    
    private String user = "admin";
    
    private String password = "lenfant";
    
    private Connection connection = null;
    
    private String query = null;
    
    private PreparedStatement statement = null;
    
    private ResultSet result = null;
    
    private static DataBase instance = null;
    
    public static DataBase getInstance() {
        if (instance == null)
            instance = new DataBase();
        return instance;
    }
    
    private DataBase(){
        importDriver();
        connect();
    }
    
    @Override
    public void finalize() throws Throwable
    {
        disconnect();
        super.finalize();
    }

    private void importDriver() {
        /* Load JDBC driver for MySQL */
            try {
                Class.forName( "com.mysql.jdbc.Driver" );
            } catch ( ClassNotFoundException e ) {
                /* Gérer les éventuelles erreurs ici. */
            }
    }

    private void connect() {
        /* Connexion à la base de données */
            
            try {
                connection = DriverManager.getConnection( url, user, password );

                out.println("Connection established for user " + user);

            } catch ( SQLException e ) {
                out.println("Connection denied" + " " + e.getMessage() + " " + e.getSQLState());
            } finally {
                if ( connection != null )
                    disconnect();
            }
    }

    private void disconnect() {
        try {
            /* Fermeture de la connexion */
            connection.close();
        } catch ( SQLException ignore ) {
            /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
        }
    }
    
    public ResultSet getArticleList()
    {
        try {
            statement = connection.prepareStatement("SELECT * FROM Article;");
            result = statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ResultSet getCommentList(int commentID)
    {
        try {
            statement = connection.prepareStatement("SELECT * FROM Comment WHERE newsID = " + articleID + ";");
            result = statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ResultSet getUserList()
    {
        try {
            statement = connection.prepareStatement("SELECT * FROM User;");
            result = statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ResultSet getArticleByID(int articleID)
    {
        try {
            statement = connection.prepareStatement("SELECT * FROM Article WHERE article ID  = " + articleID + ";");
            result = statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ResultSet getCommentByID(int commentID)
    {
        try {
            statement = connection.prepareStatement("SELECT * FROM Comment WHERE newsID = " + commentID + ";");
            result = statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ResultSet getUserByID(int userID)
    {
        try {
            statement = connection.prepareStatement("SELECT * FROM User WHERE userID = " + userID + ";");
            result = statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ResultSet addArticle(Article article)
    {
        try {
            statement = connection.prepareStatement("INSERT INTO Article VALUES(" + article.getPubDate() +","
                                                                                  + article.getTitle() +","
                                                                                  + article.getContent()+","
                                                                                  + article.getAuthorID()+");"
                                                    );
            result = statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ResultSet addComment(Comment comment)
    {
        try {
            statement = connection.prepareStatement("INSERT INTO Article VALUES(" + comment.getPubDate() +","
                                                                                  + comment.getAuthor() +","
                                                                                  + comment.getArticleID()+","
                                                                                  + comment.getContent()+");"
                                                    );
            result = statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
