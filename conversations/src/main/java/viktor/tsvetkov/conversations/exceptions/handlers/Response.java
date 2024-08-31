package viktor.tsvetkov.conversations.exceptions.handlers;

public class Response {

    private final String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
