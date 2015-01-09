package cwd.cs.server.cloud;

import java.util.Map;

/**
 * This class encapsulates the content being stored or retrieved from the Cloud
 * storage. This includes the key (String), the binary content (byte[]), as well
 * as associated metadata (Map<String, String>) to be stored.
 */
public class CloudData
{

    private final String key;
    private final byte[] data;
    private final Map<String, String> metadata;

    public CloudData(String key, byte[] data, Map<String, String> metadata)
    {
        this.key = key;
        this.data = data;
        this.metadata = metadata;
    }

    public String getKey()
    {
        return key;
    }

    public byte[] getData()
    {
        return data;
    }

    public Map<String, String> getMetadata()
    {
        return metadata;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CloudData [key=");
        builder.append(key);
        builder.append(", metadata=");
        builder.append(metadata);
        builder.append("data length=");
        if (data != null)
        {
            builder.append(data.length);
        }
        else
        {
            builder.append("null");

        }

        builder.append("]");
        return builder.toString();
    }

}
