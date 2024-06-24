package au.edu.unimelb.team.twelve.itemmanagement.repositories;

import au.edu.unimelb.team.twelve.itemmanagement.entities.Category;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoryRepository extends CrudRepository<Category, UUID> {
}
