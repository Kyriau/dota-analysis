package datacollection;

import java.io.IOException;
import java.net.URL;

public class OpenDotaConnector {
    
    private URL base;
    
    public OpenDotaConnector() {
        try {
            base = new URL("https://api.opendota.com/api/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}