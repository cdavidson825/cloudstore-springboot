package cwd.cs.server.cloud.google;

import cwd.cs.server.cloud.CloudConfiguration;

public class GCSConfiguration extends CloudConfiguration
{
    /*
     * The namespace to store all the binary objects in.
     */
    private String bucketName;

    private String jsonOAuth2File;
    private String authTokenTempDirectory;

    public String getBucketName()
    {
        return bucketName;
    }

    public void setBucketName(String bucketName)
    {
        this.bucketName = bucketName;
    }

    public String getAuthTokenTempDirectory()
    {
        return authTokenTempDirectory;
    }

    public void setAuthTokenTempDirectory(String authTokenTempDirectory)
    {
        this.authTokenTempDirectory = authTokenTempDirectory;
    }

    public String getJsonOAuth2File()
    {
        return jsonOAuth2File;
    }

    public void setJsonOAuth2File(String jsonOAuth2File)
    {
        this.jsonOAuth2File = jsonOAuth2File;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GCSConfiguration [bucketName=");
        builder.append(bucketName);
        builder.append(", jsonOAuth2File=");
        builder.append(jsonOAuth2File);
        builder.append(", authTokenTempDirectory=");
        builder.append(authTokenTempDirectory);
        builder.append("]");
        return builder.toString();
    }
    
    
    
    

}
