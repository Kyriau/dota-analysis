import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;

public class ItemFetcher implements Runnable {

    private static Random rand = new Random();

    public void run() {

        String[] urls = getItemURLArray();
        for(String url : urls) {
            try {
                Thread.sleep(rand.nextInt(1000) + 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getItemData("https://liquipedia.net" + url);
        }

    }

    private String[] getItemURLArray() {

        try{

            String path = "https://liquipedia.net/dota2/Portal:Items";
            InputStream is = new URL(path).openStream();
            byte[] raw = is.readAllBytes();
            String html = new String(raw);

            String[] matches = html.split("<div class=\"responsive\"[^>]*><a href=\"");
            String[] items = new String[matches.length - 1];
            for(int i = 1; i < matches.length; i++) {
                items[i - 1] = matches[i].substring(0, matches[i].indexOf("\""));
            }

            System.out.printf("Found %d items.%n", items.length);

            return items;

        } catch(IOException e) {
            e.printStackTrace();
        }

        // This line should only be reached if an exception was thrown above.
        throw new IllegalStateException("getItemURLArray() did not return properly.");

    }

    private void getItemData(String url) {

        try {

            InputStream is = new URL(url).openStream();
            byte[] raw = is.readAllBytes();
            System.out.printf("Received %d bytes.%n", raw.length);
            String html = new String(raw);

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Thread(new ItemFetcher()).start();
    }

}