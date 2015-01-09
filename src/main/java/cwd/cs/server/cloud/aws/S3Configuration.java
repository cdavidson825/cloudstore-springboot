package cwd.cs.server.cloud.aws;

import cwd.cs.server.cloud.CloudConfiguration;

/**
 * This class contains all the configuration associated with the Amazon S3 Storage
 * service. This class is configured via Spring.
 * 
 */
public class S3Configuration extends CloudConfiguration
{
    
    /*
     * The access key for the Amazon S3 service
     */
    private String accessKey;
    
    /*
     * The secret key for the Amazon S3 service.
     */
    private String secretKey;
    
    /*
     * The namespace to store all the binary objects in.
     */
    private String bucketName;

    public String getAccessKey()
    {
        return accessKey;
    }

    public void setAccessKey(String accessKey)
    {
        this.accessKey = accessKey;
    }

    public String getSecretKey()
    {
        return secretKey;
    }

    public void setSecretKey(String secretKey)
    {
        this.secretKey = secretKey;
    }

    public void setBucketName(String bucketName)
    {
        this.bucketName = bucketName;
    }

    public String getBucketName()
    {
        if (null != bucketName)
        {
            return bucketName;
        }
        else
        {
            return getDefaultContainerString();
        }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("S3Configuration [accessKey=");
        builder.append(accessKey);
        builder.append(", secretKey=");
        builder.append(secretKey);
        builder.append(", bucketName=");
        builder.append(bucketName);
        builder.append("]");
        return builder.toString();
    }
    
    
}
