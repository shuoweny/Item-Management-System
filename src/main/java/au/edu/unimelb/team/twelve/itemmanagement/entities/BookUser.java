package au.edu.unimelb.team.twelve.itemmanagement.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class BookUser {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "id", strategy = "uuid4")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(36)")
    private UUID id;

    String token;

    String name;
    String password;
    String salt;

    String avatar;
    String profile;

    boolean newComer;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Item> favorites;
}
