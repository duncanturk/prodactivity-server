package com.twoyang.prodactivity.server.business.tasks;

import com.twoyang.prodactivity.server.api.Task;
import com.twoyang.prodactivity.server.api.TaskCreation;
import com.twoyang.prodactivity.server.business.booking.BookingEntity;
import com.twoyang.prodactivity.server.business.booking.BookingRepository;
import com.twoyang.prodactivity.server.business.booking.suggestions.BookingSuggestionService;
import com.twoyang.prodactivity.server.business.categories.CategoryRepository;
import com.twoyang.prodactivity.server.business.util.AuthService;
import com.twoyang.prodactivity.server.business.util.CRUDService;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements CRUDService<Task, TaskCreation> {
    private final TaskRepository repository;
    private final CategoryRepository categoryRepository;
    private final BookingRepository bookingRepository;
    private final AuthService authService;
    private final BookingSuggestionService bookingSuggestionService;
    private final ModelMapper mapper;

    public TaskService(TaskRepository repository, CategoryRepository categoryRepository, BookingRepository bookingRepository, AuthService authService, BookingSuggestionService bookingSuggestionService, ModelMapper mapper) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.bookingRepository = bookingRepository;
        this.authService = authService;
        this.bookingSuggestionService = bookingSuggestionService;
        this.mapper = mapper;
    }

    @Override
    public Task create(TaskCreation command) {
        val entity = mapper.map(command, TaskEntity.class);
        val cats = command.getCategories().stream().map(categoryRepository::getOne).collect(Collectors.toList());
        cats.forEach(cat -> cat.getTasks().add(entity));
        entity.setUsr(authService.userEntity());
        entity.setCategories(cats);
        return map(repository.save(entity));
    }

    @Override
    public List<Task> getAllForUser() {
        return repository.findAllForUser().stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        val entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        entity.setDisabled(true);
        repository.save(entity);
    }

    private Task map(TaskEntity entity) {
        val task = mapper.map(entity, Task.class);
        val done = bookingRepository.findByEndTimeGreaterThanAndTask(System.currentTimeMillis() / 1000 - entity.getInterval(), entity).stream()
            .mapToLong(BookingEntity::getAmount)
            .sum();
        task.setRemaining(entity.getGoal() - done);

        task.setBookingSuggestions(bookingSuggestionService.getSuggestions(entity));

        return task;
    }
}
