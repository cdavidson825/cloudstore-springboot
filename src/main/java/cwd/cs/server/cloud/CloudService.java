package cwd.cs.server.cloud;

import java.util.List;
import java.util.Map;

public interface CloudService
{
    /**
     * Method to get the list of Keys stored in the CloudService
     * 
     * @return List of the Keys
     */
    public List<String> getKeys();

    /**
     * Method to get the metadata associated with a given key stored in the
     * CloudService.
     * 
     * @param key
     *            The key of the data to get the metadata for
     * @return Map of String key/value pairs for metadata stored.
     */
    public Map<String, String> getMetadata(String key);

    /**
     * Method to get Data based on a unique key
     * 
     * @param key
     *            The key of the data to retrieve
     * @return CloudData object associated with key or a null if key not found.
     */
    public CloudData getData(String key);

    /**
     * Method to save data for a given CloudData object. If the key (contained in
     * the CloudData object) does not exist in the back-end datastore, the CloudData
     * will be inserted. If the key already exists, an update will occur making
     * the persisted data match the CloudData object passed in.
     * 
     * @param data
     *            the CloudData object (includes the byte[] data content and
     *            metadata) to be inserted.
     * @return String representing the Location of the data stored in the cloud
     *         (currently this is the Container/Bucket name).
     */
    public String saveData(CloudData data);

    /**
     * Method to delete data for a given key.
     * 
     * @param key the unique key of the data to be deleted
     * @return true if the delete was successful. false if there was an error or
     *         the key did not exist.
     */
    public boolean deleteData(String key);
    
}
