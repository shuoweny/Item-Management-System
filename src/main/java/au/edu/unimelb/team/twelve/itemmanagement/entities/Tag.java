package au.edu.unimelb.team.twelve.itemmanagement.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "id", strategy = "uuid4")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(36)")
    private UUID id;

    String name;
}
