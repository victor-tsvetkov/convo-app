package viktor.tsvetkov.conversations.exceptions.handlers;

public class Response {

    private final Throwable throwable;

    public Response(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getMessage() {
        return throwable.getMessage();
    }

    public String getLocalizedMessage() {
        return throwable.getLocalizedMessage();
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
