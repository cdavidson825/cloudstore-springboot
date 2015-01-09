package cwd.cs.client.command;

import java.util.List;

import cwd.cs.client.util.LogUtil;
import cwd.cs.server.manager.retrieval.RetrievalManager;

public class GetKeysCommand
{
     
    protected CommandResponse getLocalKeys(RetrievalManager retrievalManager)
    {
        List<String> localKeys = retrievalManager.getLocalKeys();
        return new CommandResponse(true, LogUtil.buildLogString("Local Key List", localKeys));
    }

    protected static CommandResponse getCloudKeys(RetrievalManager retrievalManager)
    {
        List<String> localKeys = retrievalManager.getCloudKeys();
        return new CommandResponse(true, LogUtil.buildLogString("Cloud Key List", localKeys));
    }
}
