package rgms.datacontext;

import java.sql.*;
import rgms.infrastructure.*;

public abstract class DataManager {
  protected JDBCConnection connection = new JDBCConnection();
}