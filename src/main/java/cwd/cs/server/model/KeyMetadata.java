package cwd.cs.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class contains the relationship between the internalId of data and the
 * private key and algorithm used to encrypt the data. This is the object that
 * is persisted in the local datastore.
 */
@Entity
public class KeyMetadata
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long kmId;
    @Column
    private String privateKey;
    @Column
    private String privateKeyAlgorithm;

    public KeyMetadata()
    {

    }

    public KeyMetadata(String privateKey, String keyAlgorithm)
    {
        this.privateKey = privateKey;
        this.privateKeyAlgorithm = keyAlgorithm;
    }

    public long getKmId()
    {
        return kmId;
    }

    public void setKmId(long kmId)
    {
        this.kmId = kmId;
    }

    public String getPrivateKey()
    {
        return privateKey;
    }

    public void setPrivateKey(String privateKey)
    {
        this.privateKey = privateKey;
    }

    public String getPrivateKeyAlgorithm()
    {
        return privateKeyAlgorithm;
    }

    public void setPrivateKeyAlgorithm(String keyAlgorithm)
    {
        this.privateKeyAlgorithm = keyAlgorithm;
    }

    /**
     * Method to update the appropriate KeyEntry values based on the
     * input StorageMetadata object.
     * 
     * @param entry
     */
    public void updateValues(KeyMetadata entry)
    {
        setPrivateKeyAlgorithm(entry.getPrivateKeyAlgorithm());
        setPrivateKey(entry.getPrivateKey());
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("KeyEntry [kmId=");
        builder.append(kmId);
        builder.append(", privateKeyAlgorithm=");
        builder.append(privateKeyAlgorithm);
        builder.append(", privateKey=");
        builder.append(privateKey);
        builder.append("]");
        return builder.toString();
    }

}

