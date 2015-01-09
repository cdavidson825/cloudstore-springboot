package cwd.cs.server.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GzipUtil
{
    private static Log log = LogFactory.getLog(GzipUtil.class);

    public static byte[] compress(byte[] uncompressedData)
    {

        log.debug("Compressing data...");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try
        {
            GZIPOutputStream out = new GZIPOutputStream(byteArrayOutputStream);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    uncompressedData);
            byte[] buf = new byte[1024];
            int len;
            while ((len = byteArrayInputStream.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            byteArrayInputStream.close();
            out.finish();
            out.close();
        }
        catch (IOException e)
        {
            log.error("compress caught the following exception ", e);
        }

        return (byteArrayOutputStream.toByteArray());

    }

    public static byte[] decompress(byte[] compressedData)
    {
        log.debug("Decompress data...");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
            GZIPInputStream in = new GZIPInputStream(bais);
            // Transfer bytes from the compressed file to the output file
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            // Close the file and stream
            in.close();
            out.close();
        }
        catch (IOException e)
        {
            log.error("decompress caught the following exception ", e);

        }
        return (out.toByteArray());

    }
}
