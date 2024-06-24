package au.edu.unimelb.team.twelve.itemmanagement.repositories;

import au.edu.unimelb.team.twelve.itemmanagement.entities.Storage;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TagRepository extends CrudRepository<Tag, UUID> {
}
