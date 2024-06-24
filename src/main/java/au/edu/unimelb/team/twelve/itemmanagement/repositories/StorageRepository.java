package au.edu.unimelb.team.twelve.itemmanagement.repositories;

import au.edu.unimelb.team.twelve.itemmanagement.entities.Storage;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StorageRepository extends CrudRepository<Storage, UUID> {
}
