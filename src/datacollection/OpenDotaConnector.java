package datacollection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class OpenDotaConnector {
    
    private URL base;
    
    private DatabaseConnector dc;
    
    public String makeQuery(String query) throws IOException {
        
        Connection conn = dc.getConnection();
        
        InputStream queryStream = new URL(base, query).openStream();
        String rawResult = new String(queryStream.readAllBytes());
        
        try {
            dc.insertRawResult(query, rawResult);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return rawResult;
        
    }
    
    public OpenDotaConnector(DatabaseConnector dc) {
        this.dc = dc;
        try {
            base = new URL("https://api.opendota.com/api/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}