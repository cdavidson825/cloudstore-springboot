package cwd.cs.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cwd.cs.server.model.KeyMetadata;

public interface KeyMeatadataRepo extends JpaRepository<KeyMetadata, Long>
{

}
