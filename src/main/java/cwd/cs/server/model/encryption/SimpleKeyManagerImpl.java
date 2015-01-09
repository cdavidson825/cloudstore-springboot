package cwd.cs.server.model.encryption;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cwd.cs.server.model.KeyMetadata;
import cwd.cs.server.model.StorageMetadata;

/**
 * This class implements the KeyManager interface and provides a fairly
 * simplistic encryption/decryption algorithm based on ASE. 
 */
@Component
@Qualifier("Simple")
public class SimpleKeyManagerImpl implements KeyManager
{

    private static Log log = LogFactory.getLog(SimpleKeyManagerImpl.class);

    private static final String ALGORITHM = "AES";

    /*
     *16 bytes = 128-bit key length
     */
    private static final int REQUIRED_KEY_LENGTH = 16;

    public SimpleKeyManagerImpl()
    {
        
    }

    @Override
    public byte[] encryptData(StorageMetadata storageMetadata, byte[] inputData)
    {
        byte[] encryptedBytes = null;
        try
        {
            Key encryptionKey = getJCEKey(null, ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, encryptionKey);
            encryptedBytes = c.doFinal(inputData);
            storageMetadata.setIsEncrypted(true);
            
            KeyMetadata keyMetadata = new KeyMetadata(
                    new String(encryptionKey.getEncoded()), ALGORITHM);
            storageMetadata.setKeyMetadata(keyMetadata);

        }
        catch (Exception e)
        {
            log.error("Caught exception in encrypt Data", e);
        }
        return (encryptedBytes);
    }

    @Override
    public byte[] decryptData(KeyMetadata keyEntry, byte[] encryptedData)
    {
        byte[] decryptedBytes = null;
        try
        {
            if (encryptedData != null && keyEntry != null)
            {

                Key decryptionKey = getJCEKey(keyEntry.getPrivateKey(),
                        keyEntry.getPrivateKeyAlgorithm());
                Cipher c = Cipher.getInstance(keyEntry
                        .getPrivateKeyAlgorithm());
                c.init(Cipher.DECRYPT_MODE, decryptionKey);

                decryptedBytes = c.doFinal(encryptedData);
                
            }
            else
            {
                log.warn("decryptData received request to decrypt null byte[]");
            }
        }
        catch (Exception e)
        {
            log.error("Caught exception in decrypt Data", e);
        }

        return decryptedBytes;
    }

    private Key getJCEKey(String keyString, String algorithm) throws Exception
    {
        /*
         * if the input key is null, then generate a randomized 16 character
         * string, otherwise, use the input key
         */
        if (keyString == null)
        {
            keyString = RandomStringUtils.randomAlphabetic(REQUIRED_KEY_LENGTH);
        }
        Key key = new SecretKeySpec(keyString.getBytes(), algorithm);
        return key;
    }
}
