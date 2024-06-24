package au.edu.unimelb.team.twelve.itemmanagement.dto;

import au.edu.unimelb.team.twelve.itemmanagement.entities.BookUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {
    String name;
    String avatar;
    String profile;

    UUID uuid;

    public static UserDTO fromUser(BookUser user) {
        return new UserDTO(user.getName(), user.getAvatar(), user.getProfile(), user.getId());
    }
}
