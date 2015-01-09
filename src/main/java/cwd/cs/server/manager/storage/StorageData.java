package cwd.cs.server.manager.storage;

import cwd.cs.server.cloud.CloudData;
import cwd.cs.server.model.StorageMetadata;


public class StorageData
{
    private final CloudData cloudData;
    
    private final StorageMetadata storageMetadata;
    
    public StorageData(CloudData cloudData, StorageMetadata storageMetadata)
    {
        this.cloudData = cloudData;
        this.storageMetadata = storageMetadata;
    }

    public CloudData getCloudData()
    {
        return cloudData;
    }

    public StorageMetadata getStorageMetadata()
    {
        return storageMetadata;
    }
}
