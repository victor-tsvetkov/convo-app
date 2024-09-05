package viktor.tsvetkov.conversations.utils;

public class TestQueries {
    public static final String CREATE_USER = """
            insert into users (name, sex, points, username, password)
            values (
                'Maria', 'FEMALE', 100, 'Maria', '12345'
            );
            """;

    public static final String SELECT_USER = """
            select * from users where name = 'Maria';
            """;
}
