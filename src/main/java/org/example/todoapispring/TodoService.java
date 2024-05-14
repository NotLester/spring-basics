package org.example.todoapispring;

import org.springframework.stereotype.Service;

@Service("todoService")
public class TodoService implements ITodoService {

    @Override
    public String doSomething() {
        return "doing something...";
    }
}
