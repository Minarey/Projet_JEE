/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ORM;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author alainlesage
 */
public class DataBase {
    
    private String url = "jdbc:mysql://localhost:3306/projet_web_jee";
    
    private String user = "admin";
    
    private String password = "lenfant";
    
    private Connection connexion = null;
    
    private String query = null;
    
    private Statement statement = null;
    
    private ResultSet resultat = null;
    
    public DataBase(){
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
                connexion = DriverManager.getConnection( url, user, password );

                out.println("Connection established for user " + user);

            } catch ( SQLException e ) {
                out.println("Connection denied" + " " + e.getMessage() + " " + e.getSQLState());
            } finally {
                if ( connexion != null )
                    disconnect();
            }
    }

    private void disconnect() {
        try {
            /* Fermeture de la connexion */
            connexion.close();
        } catch ( SQLException ignore ) {
            /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
        }
    }
}
