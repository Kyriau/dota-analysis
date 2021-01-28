package datacollection;

import java.sql.*;

public class DatabaseConnector {
    
    private Connection conn;
    
    public void insertRawResult(String query, String json) throws SQLException {
        PreparedStatement insertion = conn.prepareStatement("INSERT INTO RawOpenDotaResult (timeReceived, openDotaQuery, jsonResult) values (?, ?, ?);");
        insertion.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
        insertion.setString(2, query);
        insertion.setString(3, json);
        insertion.execute();
    }
    
    public void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Dota_Analysis?user=root&password=SC2-MtGoneaday");
    }
    
    public void disconnect() throws SQLException {
        conn.close();
    }

}