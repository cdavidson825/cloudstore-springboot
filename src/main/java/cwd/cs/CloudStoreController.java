package cwd.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cwd.cs.server.model.StorageMetadata;
import cwd.cs.server.repository.StorageMetadataRepo;

@RestController
@RequestMapping("/cloudstore")
public class CloudStoreController {
    
    @Autowired
    StorageMetadataRepo metadataRepo;
    
    @RequestMapping("/")
    public List<StorageMetadata> listMetadata()
    {
        return(metadataRepo.findAll());
    }
    
    @RequestMapping(value = "/get/{internalId}")
    public StorageMetadata getByInternalId(@PathVariable String internalId)
    {
           StorageMetadata metadata = metadataRepo.findByInternalId(internalId);
           return metadata;
    }
    
}
