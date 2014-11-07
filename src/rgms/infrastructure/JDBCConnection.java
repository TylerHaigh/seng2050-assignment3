package rgms.infrastructure;

import	java.sql.*;
import java.util.logging.*;

/**
 * Provides the means of connecting to a MYSQL Database for use within RGMS.
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 * 
 */
public class JDBCConnection {
    private static final Logger logger = Logger.getLogger(JDBCConnection.class.getName());

    //My SQL Connection String
    private static final String databaseHost = "localhost";
    private static final String databaseName = "RGMS_DB";
    private static final String dbUrl = "jdbc:mysql://" + databaseHost + "/" + databaseName;
    
    //My SQL Credentials
    private static final String userName = "rgms";
    private static final String password = "seng2050";
    
    private static Connection conn; //Create connection variable
    
    /**
     * Creates a connection to the JDBC Database
     */
    public static Connection getConnection() {
        //Check if the connection does not exist
        try {
            if(conn	== null || conn.isClosed()) {
                //Establish a new connection
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(dbUrl,userName,password);
            }

        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "JDBC Error", e);
        }
        return conn;
    }
}