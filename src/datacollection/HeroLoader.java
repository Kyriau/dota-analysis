package datacollection;

import dataobjects.Hero;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

public class HeroLoader {
	
	public static void main(String[] args) {
		try {
			
			DatabaseConnector dc = new DatabaseConnector();
			dc.connect();
			
			OpenDotaConnector odc = new OpenDotaConnector(dc);
			String query = "heroes";
			String rawResult = odc.makeQuery(query);
			
			JSONArray heroesJson = new JSONArray(rawResult);
			for(int i = 0; i < heroesJson.length(); i++) {
				JSONObject heroJson = heroesJson.getJSONObject(i);
				Hero hero = new Hero(heroJson);
			}
			
			dc.disconnect();
			
		} catch(IOException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}