import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;

public class HeroStats {

    private static Random rand = new Random();

    private static void getMyCentaurStats() {

        int player = 83518191;
        int hero = 96;

        try {

            URL base = new URL("https://api.opendota.com/api/");

            URL playerMatches = new URL(base, "players/" + player + "/matches?date=20&hero_id=" + hero);
            InputStream matchesStream = playerMatches.openStream();
            String matchesRaw = new String(matchesStream.readAllBytes());
            JSONArray matchesJSON = new JSONArray(matchesRaw);

            LinkedList<String> matches = new LinkedList<>();
            for(int i = 0; i < matchesJSON.length(); i++) {
                JSONObject item = matchesJSON.getJSONObject(i);
                matches.add(Long.toString(item.getLong("match_id")));
            }

            for(String match : matches) {URL playerMatch = new URL(base, "matches/" + match);
                InputStream matchStream = playerMatch.openStream();
                String matchRaw = new String(matchStream.readAllBytes());
                JSONObject matchJSON = new JSONObject(matchRaw);

                JSONArray players = matchJSON.getJSONArray("players");
                JSONObject playerInfo = null;
                for(Object p : players) {
                    JSONObject playerObj = (JSONObject) p;
                    if(!playerObj.isNull("account_id") && player == playerObj.getInt("account_id")) {
                        playerInfo = playerObj;
                        break;
                    }
                }

                if(playerInfo == null) {
                    throw new IllegalStateException("Player " + player + " not found in match " + match + ".");
                }

                int towerDamage = playerInfo.getInt("tower_damage");
                System.out.println(towerDamage);

                // Wait as to not send requests too frequently
                //TODO: Multithread requests vs. processing
                try {
                    Thread.sleep(rand.nextInt(100) + 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        getMyCentaurStats();
    }

}