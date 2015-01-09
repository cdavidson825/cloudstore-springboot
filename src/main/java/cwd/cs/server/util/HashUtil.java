package cwd.cs.server.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HashUtil
{
    private static Log log = LogFactory.getLog(HashUtil.class);

    /**
     * Method to generate a hex-encoded SHA-1 hashcode based on an input byte[]
     * 
     * @param inputData
     *            the input byte[] to generate the hashcode for
     * @return hex-encoded String hashcode for the input byte[]
     */
    public static String generateSHA1HashCode(byte[] inputData)
    {
        String hashCode = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(inputData);

            hashCode = Hex.encodeHexString(digest);

        }
        catch (Exception e)
        {
            log.error("generateHashCode caught the following exception: ", e);
        }
        return hashCode;
    }
    
    /**
     * method to verify two hashcodes. this method is intended to verify that the hashcode generated
     * from the raw data during storage matches the generated hashcode of the data being returned to
     * the client. This method will log errors if the hashcodes don't match.
     */
    public static boolean verifyHashCode(String savedHashcode, String generatedHashcode)
    {
        boolean hashCodesMatch = false;
        if (savedHashcode != null)
        {
            if (generatedHashcode != null)
            {
                if (savedHashcode.equals(generatedHashcode))
                {
                    hashCodesMatch = true;
                    log.debug("The saved hash code matches the generated hashcode: "
                            + savedHashcode);
                }
                else
                {
                    log.error("savedHashcode does not match generated hashcode:  "
                            + "SavedHashcode = " + savedHashcode + " and GeneratedHashcode = "
                            + generatedHashcode + " The data may have been tampered with.");
                }
            }
            else
            {
                log.error("There was a problem generating hashcode.  "
                        + "The generated hashcode is null");
            }
        }
        else
        {
            log.info("Could not verify hashcode because no hashcode was " + "saved in the metadata");
        }
        return (hashCodesMatch);
    }

}
