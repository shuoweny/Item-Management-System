package au.edu.unimelb.team.twelve.itemmanagement.repositories;

import au.edu.unimelb.team.twelve.itemmanagement.entities.Book;
import au.edu.unimelb.team.twelve.itemmanagement.entities.BookUser;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Category;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Tag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface BookRepository extends CrudRepository<Book, UUID> {
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "delete from Book i where (i.owner = ?1)")
    void deleteAllByOwner(BookUser owner);

    @Query(value = "select i from Book i where (i.owner = ?1 or ?1 member of i.shared)")
    Iterable<Book> searchAllByOwner(BookUser owner);

    @Query(value = "select b from Book b where ?1 member of b.shared")
    Iterable<Book> listAllSharedWith(BookUser owner);

    @Query(value =
            "select i from Book i where (i.owner = ?1 or ?1 member of i.shared) and i.place.location like " + "%?2"
                    + "% and i.name like %?3% and i.description like %?4% and i.isbn like %?5%  and i.author like " + "%?6% and " + "i.publisher like %?7%")
    Iterable<Book> searchAllByOwnerAnd(BookUser owner, String location, String name, String description, String isbn,
                                       String author, String publisher);

    @Query(value = "select i from Book i where (i.owner = ?1 or ?1 member of i.shared) and ((" + "i" + ".place" +
            ".location " + "like %?2%) or (i.name like %?3%) or (i" + ".description like %?4%)or(i.isbn like %?5% ) " + "or(i" + ".author " + "like %?6%) or (i.publisher like %?7%))")
    Iterable<Book> searchAllByOwnerOr(BookUser owner, String location, String name, String description, String isbn,
                                      String author, String publisher);

    @Query(value =
            "select i from Book i where (i.owner = ?1 or ?1 member of i.shared) and i.place.location like " + "%?2"
                    + "% and i.name like %?3% and i.description like %?4% and i.isbn like %?5%  and i.author like " + "%?6% and " + "i.publisher like %?7% and ?8 member of i.tags")
    Iterable<Book> searchAllByOwnerAnd(BookUser owner, String location, String name, String description, String isbn,
                                       String author, String publisher, Tag tag);

    @Query(value = "select i from Book i where (i.owner = ?1 or ?1 member of i.shared) and ((" + "i" + ".place" +
            ".location " + "like %?2%) or (i.name like %?3%) or (i" + ".description like %?4%)or(i.isbn like %?5% ) " + "or(i" + ".author " + "like %?6%) or (i.publisher like %?7%) or (?8 member of i.tags))")
    Iterable<Book> searchAllByOwnerOr(BookUser owner, String location, String name, String description, String isbn,
                                      String author, String publisher, Tag tag);

    @Query(value =
            "select i from Book i where (i.owner = ?1 or ?1 member of i.shared) and i.place.location like " + "%?2"
                    + "% and i.name like %?3% and i.description like %?4% and i.isbn like %?5% and i.author like " +
                    "%?6%" + " and " + "i.publisher like %?7% and (?8 member of i.categories)")
    Iterable<Book> searchAllByOwnerAnd(BookUser owner, String location, String name, String description, String isbn,
                                       String author, String publisher, Category category);

    @Query(value = "select i from Book i where (i.owner = ?1 or ?1 member of i.shared) and ((" + "i" + ".place" +
            ".location " + "like %?2%) or (i.name like %?3%) or (i" + ".description like %?4%)or(i.isbn like %?5% ) " + "or(i" + ".author " + "like %?6%) or (i.publisher like %?7%) or (?8 member of i.categories))")
    Iterable<Book> searchAllByOwnerOr(BookUser owner, String location, String name, String description, String isbn,
                                      String author, String publisher, Category category);

    @Query(value =
            "select i from Book i where (i.owner = ?1 or ?1 member of i.shared) and i.place.location like " + "%?2"
                    + "% and i.name like %?3% and i.description like %?4% and i.isbn like %?5%  and i.author like " + "%?6% and " + "i.publisher like %?7% and (?8 member of i.tags) and (?9 member of i.categories)")
    Iterable<Book> searchAllByOwnerAnd(BookUser owner, String location, String name, String description, String isbn,
                                       String author, String publisher, Tag tag, Category category);

    @Query(value = "select i from Book i where (i.owner = ?1 or ?1 member of i.shared) and ((" + "i" + ".place" +
            ".location " + "like %?2%) or (i.name like %?3%) or (i" + ".description like %?4%)or(i.isbn like %?5% ) " + "or(i" + ".author " + "like %?6%) or (i.publisher like %?7%) or (?8 member of i.tags) or (?9 member of i" + ".categories))")
    Iterable<Book> searchAllByOwnerOr(BookUser owner, String location, String name, String description, String isbn,
                                      String author, String publisher, Tag tag, Category category);
}
