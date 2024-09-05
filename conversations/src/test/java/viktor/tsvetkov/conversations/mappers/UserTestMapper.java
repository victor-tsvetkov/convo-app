package viktor.tsvetkov.conversations.mappers;

import org.springframework.jdbc.core.RowMapper;
import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.enums.Sex;
import viktor.tsvetkov.conversations.utils.DateTimeUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserTestMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        String name = rs.getString("name");
        long points = rs.getLong("points");
        LocalDateTime creationDate = DateTimeUtils.
                convertToLocalDateTimeViaInstant(rs.getDate("creation_date"));
        Sex sex = Enum.valueOf(Sex.class, rs.getString("sex"));
        String username = rs.getString("username");
        String password = rs.getString("password");
        return User.builder()
                .name(name).points(points).creationDate(creationDate)
                .sex(sex)
                .username(username).password(password).build();
    }
}
