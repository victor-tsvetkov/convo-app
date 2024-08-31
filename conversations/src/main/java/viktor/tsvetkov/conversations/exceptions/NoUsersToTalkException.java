package viktor.tsvetkov.conversations.exceptions;

public class NoUsersToTalkException extends RuntimeException {

    public NoUsersToTalkException() {
        super();
    }

    public NoUsersToTalkException(String message) {
        super(message);
    }

    public NoUsersToTalkException(String message, Throwable cause) {
        super(message, cause);
    }
}
