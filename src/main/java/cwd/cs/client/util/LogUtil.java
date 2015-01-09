package cwd.cs.client.util;

import java.util.List;
import java.util.Map;

public class LogUtil
{
    /**
     * Builds a log string containing a {@link List}.
     * @param msg
     * @param values
     * @return
     */
    public static String buildLogString(String msg, List<String> values) {
        StringBuilder result = new StringBuilder();
        
        result.append("\n\n******* ");
        result.append(msg);
        result.append(" *******");
        
        for (String string : values) {
            result.append("\n");
            result.append(string);
        }
        
        result.append("\n");
        
        return result.toString();
    }
    
    /**
     * Builds a log string containing a {@link Map}.
     * @param msg
     * @param map
     * @return
     */
    public static String buildLogString(String msg, Map<String, String> map) {
        StringBuilder result = new StringBuilder();
        
        result.append("\n\n******* ");
        result.append(msg);
        result.append(" *******");
        
        if (null != map) {
            for (String key : map.keySet()) {
                result.append("\nkey: ");
                result.append(key);
                result.append("\tvalue: ");
                result.append(map.get(key));
            }
        }
        else {
            result.append("\n<no metadata>");
        }
        
        result.append("\n");
        
        return result.toString();
    }
    
    /**
     * Builds a log string containing a {@link String}.
     * @param msg
     * @param value
     * @return
     */
    public static String buildLogString(String msg, String value) {
        StringBuilder result = new StringBuilder();
        
        result.append("\n\n******* ");
        result.append(msg);
        result.append(" *******\n");
        result.append(value);
        result.append("\n");
        
        return result.toString();
    }
}
