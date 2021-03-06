package cwd.cs.client;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cwd.cs.SpringEnabledTest;
import cwd.cs.client.ClientDriver;

@Component
public class TestClientDriver extends SpringEnabledTest{

    @Autowired
    private ClientDriver client;
    
    @Test
    public void testGetLocalKeys()
    {
        String[] cmd = {"-get_local_keys"};
        client.execute(cmd);
    }
    
    @Test
    public void testGetLocalMetadata()
    {
        String[] cmd = {"--get_local_metadata", "123"};
        client.execute(cmd);
    }
    
    
}
