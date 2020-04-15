package de.demmer.dennis.odrauserservice.repository;

import de.demmer.dennis.odrauserservice.model.meta.Metadata;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MetadataRepository extends CrudRepository<Metadata, Long> {

    Optional<Metadata> findByMediaId(long mediaId);
}
