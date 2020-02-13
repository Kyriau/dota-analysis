import org.json.JSONObject;

import java.util.HashMap;

/**
 * Container for attributes of an item.
 */
public class ItemDatum {

    public static final String NAME = "Name";

    private HashMap<String, String> attributes;

    public ItemDatum(JSONObject item) {

        attributes = new HashMap<>();

        put(NAME, "dname", item);

    }

    private void put(String javaKey, String jsonKey, JSONObject item) {

        if(item.has(jsonKey))
            attributes.put(javaKey, item.getString(jsonKey));
        else {
            System.out.println("Attribute not found: " + javaKey);
            System.out.println("Item: " + item.toString());
        }

    }

    public String generateCSVEntry() {

        StringBuilder result = new StringBuilder();

        result.append(attributes.get(NAME));
        //result.append(",");

        return result.toString();

    }

    public static String generateCSVHeader() {

        StringBuilder result = new StringBuilder();

        result.append(NAME);
        //result.append(",");

        return result.toString();

    }

}