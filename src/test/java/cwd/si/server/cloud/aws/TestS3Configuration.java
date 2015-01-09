package cwd.si.server.cloud.aws;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cwd.cs.server.cloud.aws.S3Configuration;
import cwd.si.SpringEnabledTest;

public class TestS3Configuration extends SpringEnabledTest
{

    @Autowired
    private S3Configuration s3Config;
    
    @Test
    public void testConfigNotNull()
    {
      assertNotNull(s3Config);  
    }
}
