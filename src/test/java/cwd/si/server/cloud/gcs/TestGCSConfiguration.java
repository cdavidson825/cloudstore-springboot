package cwd.si.server.cloud.gcs;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cwd.cs.server.cloud.google.GCSConfiguration;
import cwd.si.SpringEnabledTest;

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
