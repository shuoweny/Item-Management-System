package au.edu.unimelb.team.twelve.itemmanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "id", strategy = "uuid4")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(36)")
    private UUID id;
    String name;
    String description;
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Tag> tags;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storage_id")
    Storage place;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    BookUser owner;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<BookUser> shared;
}
