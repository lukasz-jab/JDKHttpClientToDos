package todo;

public class ToDoNotFoundException extends Throwable {

    public ToDoNotFoundException(String message) {
        super(message);
    }
}
