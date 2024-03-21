package todo;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToDoClientTest {

    // System under test
    ToDoClient toDoClient = new ToDoClient();

    @Test
    void findAll() throws IOException, InterruptedException {
        List<ToDo> todos = toDoClient.findAll();

        assertEquals(200, todos.size());
    }

    @Test
    void shouldReturnToDoGivenValidId() throws IOException, InterruptedException, ToDoNotFoundException {
        ToDo todo1 = toDoClient.findById(1);
        assertEquals(1, todo1.id());
        assertEquals(1, todo1.userId());
        assertEquals("delectus aut autem", todo1.title());
        assertFalse(todo1.completed());
    }

    @Test
    void shouldThrowNotFoundExceptionGivenInvalidId() {
        ToDoNotFoundException toDoNotFoundException = assertThrows(ToDoNotFoundException.class, () -> toDoClient.findById(999));
        assertEquals("todo.ToDo not found", toDoNotFoundException.getMessage());
    }
}
