package viktor.tsvetkov.conversations.mappers;

import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.enums.Sex;

import static viktor.tsvetkov.conversations.utils.DateTimeUtils.parseStringToLocalDateTime;

import java.util.UUID;

public class UserMapper {
    public static User mapUser(Object[] data) {
        return User.builder()
                .id(UUID.fromString(data[0].toString()))
                .name(data[1].toString())
                .points(Long.parseLong(data[2].toString()))
                .sex(Enum.valueOf(Sex.class, data[3].toString()))
                .creationDate(parseStringToLocalDateTime(data[4].toString()))
                .username(data[5].toString()).build();
    }
}
