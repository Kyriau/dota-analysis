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

        String player = "83518191";
        String hero = "96";

        try {

            URL base = new URL("https://api.opendota.com/api/");

            URL playerMatches = new URL(base, "players/" + player + "/matches?date=20&hero=" + hero);
            InputStream matchesStream = playerMatches.openStream();
            String matchesRaw = new String(matchesStream.readAllBytes());
            JSONArray matchesJSON = new JSONArray(matchesRaw);

            LinkedList<String> matches = new LinkedList<>();
            for(int i = 0; i < matchesJSON.length(); i++) {
                JSONObject item = matchesJSON.getJSONObject(i);
                matches.add(item.getString("match_id"));
            }

            for(String match : matches) {

                URL playerMatch = new URL(base, "matches/" + match);
                InputStream matchStream = playerMatch.openStream();
                String matchRaw = new String(matchStream.readAllBytes());
                JSONObject matchJSON = new JSONObject(matchRaw);

                JSONArray players = matchJSON.getJSONArray("players");
                int i = -1;
                for(String account = ""; !account.equals(player); ++i) {
                    account = players.getJSONObject(i).getString("account_id");
                }
                JSONObject playerInfo = players.getJSONObject(i);
                // Valve doesn't publish this data from their api, so this one is rip.
                int towerDamage = playerInfo.getInt("tower_damage");

                // Wait so we aren't sending requests too frequently
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