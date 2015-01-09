package cwd.cs.server.model.encryption;

import cwd.cs.server.model.KeyMetadata;
import cwd.cs.server.model.StorageMetadata;

/**
 * Public KeyManager interface that all providers must implement.
 */
public interface KeyManager
{

    /**
     * Method to encrypt data
     * 
     * @param key
     *            The key associated with the data being encrypted
     * @param inputData
     *            byte[] of data to encrypt
     * @return byte[] of encrypted data or null byte[] if an error occurred
     *         during the encrypt process.
     */
    public byte[] encryptData(StorageMetadata storageMetadata, byte[] inputData);

    /**
     * Method to decrypt data
     * 
     * @param KeyMetadata
     *            The key metadata associated with the store data to be decrypted.
     * @param encryptedData
     *            byte[] of encrypted data
     * @return byte[] of decrypted data or null byte[] if an error occurred
     *         during the decrypt process.
     */
    public byte[] decryptData(KeyMetadata keyMetadata, byte[] encryptedData);
}
