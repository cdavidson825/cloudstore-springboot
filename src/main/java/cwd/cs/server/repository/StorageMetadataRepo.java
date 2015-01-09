package cwd.cs.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cwd.cs.server.model.StorageMetadata;

public interface StorageMetadataRepo extends JpaRepository<StorageMetadata, Long>
{
    public StorageMetadata findByInternalId(String internalId);
}
