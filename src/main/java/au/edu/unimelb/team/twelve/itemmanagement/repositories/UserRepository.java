package au.edu.unimelb.team.twelve.itemmanagement.repositories;

import au.edu.unimelb.team.twelve.itemmanagement.entities.BookUser;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<BookUser, UUID> {

    @Query(value = "select u from BookUser u where u.name like %?1%")
    Iterable<BookUser> findByName(String name);

    @Query(value = "select u from BookUser u where u.name = ?1")
    BookUser exactMatch(String name);

    @Query(value = "select u from BookUser u where u.token = ?1")
    BookUser fromToken(String token);
}
