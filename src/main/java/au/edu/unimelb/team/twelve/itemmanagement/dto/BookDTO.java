package au.edu.unimelb.team.twelve.itemmanagement.dto;

import au.edu.unimelb.team.twelve.itemmanagement.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    UUID id;
    UUID ownerId;
    Set<UUID> sharedIds;
    Set<UUID> tagIds;
    String name;
    String description;
    UUID storageId;

    String author;
    @DateTimeFormat(pattern = "yyyy/mm/dd")
    Date publishedDate;
    String isbn;
    String publisher;
    Set<UUID> categoryIds;

    public void fillSets() {
        if (sharedIds == null) {
            sharedIds = new HashSet<>();
        }
        if (tagIds == null) {
            tagIds = new HashSet<>();
        }
        if (categoryIds == null) {
            categoryIds = new HashSet<>();
        }
    }
    public static BookDTO fromItem(Book item) {
        return new BookDTO(item.getId(), item.getOwner().getId(),
                item.getShared().stream().map(BookUser::getId).collect(Collectors.toSet()),
                item.getTags().stream().map(Tag::getId).collect(Collectors.toSet()), item.getName(),
                item.getDescription(), item.getPlace().getId(), item.getAuthor(), item.getPublishedDate(),
                item.getIsbn(), item.getPublisher(),
                item.getCategories().stream().map(Category::getId).collect(Collectors.toSet()));
    }

    public static Iterable<BookDTO> fromGenericItem(Stream<Item> items) {
        return fromItem(items.filter(i -> i instanceof Book).map(i -> (Book) i));
    }

    public static Iterable<BookDTO> fromItem(Stream<Book> items) {
        return items.map(BookDTO::fromItem).collect(Collectors.toList());
    }

    public static Iterable<BookDTO> fromItem(Iterable<Book> items) {
        return fromItem(StreamSupport.stream(items.spliterator(), false));
    }
}
