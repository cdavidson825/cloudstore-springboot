package cwd.cs.server.manager.retrieval;

import java.util.List;
import java.util.Map;

/**
 * This is the main interface to the back-end storage retrieval.
 */
public interface RetrievalManager
{
    // TODO return Optional<ByteArrayOutputStream>
    public byte[] getData(String key);
    
    /**
     * Method to return the list of stored Keys in the Cloud
     * 
     * @return List of Keys stored in the Cloud content manager.
     */
    public List<String> getCloudKeys();
    
    /**
     * Method to get the Cloud Metadata for a given cloud key
     * 
     * @param cloudKey
     *            - The cloud key of the data to get the metadata for
     * @return Map of String key/value pairs for metadata stored in the Cloud content manager.
     */
    public Map<String, String> getCloudMetadata(String cloudKey);

    /**
     * Method to return the list of stored Keys in the local database.
     * @return
     */
    public List<String> getLocalKeys();

    /**
     * Method to get the local metadata for a given local key
     * @param localKey
     * @return String representation of the local Metadata object.
     */
    public String getLocalMetadata(String localKey);    

}
