package org.example.todoapispring;

import org.springframework.stereotype.Service;

@Service("todoService")
public class TodoService implements ITodoService {

    @TimeMonitor
    @Override
    public String doSomething() {
        for (int i = 0; i < 100000; i++) {}
        return "doing something...";
    }
}
