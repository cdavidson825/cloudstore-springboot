package cwd.cs.server.cloud.aws;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cwd.cs.server.cloud.CloudData;
import cwd.cs.server.cloud.CloudService;

public class MockS3Client implements CloudService
{
    @Override
    public List<String> getKeys()
    {
        return (Arrays.asList("AAA", "BBB", "CCC", "DDD"));
    }

    @Override
    public Map<String, String> getMetadata(String key)
    {
        return generateMetadata(key);
    }

    @Override
    public CloudData getData(String key)
    {
        return generateCloudData(key);
    }

    @Override
    public String saveData(CloudData data)
    {
        return "MockDataSaved";
    }

    @Override
    public boolean deleteData(String key)
    {
        return true;
    }

    private Map<String, String> generateMetadata(String key)
    {
        Map<String, String> mockMetadata = new HashMap<String, String>();
        mockMetadata.put("KEY",key);
        mockMetadata.put("111", "value1");
        mockMetadata.put("222", "value2");

        return mockMetadata;
    }

    private CloudData generateCloudData(String key)
    {
        String data = "This is mock CloudData object";
        return new CloudData(key, data.getBytes(), generateMetadata(key));
    }


}
