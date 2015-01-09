package cwd.cs.server.manager.storage;

public interface StorageManager
{
    /**
     * Method to save data for a given key. If the key does not exist in the
     * back-end datastore, the key and data will be inserted. If the key already
     * exists, an update will occur making the persisted data match the data
     * passed in.
     * 
     * @param key
     *            the unique key of the data to be inserted or updated
     * @param data
     *            the data to be inserted/updated
     * @return true if the data was successfully inserted or updated, false
     *         otherwise.
     */
    public boolean saveData(String key, byte[] data);
    
  /**
  * Method to delete data for a given key.
  * 
  * @param key
  *            the unique key of the data to be deleted
  * @return true if the delete was successful. false if there was an error or
  *         the key did not exist.
  */
  public boolean deleteData(String key);

    
}
