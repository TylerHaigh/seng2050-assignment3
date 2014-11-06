package rgms.datacontext;

import java.sql.*;
import rgms.infrastructure.*;

/**
 * Provides a connection to a database
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
public abstract class DataManager {
  protected JDBCConnection connection = new JDBCConnection();
}