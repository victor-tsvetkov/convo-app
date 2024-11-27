package viktor.tsvetkov.conversations.utils.query;

public class SqlQueries {
    public static final String USERS_CURRENT_USER_DOES_NOT_HAVE_CHAT_WITH = """
            select u.* from users u
            where u.id not in (
                select users.id from users join chat_items ci
                                on users.id = ci.user_id
                                join chats c on ci.chat_id = c.id
            ) and u.id != :currentUserId""";

    public static final String BY_SEX = " and u.sex = :sex";

    public static final
    String USERS_CURRENT_USER_DOES_NOT_HAVE_CHAT_WITH_BY_SEX = USERS_CURRENT_USER_DOES_NOT_HAVE_CHAT_WITH + BY_SEX;


    private static final String CHATS_WITH_INTERLOCUTOR = """
                with c_w_i as (
                            select c.chat_id, u.id as id_interlocutor, u.name as interlocutor_name
                            from
                            (select chat_id from chat_items where user_id = :idUser) as c
                            join chat_items on c.chat_id = chat_items.chat_id and user_id <> :idUser
                            join users u on chat_items.user_id = u.id)
                            select c.chat_id as chatId, c.id_interlocutor as interlocutorId, c.interlocutor_name as interlocutorName,
                            case
                               when m.creation_date\\:\\:date = 'today'
                                   then to_char(m.creation_date, 'HH24:MI')
                               when m.creation_date\\:\\:date = 'yesterday'
                                   then 'вчера'
                               when extract(days from current_date - m.creation_date) <= 7
                                   then to_char(m.creation_date, 'TMday')
                               when extract(days from current_date - m.creation_date) > 7 and extract(years from age(m.creation_date)) < 1
                                   then to_char(m.creation_date, 'dd.MM')
                               when extract(years from age(m.creation_date)) >= 1
                                   then to_char(m.creation_date, 'dd.MM.yyyy')
                            end as messageDate,
                            m.text as messageText, m.is_read as read, m.id_user as sender from c_w_i c, messages m """;

    private static final String CONDITION_LATEST_MESSAGE = """
             where chat_id = m.id_chat and m.creation_date = (select max(creation_date) from messages where messages.id_chat = c.chat_id)
            order by m.creation_date desc;""";

    private static final String CONDITION_SEARCH_MESSAGE = """
             where chat_id = m.id_chat and lower(m.text) like :searchParam
            order by m.creation_date desc;""";

    public static final String INTERLOCUTOR_CHAT_LATEST_MESSAGE = CHATS_WITH_INTERLOCUTOR + CONDITION_LATEST_MESSAGE;
    public static final String SEARCH_MESSAGE = CHATS_WITH_INTERLOCUTOR + CONDITION_SEARCH_MESSAGE;

    public static final String MESSAGES_IN_CHAT = "select m.id as id, m.is_read as isRead, to_char(m.creation_date, 'HH24:MI') as creationDate, m.id_user as idUser, m.text as text, " +
            "case" +
            "           when m.creation_date\\:\\:date = 'today' " +
            "               then " +
            "                      case" +
            "                      when m.creation_date = (select min(creation_date) from messages " +
            "                                                                          where creation_date\\:\\:date = 'today' " +
            "                                                                          and id_chat = :chatId) " +
            "                            then 'сегодня' " +
            "                        else null " +
            "                    end " +
            "            when m.creation_date\\:\\:date = 'yesterday'" +
            "                then " +
            "                       case\n" +
            "                        when m.creation_date = (select min(creation_date) from messages\n" +
            "                                                                            where creation_date\\:\\:date = 'yesterday'\n" +
            "                                                                            and id_chat = :chatId)\n" +
            "                            then 'вчера'\n" +
            "                        else null\n" +
            "                    end" +
            "            when extract(years from age(m.creation_date)) < 1" +
            "                then " +
            "                       case\n" +
            "                        when m.creation_date = (select min(creation_date) from messages\n" +
            "                                                                          where m.creation_date\\:\\:date = creation_date\\:\\:date\n" +
            "                                                                          and id_chat = :chatId)\n" +
            "                        then to_char(m.creation_date, 'dd TMmonth')\n" +
            "                        else null\n" +
            "                    end" +
            "           when extract(years from age(m.creation_date)) > 1" +
            "               then " +
            "                   case\n" +
            "                        when m.creation_date = (select min(creation_date) from messages\n" +
            "                                                where m.creation_date\\:\\:date = creation_date\\:\\:date\n" +
            "                                                  and id_chat = :chatId)\n" +
            "                        then to_char(m.creation_date, 'dd TMmonth yyyy')\n" +
            "                        else null\n" +
            "                    end" +
            "       end as formattedDay, (select name from users u where u.id = m.id_user) as userName" +
            " " +
            "from messages m where m.id_chat = :chatId order by m.creation_date desc";

    public static final String MESSAGES_QUANTITY_IN_CHAT = "select count(*) from messages where id_chat = :chatId";
}
