package todo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ToDoClient {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/todos";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ToDoClient() {
        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    public List<ToDo> findAll() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> todos = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(todos.body(), new TypeReference<List<ToDo>>() {
        });
    }

    public ToDo findById(int id) throws IOException, InterruptedException, ToDoNotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> todo = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (todo.statusCode() == 404)
            throw new ToDoNotFoundException("todo.ToDo not found");
        return objectMapper.readValue(todo.body(), ToDo.class);
    }
}
