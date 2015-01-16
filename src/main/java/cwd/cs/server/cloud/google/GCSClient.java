package cwd.cs.server.cloud.google;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.model.Objects;
import com.google.api.services.storage.model.StorageObject;

import cwd.cs.server.cloud.CloudData;
import cwd.cs.server.cloud.CloudService;

public class GCSClient implements CloudService
{
    private static Log log = LogFactory.getLog(GCSClient.class);

    private final GCSConfiguration gcsConfig;
    private static Storage storage;
    private final OAuth2Service oAuth2;

    public GCSClient(GCSConfiguration gcsConfig)
    {
        if (gcsConfig == null)
        {
            String msg =
                    "GCSConfig not initialized, therefore cannot connect to Google Cloud Storage.. Please verify the Spring configuration";
            log.fatal(msg);
            throw new RuntimeException(msg);
        }

        this.gcsConfig = gcsConfig;
        oAuth2 = new OAuth2Service(gcsConfig);
        try
        {
            storage = oAuth2.authenticate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getKeys()
    {
        List<String> keyList = new ArrayList<String>();
        try
        {
            Objects objects = storage.objects().list(gcsConfig.getBucketName()).execute();
            for (StorageObject object : objects.getItems())
            {
                keyList.add(object.getName());
            }
        }
        catch (Exception e)
        {
            log.error("Caught the following exception in getKeys()", e);
        }
        return keyList;
    }

    @Override
    public Map<String, String> getMetadata(String key)
    {
        Map<String, String> metadata = new HashMap<String, String>();
        try
        {
            StorageObject object = storage.objects().get(gcsConfig.getBucketName(), key).execute();
            // TODO parse JSON and emit map of key/values.
            metadata.put("response", object.toPrettyString());
        }
        catch (Exception e)
        {
            log.error("Caught the following exception in getMetadata() for key: " + key, e);
        }
        return metadata;
    }

    @Override
    public CloudData getData(String key)
    {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CloudData cloudData = null;
        try
        {
            Storage.Objects.Get getObject = storage.objects().get(gcsConfig.getBucketName(), key);
            getObject.executeMediaAndDownloadTo(out);

            cloudData = new CloudData(key, out.toByteArray(), new HashMap<String, String>());

        }
        catch (Exception e)
        {
            log.error("Caught the following exception in getData() for key: " + key, e);

        }
        return cloudData;
    }

    @Override
    public String saveData(CloudData data)
    {
        StorageObject objectMetadata = null;
        InputStreamContent mediaContent =
                new InputStreamContent("application/octet-stream", new ByteArrayInputStream(
                        data.getData()));
        String returnString = "unknown";
        try
        {
            Storage.Objects.Insert insertObject =
                    storage.objects().insert(gcsConfig.getBucketName(), objectMetadata,
                            mediaContent);
            insertObject.execute();
            returnString = insertObject.getName();
        }
        catch (Exception e)
        {
            log.error("Caught Exception saving data", e);
        }

        return returnString;
    }

    @Override
    public boolean deleteData(String key)
    {
        log.warn("DELETE Not implemented yet...");
        return false;
    }



}
