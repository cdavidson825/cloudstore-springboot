package cwd.cs.server.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * This class contains the internally persisted metadata associated with the
 * data stored in the cloud.
 */
@Entity
public class StorageMetadata
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long smId;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private KeyMetadata keyMetadata;
    @Column
    private String internalId;
    @Column
    private String externalId;
    @Column
    private String externalContainer;
    @Column
    private String internalHashCode;
    @Column
    private String externalHashCode;
    @Column
    private String owner;
    @Column
    private Boolean isCompressed;
    @Column
    private Boolean isEncrypted;
    @Column
    private Timestamp createDate;
    @Column
    private Timestamp lastModifiedDate;

    public StorageMetadata()
    {
        // by default all data should be encrypted and compressed.
        setIsEncrypted(true);
        setIsCompressed(true);
        createDate = new Timestamp(System.currentTimeMillis());
    }

    public Long getSmId()
    {
        return smId;
    }

    public void setSmId(Long smId)
    {
        this.smId = smId;
    }

    public String getInternalId()
    {
        return internalId;
    }

    public void setInternalId(String internalId)
    {
        this.internalId = internalId;
    }

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId(String externalId)
    {
        this.externalId = externalId;
    }

    public String getExternalContainer()
    {
        return externalContainer;
    }

    public void setExternalContainer(String externalContainer)
    {
        this.externalContainer = externalContainer;
    }

    public String getInternalHashCode()
    {
        return internalHashCode;
    }
    
    public String getExternalHashCode()
    {
        return externalHashCode;
    }

    public void setInternalHashCode(String internalHashCode)
    {
        this.internalHashCode = internalHashCode;
    }
    
    public void setExternalHashCode(String externalHashCode)
    {
        this.externalHashCode = externalHashCode;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public Timestamp getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate)
    {
        this.createDate = createDate;
    }

    public Timestamp getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean getIsCompressed()
    {
        return isCompressed;
    }

    public void setIsCompressed(Boolean isCompressed)
    {
        this.isCompressed = isCompressed;
    }

    public Boolean getIsEncrypted()
    {
        return isEncrypted;
    }

    public void setIsEncrypted(Boolean isEncrypted)
    {
        this.isEncrypted = isEncrypted;
    }

    /**
     * Method to update the appropriate StorageMetadata values based on the input StorageMetadata object.
     * @param metadata
     */
    public void updateValues(StorageMetadata metadata)
    {
        setInternalId(metadata.getInternalId());
        setExternalId(metadata.getExternalId());
        setExternalContainer(metadata.getExternalContainer());
        setInternalHashCode(metadata.getInternalHashCode());
        setExternalHashCode(metadata.getExternalHashCode());
        setOwner(metadata.getOwner());
        setIsCompressed(metadata.getIsCompressed());
        setIsEncrypted(metadata.getIsEncrypted());
        setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
        
    }
    public String summary()
    {
        return(String.format(
                "dbId: %s -- InternalID: %s, ExternalContainer: %s, ExternalID: %s",
                String.valueOf(getSmId()), getInternalId(),
                getExternalContainer(), getExternalId()));
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("StorageMetadata: \ncreateDate=");
        builder.append(createDate);
        builder.append("\nsmId=");
        builder.append(smId);
        builder.append("\nexternalContainer=");
        builder.append(externalContainer);
        builder.append("\nexternalId=");
        builder.append(externalId);
        builder.append("\ninternalHashCode=");
        builder.append(internalHashCode);
        builder.append("\nexternalHashCode=");
        builder.append(externalHashCode);
        builder.append("\ninternalId=");
        builder.append(internalId);
        builder.append("\nisCompressed=");
        builder.append(isCompressed);
        builder.append("\nisEncrypted=");
        builder.append(isEncrypted);
        builder.append("\nlastModifiedDate=");
        builder.append(lastModifiedDate);
        builder.append("\nowner=");
        builder.append(owner);
        return builder.toString();
    }

    public void setKeyMetadata(KeyMetadata keyMetadata) {
        this.keyMetadata = keyMetadata;
    }

    public KeyMetadata getKeyMetadata() {
        return keyMetadata;
    }

}
