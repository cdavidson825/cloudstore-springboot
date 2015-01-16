package cwd.cs.server.model.encryption;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cwd.cs.SpringEnabledTest;
import cwd.cs.server.model.StorageMetadata;
import cwd.cs.server.model.encryption.KeyManager;

public class TestSimpleKeyManagerImpl extends SpringEnabledTest
{
    @Autowired
    @Qualifier("Simple")
    private KeyManager keyManager;

    @Test
    public void testEncrypt()
    {
        String encryptThis = "This is the string to encrypt";
        StorageMetadata meta = new StorageMetadata();
        meta.setInternalId("1");
        byte[] encryptedBytes = keyManager.encryptData(meta, encryptThis.getBytes());
        assertNotNull(encryptedBytes);
    }

    @Test
    public void testDecrypt()
    {
        String encryptThenDecryptString = "This is the string that I will encrypt then attempt to decrypt";
        StorageMetadata meta = new StorageMetadata();
        meta.setInternalId("2");
        byte[] encryptedBytes = keyManager.encryptData(meta, encryptThenDecryptString.getBytes());
        byte[] decryptedBytes = keyManager.decryptData(meta.getKeyMetadata(), encryptedBytes);
        
        assertThat(new String(decryptedBytes), is(encryptThenDecryptString));

        
    }

}
