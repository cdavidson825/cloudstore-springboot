package cwd.cs.server.manager.retrieval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cwd.cs.common.UserSuppliedCredentials;
import cwd.cs.server.cloud.CloudData;
import cwd.cs.server.cloud.CloudService;
import cwd.cs.server.manager.ServiceManager;
import cwd.cs.server.model.StorageMetadata;
import cwd.cs.server.model.encryption.KeyManager;
import cwd.cs.server.repository.StorageMetadataRepo;
import cwd.cs.server.util.GzipUtil;
import cwd.cs.server.util.HashUtil;

@Component
public class RetrievalManagerImpl extends ServiceManager implements RetrievalManager
{

    private static Log log = LogFactory.getLog(RetrievalManagerImpl.class);

    @Autowired
    @Qualifier("Simple")
    private KeyManager keyManager;

    @Autowired
    StorageMetadataRepo metadataRepo;

    @Autowired
    CloudService cloudService;


    public RetrievalManagerImpl()
    {
        this(new UserSuppliedCredentials("anonUser", "bogus"));
    }

    public RetrievalManagerImpl(UserSuppliedCredentials userSuppliedCreds)
    {
        super(userSuppliedCreds);
    }

    @Override
    public byte[] getData(String key)
    {
        {
            byte[] returnData = null;

            if (isAuthenticatedUser())
            {
                if (key != null)
                {
                    StorageMetadata metadata = metadataRepo.findOne(Long.valueOf(key));

                    if (metadata != null)
                    {
                        CloudData cloudData = cloudService.getData(metadata.getExternalId());

                        returnData = cloudData.getData();
                        log.debug("CloudData -- metadata = " + metadata);

                        if (returnData != null)
                        {
                            String generatedExternalHashcode =
                                    HashUtil.generateSHA1HashCode(returnData);
                            /*
                             * verify the saved and generated external hashcodes match. This method
                             * logs errors if they don't.
                             */
                            log.info("Verifying external hash.");
                            HashUtil.verifyHashCode(metadata.getExternalHashCode(),
                                    generatedExternalHashcode);

                            if (metadata.getIsEncrypted())
                            {
                                log.info("Decrypting data.");
                                returnData =
                                        keyManager.decryptData(metadata.getKeyMetadata(),
                                                returnData);
                            }

                            if (metadata.getIsCompressed())
                            {
                                log.info("Decompressing data.");
                                returnData = GzipUtil.decompress(returnData);
                            }
                            String generatedInternalHashcode =
                                    HashUtil.generateSHA1HashCode(returnData);
                            /*
                             * verify the saved and generated internal hashcodes match. This method
                             * logs errors if they don't.
                             */
                            log.info("Verifying internal hash.");
                            HashUtil.verifyHashCode(metadata.getInternalHashCode(),
                                    generatedInternalHashcode);
                        }
                        else
                        {
                            log.warn("getData -- no data returned for key " + key);
                        }
                    }
                    else
                    {
                        log.error("Could not find metadata for internalId = " + key);
                    }
                }
                else
                {
                    log.error("getData -- invalid input key -- null ");
                }
            }

            return returnData;
        }
    }

    @Override
    public List<String> getCloudKeys()
    {
        List<String> keyNames = null;

        if (isAuthenticatedUser())
        {
            keyNames = cloudService.getKeys();
        }

        return (keyNames);
    }

    @Override
    public Map<String, String> getCloudMetadata(String cloudKey)
    {
        Map<String, String> metadata = null;
        if (isAuthenticatedUser())
        {
            if (cloudKey != null)
            {
                metadata = cloudService.getMetadata(cloudKey);
            }
        }
        return metadata;
    }

    @Override
    public List<String> getLocalKeys()
    {
        List<String> localKeyList = new ArrayList<String>();
        if (isAuthenticatedUser())
        {
            Collection<StorageMetadata> metadataCollection = metadataRepo.findAll();
            log.info(String.format("getLocalKeys Found %d records", metadataCollection.size()));
            for (StorageMetadata metadata : metadataCollection)
            {
                localKeyList.add(metadata.summary());
            }
        }
        return localKeyList;
    }

    @Override
    public String getLocalMetadata(String localId)
    {
        String metadataString = null;
        if (isAuthenticatedUser())
        {
            if (localId != null)
            {
                StorageMetadata metadata = metadataRepo.findByInternalId(localId);
                if (metadata != null)
                {
                    metadataString = metadata.toString();
                }
            }
        }
        return metadataString;
    }

}
