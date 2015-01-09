package cwd.si.server.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import cwd.cs.server.model.KeyMetadata;
import cwd.cs.server.model.StorageMetadata;
import cwd.cs.server.repository.StorageMetadataRepo;
import cwd.si.SpringEnabledTest;

@Profile("test")
public class TestStorageMetadata extends SpringEnabledTest
{

    @Autowired
    StorageMetadataRepo metadataRepo;
    
    @Test
    public void TestStorageMetadataSave()
    {
        StorageMetadata storageMetadata = new StorageMetadata();
        storageMetadata.setExternalContainer("Testing");
        storageMetadata.setExternalHashCode("00000");
        storageMetadata.setInternalHashCode("11111");
        storageMetadata.setKeyMetadata(new KeyMetadata("testing","testAlg"));
        
        StorageMetadata savedObj = metadataRepo.saveAndFlush(storageMetadata);
        
        assertNotNull(savedObj);
        assertThat(savedObj.getInternalHashCode(), is("11111"));
        
        List<StorageMetadata> dbItems = metadataRepo.findAll();
        
        assertThat(dbItems.size(), is(1));
        assertThat(dbItems.get(0).getInternalHashCode(), is("11111"));
        assertThat(dbItems.get(0).getKeyMetadata().getPrivateKey(), is("testing"));
        System.out.println("DBItem = " + dbItems.get(0));
    }
    
    
    
}
