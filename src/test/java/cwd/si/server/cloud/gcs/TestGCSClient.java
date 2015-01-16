package cwd.si.server.cloud.gcs;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cwd.cs.server.cloud.CloudData;
import cwd.cs.server.cloud.google.GCSClient;
import cwd.cs.server.cloud.google.GCSConfiguration;

public class TestGCSClient
{
    
    private GCSClient client;
    
    @Before
    @Ignore
    public void setup()
    {        
        GCSConfiguration config = new GCSConfiguration();
        config.setBucketName("cloudstore-sb");
        config.setAuthTokenTempDirectory("/tmp/");
        config.setJsonOAuth2File("/cloud/google_oauth2.json");
        client = new GCSClient(config);
    }

    @Test
    @Ignore
    public void testGetKeys()
    {
        System.out.println("keys = " + client.getKeys()); 
    }
    
    @Test
    @Ignore
    public void testGetMetadata()
    {
        List<String> keys = client.getKeys();
        if(keys.size() > 0)
        {
        System.out.println("metadata= " + client.getMetadata(keys.get(0)));  
        }
        else
        {
            System.out.println("No data stored in Google Cloud Storage to pull metadata on");
        }
    }
    
    @Test
    @Ignore
    public void testGetData()
    {
        List<String> keys = client.getKeys();
        if(keys.size() > 0)
        {
            CloudData cloudData = client.getData(keys.get(0));
            System.out.println("CloudData = " + cloudData);
        }
        else
        {
            System.out.println("No data stored in Google Cloud Storage to getData on");
        }
    }
    
    @Test
    @Ignore
    public void testSaveData()
    {
        CloudData cloudData = new CloudData("my_key2", "This is the fileToSave".getBytes(), null);
        String result = client.saveData(cloudData);
        System.out.println("Result = " + result);
    }
    
    
}
