package cwd.cs.client.command;

import java.io.FileInputStream;
import java.io.IOException;

import joptsimple.OptionSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.io.ByteStreams;

import cwd.cs.server.manager.storage.StorageManager;

public class DataCommand
{

    private static Log log = LogFactory.getLog(DataCommand.class);
    // 10 MB max
    protected static final int MAX_UPLOAD_BYTES = 10240000;

    protected static CommandResponse saveData(StorageManager storageManager,
            OptionSet options)
    {
        String localPath = null;
        String key = null;

        byte[] inputFileContent = null;

        if (options.has(CommandProcessor.LOCAL_PATH_OPTION)
                && options.hasArgument(CommandProcessor.LOCAL_PATH_OPTION))
        {
            localPath = (String) options.valueOf(CommandProcessor.LOCAL_PATH_OPTION);
        }
        if (options.has(CommandProcessor.SAVE_CMD)
                && options.hasArgument(CommandProcessor.SAVE_CMD))
        {
            key = (String) options.valueOf(CommandProcessor.SAVE_CMD);
        }
        if (localPath == null || key == null)
        {
            return new CommandResponse(false, String.format(
                    "Either key %s or localPath %s were null", key, localPath));
        }
        else
        {
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

                boolean saved = storageManager.saveData(key, inputFileContent);
                return new CommandResponse(saved, "Data saved with response code: " + saved);
            }
            catch (IOException e)
            {
                log.error("Caught the following exception while saving", e);
                return new CommandResponse(false, "Caught exception while saving");
            }
        }
    }



}
