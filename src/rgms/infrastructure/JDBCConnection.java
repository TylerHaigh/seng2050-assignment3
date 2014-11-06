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
    private String databaseHost = "localhost";
    private String databaseName = "RGMS_DB";
    private String dbUrl = "jdbc:mysql://" + databaseHost + "/" + databaseName;
    
    //My SQL Credentials
    private String userName = "rgms";
    private String password = "seng2050";
    
    private Connection conn; //Create connection variable
    
    /**
     * Constructor for a JDBC connection
     * 
     * @throws ClassNotFoundException The driver class cannot be found
     * @throws InstantiationException Cannot instantiate a connection to the
     *                                JDBC driver
     * @throws IllegalAccessException Unable to access the JDBC driver
     */		
    public JDBCConnection() {
        //Load the database driver
        try {
            logger.info("Loading JDBC driver");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "JDBC Error", e);
        }
    }

    public JDBCConnection(Connection connection) {
        this.conn = connection;
    }
    
    /**
     * Creates a connection to the JDBC Database
     * @throws SQLException The connection cannot be established
     */
    public Connection getConnection() throws	SQLException {
        //Check if the connection does not exist
        if(conn	== null || conn.isClosed()) {
            //Establish a new connection
            conn = DriverManager.getConnection(dbUrl,userName,password);
        }

        return conn;
    }
    
    /**
     * Closes the connection to the JDBC Database
     * @throws SQLException The connection cannot be closed
     */
    public void setDone() throws SQLException {
        //Check the connection still exists
        if(conn!=null) {
            //Close the connection
            conn.close();
            conn = null;
        }
    }

    /**
     * Executes a query to the database
     * @param query The query to execute
     * @return A Result Set containing the results of the query
     * @throws SQLException  Unable to execute the query
     */
    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet rs = null;
        
        //Exeute the query
        try {
            Statement s = conn.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rs;
    }
    
    
}