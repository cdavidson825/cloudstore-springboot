package cwd.si.server.cloud;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cwd.cs.server.cloud.CloudData;

public class TestCloudData
{
    static
    {
        Map<String, String> tmpMap = new HashMap<String, String>();
        tmpMap.put("hm1_key", "hm1_value");
        tmpMap.put("hm2_key", "hm2_value");
        metadata = Collections.unmodifiableMap(tmpMap);
    }
    
    private final static String key = "myKey";
    private final static String data = "here is my data";
    private final static Map<String, String> metadata;
    
    private CloudData cloudData = null;

    @Before
    public void setup()
    {
        cloudData = new CloudData(key, data.getBytes(), metadata);
    }

    @Test
    public void testKey()
    {
        assertThat(cloudData.getKey(), is(key));
    }
    
    @Test 
    public void testData()
    {
        assertThat(cloudData.getData(), is(data.getBytes()));
    }
    
    @Test
    public void testMetadata()
    {
        assertThat(cloudData.getMetadata(), is(metadata));
    }
}
