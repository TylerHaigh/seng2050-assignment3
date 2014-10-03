/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rgms.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rgms.infrastructure.JDBCConnection;

/**
 * Stores a connection to a JDBC connection
 * @author Tyler 2
 */
public class DatabaseBean implements Serializable {

    private JDBCConnection connection;
    private String query;
    
    /**
     * Constructor for a Database Bean. Establishes a connection to a JDBC
     * Database
     */
    public DatabaseBean() {
        //Try and establish a connection to a JDBC Database
        try {
            connection = new JDBCConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Gets the data from the Bean Query
     * @return The Result Set from the query
     */
    public ResultSet getData() {
        
        ResultSet rs = null;
        
        //Try and execute the query
        try {
            rs =  connection.executeQuery(this.query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return rs;
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public void setQuery(String query) {
        this.query = query;
    }
    
}
