package cwd.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cwd.cs.sample.Greeting;
import cwd.cs.server.model.KeyMetadata;
import cwd.cs.server.model.StorageMetadata;
import cwd.cs.server.repository.StorageMetadataRepo;

@RestController
public class StoreItController {
    
    @Autowired
    @Qualifier("Normal")
    Greeting greeting = null;
    
    @Autowired
    StorageMetadataRepo metadataRepo;
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot! " + greeting.sayHello();
    }
    
    @RequestMapping("/metadata")
    public List<StorageMetadata> listMetadata()
    {
        return(metadataRepo.findAll());
    }
    
    @RequestMapping("/metadata/add")
    public String addMetadata()
    {
        StorageMetadata storageMetadata = new StorageMetadata();
        storageMetadata.setExternalContainer("Testing_metadata");
        storageMetadata.setExternalHashCode("22222");
        storageMetadata.setInternalHashCode("33333");
        storageMetadata.setKeyMetadata(new KeyMetadata("add","addAlg"));
        
        StorageMetadata savedObj = metadataRepo.save(storageMetadata);
        return(savedObj.toString());
    }
    
    
    
}
