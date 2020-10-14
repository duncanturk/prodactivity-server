package com.twoyang.prodactivity.server.business.tasks;

import com.twoyang.prodactivity.server.api.Task;
import com.twoyang.prodactivity.server.api.TaskCreation;
import com.twoyang.prodactivity.server.business.util.CRUDController;
import com.twoyang.prodactivity.server.business.util.CRUDService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController implements CRUDController<Task, TaskCreation> {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @Override
    public CRUDService<Task, TaskCreation> getService() {
        return service;
    }
}
