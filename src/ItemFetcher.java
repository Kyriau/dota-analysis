import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ItemFetcher implements Runnable {

    public void run() {

        List<ItemDatum> items = getItems();
        generateCSV(items);

    }

    private static List<ItemDatum> getItems() {

        try {

            List<ItemDatum> items = items = new LinkedList<>();

            String path = "dota-analysis/res/items.json";
            FileInputStream is = new FileInputStream(path);
            String raw = new String(is.readAllBytes());
            is.close();

            JSONObject json = new JSONObject(raw);
            for(String key : json.keySet()) {
                JSONObject item = (JSONObject) json.get(key);
                items.add(new ItemDatum(item));
            }

            return items;

        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    private void generateCSV(List<ItemDatum> items) {

        StringBuilder output = new StringBuilder();

        output.append(ItemDatum.generateCSVHeader());
        output.append("\n");

        for(ItemDatum item : items) {
            output.append(item.generateCSVEntry());
            output.append("\n");
        }

        try {
            FileOutputStream writer = new FileOutputStream("DotaItems.csv");
            writer.write(output.toString().getBytes());
            writer.flush();
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        new Thread(new ItemFetcher()).start();

    }

}