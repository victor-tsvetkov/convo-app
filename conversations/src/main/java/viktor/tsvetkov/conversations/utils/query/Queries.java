package viktor.tsvetkov.conversations.utils.query;

public class Queries {
    public static final String GET_CHATS_OF_CURRENT_USER_WITH_INTERLOCUTOR = """
                select c.*, u.* from chats c
                join users_chats uc
                on c.id = uc.chat_id
                join users u on uc.user_id = u.id
                where u.id != :currentUserId \s""";

    public static final String USERS_CURRENT_USER_DOES_NOT_HAVE_CHAT_WITH = """
            select u.* from users u
            where u.id not in (
                select users.id from users join users_chats uc
                                on users.id = uc.user_id
                                join chats c on uc.chat_id = c.id
            ) and u.id != :currentUserId""";

    public static final String BY_SEX = " and u.sex = :sex";

    public static final
    String USERS_CURRENT_USER_DOES_NOT_HAVE_CHAT_WITH_BY_SEX = USERS_CURRENT_USER_DOES_NOT_HAVE_CHAT_WITH + BY_SEX;
}
