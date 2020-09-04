package datacollection;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {
    
    public Connection getConnection() throws SQLException {
        
        Connection conn = null;
        return conn;
        
    }
    
    public static void main(String[] args) {
        
        DatabaseConnector dc = new DatabaseConnector();
        try {
            dc.getConnection();
        } catch(SQLException e) {
        
        }
        
    }

}