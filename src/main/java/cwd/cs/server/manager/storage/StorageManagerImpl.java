package cwd.cs.server.manager.storage;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cwd.cs.common.DataPreferences;
import cwd.cs.common.UserSuppliedCredentials;
import cwd.cs.common.DataPreferences.DataPreferenceEnum;
import cwd.cs.server.cloud.CloudData;
import cwd.cs.server.cloud.CloudService;
import cwd.cs.server.manager.ServiceManager;
import cwd.cs.server.model.StorageMetadata;
import cwd.cs.server.model.encryption.KeyManager;
import cwd.cs.server.repository.StorageMetadataRepo;
import cwd.cs.server.util.GzipUtil;
import cwd.cs.server.util.HashUtil;

@Component
public class StorageManagerImpl extends ServiceManager implements StorageManager
{

    private static Log log = LogFactory.getLog(StorageManagerImpl.class);

    @Autowired
    @Qualifier("Simple")
    private KeyManager keyManager;

    @Autowired
    StorageMetadataRepo metadataRepo;

    @Autowired
    CloudService cloudService;

    private final DataPreferences dataPreferences;

    //really really bad...need to figure out a better way to do this...
    public StorageManagerImpl()
    {
        this(new UserSuppliedCredentials("anonUser", "bogus"));
    }

    public StorageManagerImpl(UserSuppliedCredentials userSuppliedCreds)
    {
        this(userSuppliedCreds, null);
    }

    public StorageManagerImpl(UserSuppliedCredentials userSuppliedCreds,
            DataPreferences userDataPrefs)
    {
        super(userSuppliedCreds);

        if (userDataPrefs == null)
        {
            userDataPrefs = new DataPreferences();
        }
        this.dataPreferences = userDataPrefs;
    }

    @Override
    public StorageMetadata saveData(String key, byte[] data)
    {
        StorageMetadata savedMetadata = null;

        if (isAuthenticatedUser())
        {
            StorageData storageData = createStorageData(key, data);
            if (storageData != null)
            {
                CloudData cdnData = storageData.getCloudData();
                StorageMetadata metadata = storageData.getStorageMetadata();

                if (cdnData != null && metadata != null)
                {
                    String dataLocation = cloudService.saveData(cdnData);

                    metadata.setExternalContainer(dataLocation);
                    savedMetadata = metadataRepo.save(metadata);
                }
                else
                {
                    log.error("CDNData or metadata object not created.  No save will occur.  CDNdata = "
                            + cdnData + "  metadata = " + metadata);
                }
            }
            else
            {
                log.error("StorageData object is null, no data will be saved");
            }
        }
        return savedMetadata;
    }

    /*
     * Method to create a StorageData object based on the input key and byte[] data content. The
     * StorageData object contains the CDNData object to be stored in the cloud as well as the
     * StorageMetadata to be stored locally.
     */
    private StorageData createStorageData(String key, byte[] data)
    {
        StorageData storageData = null;

        if (key != null && data != null)
        {
            String externalId = UUID.randomUUID().toString();

            StorageMetadata storageMetadata = new StorageMetadata();
            storageMetadata.setOwner(this.getUserCredentials().getUserId());
            storageMetadata.setInternalId(key);
            storageMetadata.setExternalId(externalId.toString());

            log.info("Generating internal hash.");
            String sha1InternalHash = HashUtil.generateSHA1HashCode(data);
            storageMetadata.setInternalHashCode(sha1InternalHash);

            if (shouldCompress())
            {
                log.info("Compressing data.");
                data = GzipUtil.compress(data);
                storageMetadata.setIsCompressed(true);
            }
            else
            {
                storageMetadata.setIsCompressed(false);
            }

            if (shouldEncrypt())
            {
                log.info("Encrypting data.");
                data = keyManager.encryptData(storageMetadata, data);
                storageMetadata.setIsEncrypted(true);
            }
            else
            {
                storageMetadata.setIsEncrypted(false);
            }

            if (data != null)
            {
                CloudData cloudData = new CloudData(externalId, data, null);

                storageData = new StorageData(cloudData, storageMetadata);

                log.info("Generating external hash.");
                String sha1ExternalHash = HashUtil.generateSHA1HashCode(cloudData.getData());
                storageMetadata.setExternalHashCode(sha1ExternalHash);
            }
            else
            {
                log.error("Something went wrong during while creating the StorageData object. The data is now null");
                storageData = null;
            }
        }
        else
        {
            log.error("updateData -- invalid input: KeyEntry or data is null");
            storageData = null;
        }
        return (storageData);
    }
    
    @Override
    public boolean deleteData(String internalIdentifier)
    {
        boolean deletedData = false;
        if (isAuthenticatedUser())
        {
            if (internalIdentifier != null)
            {
                StorageMetadata metadata = metadataRepo.findByInternalId(internalIdentifier);
                if (metadata != null)
                {
                    deletedData = cloudService.deleteData(metadata
                            .getExternalId());
                    metadataRepo.delete(metadata.getSmId());
                }
                else
                {
                    log.warn("Could not find persisted metadata for internalIdentifier = "
                            + internalIdentifier);
                }
            }
            else
            {
                log.warn("deleteData -- invalid input internalIdentifier is null");
            }
        }
        return (deletedData);
    }

    /*
     * Method to determine if the data should be encrypted based on the DataPreferences. By default,
     * data should be encrypted, unless the Client explicitly requests not to. requests not to.
     */
    private boolean shouldEncrypt()
    {
        boolean shouldEncrypt = true;
        if (dataPreferences != null)
        {
            String encryptValue = dataPreferences.getPreference(DataPreferenceEnum.ENCRYPT_DATA);
            if (encryptValue != null)
            {
                shouldEncrypt = Boolean.valueOf(encryptValue);
            }
        }

        return (shouldEncrypt);
    }

    /*
     * Method to determine if the data should be compressed based on the DataPreferences. By
     * default, data should be compressed, unless the Client explicitly requests not to.
     */
    private boolean shouldCompress()
    {
        boolean shouldCompress = true;
        if (dataPreferences != null)
        {
            String compressValue = dataPreferences.getPreference(DataPreferenceEnum.COMPRESS_DATA);
            if (compressValue != null)
            {
                shouldCompress = Boolean.valueOf(compressValue);
            }
        }

        return (shouldCompress);
    }

}
