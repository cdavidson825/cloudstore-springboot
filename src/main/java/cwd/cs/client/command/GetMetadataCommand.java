package cwd.cs.client.command;

import java.util.Map;

import joptsimple.OptionSet;
import cwd.cs.client.util.LogUtil;
import cwd.cs.server.manager.retrieval.RetrievalManager;

public class GetMetadataCommand
{

    protected static CommandResponse getLocalMetadata(RetrievalManager retrievalManager,OptionSet options)
    {
        if (options.hasArgument(CommandProcessor.GET_LOCAL_METADATA_CMD))
        {
            String key = (String) options.valueOf(CommandProcessor.GET_LOCAL_METADATA_CMD);
            String localMetadata = retrievalManager.getLocalMetadata(key);
            return new CommandResponse(true, LogUtil.buildLogString(
                    "Getting Local Metadata for key: " + key, localMetadata));
        }
        else
        {
            return new CommandResponse(false, "Key not specified");
        }
    }
    
    protected static CommandResponse getCloudMetadata(RetrievalManager retrievalManager, OptionSet options)
    {
        if (options.hasArgument(CommandProcessor.GET_CLOUD_METADATA_CMD))
        {
            String key = (String) options.valueOf(CommandProcessor.GET_CLOUD_METADATA_CMD);
            Map<String, String> cloudMetadata = retrievalManager.getCloudMetadata(key);
            return new CommandResponse(true, LogUtil.buildLogString(
                    "Getting Cloud Metadata for key: " + key, cloudMetadata));
        }
        else
        {
            return new CommandResponse(false, "Key not specified");
        }
    }
    
}
