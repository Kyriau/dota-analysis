package datacollection;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class DatabaseConnector {
    
    private Connection conn;
    
    public void insertRawResult(String query, String jsonResult) throws SQLException {
        PreparedStatement insertion = conn.prepareStatement("INSERT INTO RawOpenDotaResult (timeReceived, openDotaQuery, jsonResult) values (?, ?, ?);");
        insertion.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
        insertion.setString(2, query);
        insertion.setString(3, jsonResult);
        insertion.execute();
    }
    
    public void rawResultToFile(int resultID) throws SQLException {
        PreparedStatement selection = conn.prepareStatement("SELECT (jsonResult) FROM RawOpenDotaResult WHERE resultID = ?;");
        selection.setInt(1, resultID);
        ResultSet result = selection.executeQuery();
        if(!result.next())
            throw new IllegalArgumentException("Requested resultID invalid in database.");
        String rawJson = result.getString(1);
        try {
            String url = "output/json/rawQuery" + resultID + ".json";
            FileOutputStream output = new FileOutputStream(url);
            output.write(rawJson.getBytes());
            output.flush();
            output.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void connect() {
        try {
            if (conn == null)
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Dota_Analysis?user=root&password=SC2-MtGoneaday");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isConnected() {
        return conn != null;
    }
    
    public Connection getConnection() {
        if(!isConnected())
            connect();
        return conn;
    }
    
    public static void main(String[] args) {
        try {
            DatabaseConnector dc = new DatabaseConnector();
            dc.connect();
            OpenDotaConnector odc = new OpenDotaConnector(dc);
            String query = "players/83518191/matches?hero_id=88";
            String rawResult = odc.makeQuery(query);
            dc.disconnect();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}