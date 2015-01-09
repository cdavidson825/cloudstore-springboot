package cwd.cs.client.command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cwd.cs.server.manager.retrieval.RetrievalManager;
import cwd.cs.server.manager.storage.StorageManager;

@Component
public class CommandProcessor
{

    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(CommandProcessor.class);

    protected final static String GET_LOCAL_KEYS_CMD = "get_local_keys";
    protected final static String GET_CLOUD_KEYS_CMD = "get_cloud_keys";
    protected final static String GET_LOCAL_METADATA_CMD = "get_local_metadata";
    protected final static String GET_CLOUD_METADATA_CMD = "get_cloud_metadata";
    protected final static String SAVE_CMD = "save";
    protected final static String GET_CMD = "get";
    protected final static String DELETE_CMD = "delete";
    protected final static String LOCAL_PATH_OPTION = "local_path";

    @Autowired
    private StorageManager storageManager = null;
    @Autowired
    private RetrievalManager retrievalManager = null;

    private OptionParser optionsParser;

    // hack variable to get around append bug in OptionParser printHelp method.
    private String helpString = null;

    public CommandProcessor()
    {
        initCommandLineOptionParser();
    }

    private void initCommandLineOptionParser()
    {
        optionsParser = new OptionParser();
        optionsParser.accepts(GET_LOCAL_KEYS_CMD, "Get ALL keys stored locally");
        optionsParser.accepts(GET_CLOUD_KEYS_CMD, "Get ALL keys stored in the cloud");
        optionsParser.accepts(GET_LOCAL_METADATA_CMD, "Get local metadata for provided ID")
                .withRequiredArg();
        optionsParser.accepts(GET_CLOUD_METADATA_CMD, "Get cloud metadata for provided ID")
                .withRequiredArg();
        optionsParser
                .accepts(
                        SAVE_CMD,
                        "Save data at provided ID --local_path required to indicate local input fully-qualified file location  ")
                .withRequiredArg();
        optionsParser
                .accepts(
                        GET_CMD,
                        "Get data for provided ID --local_path required to indicate local output fully-qualified file location")
                .withRequiredArg();
        optionsParser.accepts(DELETE_CMD, "Delete data for provided ID").withRequiredArg();
        optionsParser.accepts(LOCAL_PATH_OPTION).withRequiredArg();
        optionsParser.acceptsAll(Arrays.asList("help", "?"), "show help").forHelp();

        /*
         * hack to get around "append bug" in OptionParser printHelp method.
         */
        try
        {
            ByteArrayOutputStream helpBAOS = new ByteArrayOutputStream();
            optionsParser.printHelpOn(helpBAOS);
            helpString = new String(helpBAOS.toByteArray());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public CommandResponse execute(String[] inputCommand)
    {
        CommandResponse response = null;

        OptionSet options = optionsParser.parse(inputCommand);
        if (options.has(GET_LOCAL_KEYS_CMD))
        {
            response = new GetKeysCommand().getLocalKeys(retrievalManager);
        }
        else if (options.has(GET_CLOUD_KEYS_CMD))
        {
            response = GetKeysCommand.getCloudKeys(retrievalManager);
        }
        else if (options.has(GET_LOCAL_METADATA_CMD))
        {
            response = GetMetadataCommand.getLocalMetadata(retrievalManager, options);
        }
        else if (options.has(GET_CLOUD_METADATA_CMD))
        {
            response = GetMetadataCommand.getCloudMetadata(retrievalManager, options);
        }
        else if (options.has(SAVE_CMD))
        {
            response = DataCommand.saveData(storageManager, options);
        }
        else if (options.has(GET_CMD))
        {
            response = new CommandResponse(false, "Get Not implemented yet...");

        }
        else if (options.has(DELETE_CMD))
        {
            response = new CommandResponse(false, "Delete Not implemented yet...");
        }
        else if (options.has("help") || options.has("?"))
        {
            response = new CommandResponse(true, helpString);
        }
        else
        {
            response = new CommandResponse(false, "Unknown command:\n " + helpString);
        }

        return response;
    }
}
