package cwd.cs.server.cloud.aws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.common.io.ByteStreams;

import cwd.cs.server.cloud.CloudData;
import cwd.cs.server.cloud.CloudService;

/**
 * This class interfaces the Amazon Simple Storage Service (S3) to store and retrieve binary data.
 * This class uses the Amazon Java SDK to interface S3. The S3 configuration comes from the
 * AmazonS3Configuration object and is loaded via Spring.
 * 
 */
public class S3Client implements CloudService
{

    private static Log log = LogFactory.getLog(S3Client.class);

    private final S3Configuration s3Config;

    private final AmazonS3 s3;

    public S3Client(S3Configuration s3Config)
    {

        if (s3Config == null)
        {
            String msg =
                    "S3Config not initialized, therefore cannot connect to AmazonS3.. Please verify the Spring configuration";
            log.fatal(msg);

            throw new RuntimeException(msg);
        }
        else
        {
            this.s3Config = s3Config;
        }
        BasicAWSCredentials awsCreds =
                new BasicAWSCredentials(s3Config.getAccessKey(), s3Config.getSecretKey());
        s3 = new AmazonS3Client(awsCreds);

        /*
         * If the AWS bucket does not already exist, create it.
         */
        if (!s3.listBuckets().contains(s3Config.getBucketName()))
        {
            s3.createBucket(s3Config.getBucketName());
        }
    }

    @Override
    public List<String> getKeys()
    {
        List<String> keys = new ArrayList<String>();

        ObjectListing objListing = s3.listObjects(s3Config.getBucketName());
        List<S3ObjectSummary> objSummaryList = objListing.getObjectSummaries();
        for (S3ObjectSummary objSummary : objSummaryList)
        {
            keys.add(objSummary.getKey());
        }

        return keys;
    }

    @Override
    public Map<String, String> getMetadata(String key)
    {
        Map<String, String> metadata = null;
        log.debug("getMetadata called for key = " + key);

        if (key != null)
        {
            ObjectMetadata objMetadata = s3.getObjectMetadata(s3Config.getBucketName(), key);
            metadata = objMetadata.getUserMetadata();

            if (objMetadata.getRawMetadata() != null)
            {
                for (String rawKey : objMetadata.getRawMetadata().keySet())
                {
                    Object objValue = objMetadata.getRawMetadata().get(rawKey);
                    if (objValue != null)
                    {
                        metadata.put(rawKey, objValue.toString());
                    }
                }
            }
        }
        return metadata;
    }

    @Override
    public CloudData getData(String key)
    {
        CloudData cdnData = null;
        log.debug("getData called for key = " + key);
        if (key != null)
        {
            GetObjectRequest request = new GetObjectRequest(s3Config.getBucketName(), key);
            S3Object result = s3.getObject(request);

            byte[] dataContent;
            try
            {
                dataContent = ByteStreams.toByteArray(result.getObjectContent());

                HashMap<String, String> metadata = new HashMap<String, String>();
                metadata.putAll(result.getObjectMetadata().getUserMetadata());
                cdnData = new CloudData(key, dataContent, metadata);

            } catch (IOException ioe)
            {
                log.error("getData caught the following exception: ", ioe);
                cdnData = null;
            }
        }

        return (cdnData);
    }

    @Override
    public String saveData(CloudData data)
    {
        String dataLocation = null;
        log.debug("saveData called for " + data);
        if (data != null)
        {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(data.getData().length);

            metadata.setUserMetadata(data.getMetadata());
            PutObjectRequest request =
                    new PutObjectRequest(s3Config.getBucketName(), data.getKey(),
                            new ByteArrayInputStream(data.getData()), metadata);
            s3.putObject(request);
            dataLocation = s3Config.getBucketName();
        }

        return dataLocation;
    }

    @Override
    public boolean deleteData(String key)
    {
        boolean isDeleted = false;
        log.debug("deleteData called for " + key);

        if (key != null)
        {

            DeleteObjectRequest request = new DeleteObjectRequest(
                    s3Config.getBucketName(), key);
            s3.deleteObject(request);
            isDeleted = true;
        }

        return isDeleted;
    }
    
    @Override
    public String getCloudProvider()
    {
        return "AMAZON";
    }

}
