package cwd.cs.server.cloud;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cwd.cs.SpringEnabledTest;
import cwd.cs.server.cloud.CloudData;
import cwd.cs.server.cloud.CloudService;

public class TestMockCloudService extends SpringEnabledTest
{

    @Autowired
    CloudService cloudService;

    @Test
    public void testCloudServiceNotNull()
    {
        assertNotNull(cloudService);
    }

    @Test
    public void testGetMetadata()
    {
        Map<String, String> metadata = cloudService.getMetadata("testValue");
        String value = metadata.get("KEY");
        assertThat(value, is("testValue"));
        assertThat(metadata.containsKey("111"), is(true));
        assertThat(metadata.containsKey("222"), is(true));
        assertThat(metadata.containsKey("666"), is(false));
    }
    
    @Test
    public void testGetData()
    {
        CloudData data = cloudService.getData("testKey");
        assertThat(new String(data.getData()), is("This is mock CloudData object"));
    }
    
    @Test
    public void testSaveData()
    {
        CloudData data = cloudService.getData("testKey");
        String returnString = cloudService.saveData(data);
        assertThat(returnString, is("MockDataSaved"));
    }
    
    @Test
    public void testDelete()
    {
        boolean returnValue = cloudService.deleteData("fake");
        assertThat(returnValue, is(true));
    }

}
