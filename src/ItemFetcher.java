import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ItemFetcher implements Runnable {

    public void run() {

        try{

            String path = "https://liquipedia.net/dota2/Portal:Items";
            InputStream is = new URL(path).openStream();
            byte[] raw = is.readAllBytes();
            String result = new String(raw);

            String[] matches = result.split("<div class=\"responsive\"[^>]*><a href=\"");
            String[] items = new String[matches.length - 1];
            for(int i = 1; i < matches.length; i++) {
                items[i - 1] = matches[i].substring(0, matches[i].indexOf("\""));
            }

            for(int i = 0; i < items.length; i++) {
                System.out.println(items[i]);
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Thread(new ItemFetcher()).start();
    }

}