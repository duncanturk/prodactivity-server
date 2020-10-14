package com.twoyang.prodactivity.server.business.tasks;

import com.twoyang.prodactivity.server.api.Task;
import com.twoyang.prodactivity.server.api.TaskCreation;
import com.twoyang.prodactivity.server.business.booking.BookingEntity;
import com.twoyang.prodactivity.server.business.booking.BookingRepository;
import com.twoyang.prodactivity.server.business.categories.CategoryEntity;
import com.twoyang.prodactivity.server.business.categories.CategoryRepository;
import com.twoyang.prodactivity.server.business.util.CRUDService;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements CRUDService<Task, TaskCreation> {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper mapper;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository, BookingRepository bookingRepository, ModelMapper mapper) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
    }

    @Override
    public Task create(TaskCreation command) {
        val entity = mapper.map(command, TaskEntity.class);
        val cats = command.getCategories().stream().map(categoryRepository::getOne).collect(Collectors.toList());
        cats.forEach(cat -> cat.getTasks().add(entity));
        entity.setCategories(cats);
        return map(taskRepository.save(entity));
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    private Task map(TaskEntity entity) {
        val task = mapper.map(entity, Task.class);
        val done = bookingRepository.findByEndTimeGreaterThanAndTask(System.currentTimeMillis() / 1000 - entity.getInterval(), entity).stream().mapToLong(BookingEntity::getAmount).sum();
        task.setRemaining(entity.getGoal() - done);
        return task;
    }
}
