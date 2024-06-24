package au.edu.unimelb.team.twelve.itemmanagement.repositories;

import au.edu.unimelb.team.twelve.itemmanagement.entities.BookUser;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Item;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Tag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ItemRepository extends CrudRepository<Item, UUID> {
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "delete from item", nativeQuery = true)
    void deleteAll();

    @Query(value = "select i from Item i where (i.owner = ?1 or ?1 member of i.shared)")
    Iterable<Item> searchAllByOwner(BookUser owner);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "delete from Item i where (i.owner = ?1)")
    void deleteAllByOwner(BookUser owner);

    @Query(value = "select i from Item i where ?1 member of i.shared")
    Iterable<Item> listAllSharedWith(BookUser owner);

    @Query(value =
            "select i from Item i where (i.owner = ?1 or ?1 member of i.shared) and (i.place.location like " + "%?2"
                    + "%) and (i.name like %?3%) and (i.description like %?4%) and (?5 member of i.tags)")
    Iterable<Item> searchAllByOwnerAnd(BookUser owner, String location, String name, String description, Tag tag);

    @Query(value =
            "select i from Item i where (i.owner = ?1 or ?1 member of i.shared) and ((i.place.location like %?2%) " +
                    "or (i.name like %?3%) or (i" + ".description like %?4%) or (?5 member of i.tags))")
    Iterable<Item> searchAllByOwnerOr(BookUser owner, String location, String name, String description, Tag tag);

    @Query(value =
            "select i from Item i where (i.owner = ?1 or ?1 member of i.shared) and (i.place.location like " + "%?2"
                    + "%) and (i.name like %?3%) and (i.description like %?4%)")
    Iterable<Item> searchAllByOwnerAnd(BookUser owner, String location, String name, String description);

    @Query(value =
            "select i from Item i where (i.owner = ?1 or ?1 member of i.shared) and ((i.place.location like %?2%) " +
                    "or (i.name like %?3%) or (i" + ".description like %?4%))")
    Iterable<Item> searchAllByOwnerOr(BookUser owner, String location, String name, String description);
}
