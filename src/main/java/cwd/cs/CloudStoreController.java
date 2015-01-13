package cwd.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwd.cs.server.manager.retrieval.RetrievalManager;
import cwd.cs.server.manager.storage.StorageManager;
import cwd.cs.server.model.StorageMetadata;
import cwd.cs.server.repository.StorageMetadataRepo;

@RestController
@RequestMapping("/cloudstore")
public class CloudStoreController
{
    @Autowired
    StorageMetadataRepo metadataRepo;
    @Autowired
    StorageManager storageManager;
    @Autowired
    RetrievalManager retrievalManager;

    @RequestMapping("/")
    public List<StorageMetadata> listAll()
    {
        return (metadataRepo.findAll());
    }

    @RequestMapping(value = "/get_meta/{internalId}")
    public StorageMetadata getByInternalId(@PathVariable String internalId)
    {
        StorageMetadata metadata = metadataRepo.findByInternalId(internalId);
        return metadata;
    }

    @RequestMapping(value = "/delete/{internalId}", method=RequestMethod.DELETE)
    public boolean deleteByInternalId(@PathVariable String internalId)
    {
        return (storageManager.deleteData(internalId));
    }

}
