package org.example.todoapispring;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
    private final ITodoService todoService;
    private final ITodoService todoService2;

    private static List<Todo> todoList;
    private final static Map<String, String> NOT_FOUND = Map.of("message", "Todo not found");

    public TodoController(
            @Qualifier("todoService") ITodoService todoService,
            @Qualifier("anotherTodoService") ITodoService todoService2
    ) {
        this.todoService = todoService;
        this.todoService2 = todoService2;

        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "Create a new todo", 1));
        todoList.add(new Todo(2, true, "Update an existing todo", 2));
        todoList.add(new Todo(3, false, "Delete a todo", 1));
    }

    @GetMapping("/")
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable int id) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                return ResponseEntity.status(HttpStatus.OK).body(todo + " " + todoService.doSomething());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getTodoByUserId(@RequestParam(required = false, defaultValue = "0") int userId) {
        if (userId == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "User ID is required"));
        }
        for (Todo todo : todoList) {
            if (todo.getUserId() == userId) {
                return ResponseEntity.ok(todo);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo) {
        todoList.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable int id) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                todoList.remove(todo);
                return ResponseEntity.ok(todo);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTodoById(@PathVariable int id, @RequestBody Todo newTodo) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                todo.setCompleted(newTodo.isCompleted());
                todo.setTitle(newTodo.getTitle());
                todo.setUserId(newTodo.getUserId());
                return ResponseEntity.ok(todo);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
    }
}
