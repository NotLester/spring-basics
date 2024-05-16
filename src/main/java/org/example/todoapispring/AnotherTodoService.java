package org.example.todoapispring;

import org.springframework.stereotype.Service;

@Service("anotherTodoService")
public class AnotherTodoService implements ITodoService {
    @Override
    public String doSomething() {
        return "doing something in some other class";
    }
}
