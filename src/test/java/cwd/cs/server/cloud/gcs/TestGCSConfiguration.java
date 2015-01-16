package cwd.cs.server.cloud.gcs;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cwd.cs.SpringEnabledTest;
import cwd.cs.server.cloud.google.GCSConfiguration;

public class TestGCSConfiguration extends SpringEnabledTest
{

    @Autowired
    private GCSConfiguration gcsConfig;
    
    @Test
    public void testConfigNotNull()
    {
      assertNotNull(gcsConfig);  
    }
    
}
