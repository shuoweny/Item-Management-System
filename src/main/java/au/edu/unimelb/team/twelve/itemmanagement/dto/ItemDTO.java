package au.edu.unimelb.team.twelve.itemmanagement.dto;

import au.edu.unimelb.team.twelve.itemmanagement.entities.BookUser;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Item;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    UUID id;
    UUID ownerId;
    Set<UUID> sharedIds;
    Set<UUID> tagIds;
    String name;
    String description;
    UUID storageId;

    public void fillSets() {
        if (sharedIds == null) {
            sharedIds = new HashSet<>();
        }
        if (tagIds == null) {
            tagIds = new HashSet<>();
        }
    }

    public static ItemDTO fromItem(Item item) {
        return new ItemDTO(item.getId(), item.getOwner().getId(),
                item.getShared().stream().map(BookUser::getId).collect(Collectors.toSet()),
                item.getTags().stream().map(Tag::getId).collect(Collectors.toSet()), item.getName(),
                item.getDescription(), item.getPlace().getId());
    }

    public static Iterable<ItemDTO> fromItem(Stream<Item> items) {
        return items.map(ItemDTO::fromItem).collect(Collectors.toList());
    }

    public static Iterable<ItemDTO> fromItem(Iterable<Item> items) {
        return fromItem(StreamSupport.stream(items.spliterator(), false));
    }
}
