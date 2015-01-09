package cwd.cs.client.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import joptsimple.OptionSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

import cwd.cs.server.manager.retrieval.RetrievalManager;
import cwd.cs.server.manager.storage.StorageManager;

public class DataCommand
{

    private static Log log = LogFactory.getLog(DataCommand.class);
    // 10 MB max
    protected static final int MAX_UPLOAD_BYTES = 10240000;

    protected static CommandResponse saveData(StorageManager storageManager, OptionSet options)
    {
        String localPath = null;
        String localKey = null;

        byte[] inputFileContent = null;

        CommandResponse response = null;
        if (!validateCommands(
                Arrays.asList(CommandProcessor.SAVE_CMD, CommandProcessor.LOCAL_FILE_OPTION),
                options))
        {
            response =
                    new CommandResponse(false, String.format(
                            "Either localKey %s or localPath %s were null", localKey, localPath));
        }
        else
        {
            localPath = (String) options.valueOf(CommandProcessor.LOCAL_FILE_OPTION);
            localKey = (String) options.valueOf(CommandProcessor.SAVE_CMD);
            try
            {
                /*
                 * Read inputFileContents
                 */
                FileInputStream fin = new FileInputStream(localPath);
                inputFileContent = ByteStreams.toByteArray(fin);

                if (inputFileContent.length > MAX_UPLOAD_BYTES) { throw new RuntimeException(
                        "File too large.  This client has a restriction of only allowing uploads of files smaller than "
                                + MAX_UPLOAD_BYTES + "  bytes.  Your file is "
                                + inputFileContent.length + " bytes"); }

                boolean saved = storageManager.saveData(localKey, inputFileContent);
                response = new CommandResponse(saved, "Data saved with response code: " + saved);
            }
            catch (IOException e)
            {
                log.error("Caught the following exception while saving", e);
                response = new CommandResponse(false, "Caught exception while saving");
            }
        }
        return response;
    }


    protected static CommandResponse getData(RetrievalManager retrievalManager, OptionSet options)
    {
        String localPath = null;
        String localKey = null;
        CommandResponse response = null;
        if (!validateCommands(
                Arrays.asList(CommandProcessor.GET_CMD, CommandProcessor.LOCAL_FILE_OPTION),
                options))
        {
            response =
                    new CommandResponse(false, String.format(
                            "Either localKey %s or localPath %s were null", localKey, localPath));
        }
        else
        {

            localPath = (String) options.valueOf(CommandProcessor.LOCAL_FILE_OPTION);
            localKey = (String) options.valueOf(CommandProcessor.GET_CMD);

            try
            {
                byte[] dataBytes = retrievalManager.getData(localKey);
                Files.write(dataBytes, new File(localPath));
                response = new CommandResponse(true, "Data retrived and stored at " + localPath);

            }
            catch (IOException e)
            {
                log.error("Caught the following exception while saving", e);
                response = new CommandResponse(false, "Caught exception while saving");
            }
        }

        return response;
    }

    protected static CommandResponse deleteData(StorageManager storageManager, OptionSet options)
    {

        if (options.hasArgument(CommandProcessor.DELETE_CMD))
        {
            String localKey = (String) options.valueOf(CommandProcessor.DELETE_CMD);
            boolean deleted = storageManager.deleteData(localKey);
            return new CommandResponse(deleted, String.format(
                    "Data for localkey %s removed with response code:", localKey, deleted));
        }
        else
        {
            return new CommandResponse(false, "Local Key not specified");
        }

    }

    private static boolean validateCommands(List<String> commands, OptionSet options)
    {
        for (String command : commands)
        {
            if (!options.has(command) || !options.hasArgument(command)) { return false; }
        }
        return true;
    }



}
