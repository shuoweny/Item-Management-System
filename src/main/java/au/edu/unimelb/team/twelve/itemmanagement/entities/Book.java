package au.edu.unimelb.team.twelve.itemmanagement.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class Book extends Item {
    String author;
    Date publishedDate;
    String isbn;
    String publisher;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Category> categories;
}
