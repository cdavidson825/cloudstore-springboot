package cwd.cs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;

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

    @RequestMapping(value = "/", method =
    {RequestMethod.GET, RequestMethod.POST})
    public List<StorageMetadata> listAll()
    {
        return (metadataRepo.findAll());
    }

    @RequestMapping(value = "/cloud_keys", method =
    {RequestMethod.GET, RequestMethod.POST})
    public List<String> listAllCloud()
    {
        return (retrievalManager.getCloudKeys());
    }
    
    @RequestMapping(value = "/get_meta", method =
    {RequestMethod.GET, RequestMethod.POST})
    public StorageMetadata getByInternalIdPost(@RequestParam String internalId)
    {
        return (getByInternalId(internalId));
    }
    
    @RequestMapping(value = "/get_cloud_meta", method =
    {RequestMethod.GET, RequestMethod.POST})
    public Map<String,String> getCloudMetadata(@RequestParam String cloudKey)
    {
        return (retrievalManager.getCloudMetadata(cloudKey));
    }

    @RequestMapping(value = "/get_meta/{internalId}", method = RequestMethod.GET)
    public StorageMetadata getByInternalIdGet(@PathVariable String internalId)
    {
        return (getByInternalId(internalId));
    }

    @RequestMapping(value = "/delete", method =
    {RequestMethod.POST, RequestMethod.DELETE})
    public boolean deleteByInternalIdRequest(@RequestParam String internalId)
    {
        return (deleteByInternalId(internalId));
    }

    @RequestMapping(value = "/delete/{internalId}", method =
    {RequestMethod.POST, RequestMethod.DELETE})
    public boolean deleteByInternalIdPathVariable(@PathVariable String internalId)
    {
        return (deleteByInternalId(internalId));
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public StorageMetadata saveData(@RequestParam(value = "internalId") String internalId, @RequestParam(
            value = "input_file") MultipartFile inputFile)
    {
        System.out.println("Save called for internalID = " + internalId);

        StorageMetadata savedMetadata = null;
        try
        {
            savedMetadata = storageManager.saveData(internalId, inputFile.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return savedMetadata;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public String getDataSaveOnServer(@RequestParam(value = "internalId") String internalId,
            @RequestParam(value = "saveAs") String saveAs)
    {
        byte[] dataBytes = retrievalManager.getData(internalId);
        File file = new File(saveAs);
        String returnString;
        try
        {
            Files.write(dataBytes, file);
            returnString = String.format("Your file is stored at: %s", file.getAbsolutePath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            returnString = "There was an error saving: " + e.getMessage();
        }
        return returnString;
    }

    private StorageMetadata getByInternalId(String internalId)
    {
        StorageMetadata metadata = metadataRepo.findByInternalId(internalId);
        return metadata;
    }


    private boolean deleteByInternalId(String internalId)
    {
        return (storageManager.deleteData(internalId));
    }

}
