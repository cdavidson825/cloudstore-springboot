package cwd.cs.common;

import java.util.HashMap;

public class DataPreferences
{
    
    public enum DataPreferenceEnum
    {
        COMPRESS_DATA, ENCRYPT_DATA
    }
    
    private HashMap<DataPreferenceEnum, String> preferencesHashMap = new HashMap<DataPreferenceEnum, String>();

    public void addPreference(DataPreferenceEnum key, String value)
    {
        preferencesHashMap.put(key, value);
    }

    /**
     * method to convert the user preferences into a hash map of strings for
     * generic storage on the server-side.
     * 
     * @return
     */
    public HashMap<String, String> convertToHashMapOfStrings()
    {
        HashMap<String, String> hashMapOfStrings = new HashMap<String, String>();
        for (DataPreferenceEnum key : preferencesHashMap.keySet())
        {
            String valueString = preferencesHashMap.get(key).toString();
            hashMapOfStrings.put(key.toString(), valueString);
        }

        return (hashMapOfStrings);
    }

    /**
     * get the preference for a given DataPreferenceEnum
     * 
     * @param key
     *            The key to lookup the value from
     * @return The String value or null if the key did not exist
     */
    public String getPreference(DataPreferenceEnum key)
    {
        return (preferencesHashMap.get(key));
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("DataPreferences [preferencesHashMap=");
        builder.append(preferencesHashMap);
        builder.append("]");
        return builder.toString();
    }
}
