package com.twoyang.prodactivity.server.business.booking;

import com.twoyang.prodactivity.server.api.Booking;
import com.twoyang.prodactivity.server.api.BookingCreation;
import com.twoyang.prodactivity.server.business.tasks.TaskRepository;
import com.twoyang.prodactivity.server.business.util.CRUDService;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService implements CRUDService<Booking, BookingCreation> {
    private final BookingRepository repository;
    private final TaskRepository taskRepository;
    private final ModelMapper mapper;

    public BookingService(BookingRepository repository, TaskRepository taskRepository, ModelMapper mapper) {
        this.repository = repository;
        this.taskRepository = taskRepository;
        this.mapper = mapper;
    }

    @Override
    public Booking create(BookingCreation createCommand) {
        val entity = mapper.map(createCommand, BookingEntity.class);
        entity.setTask(taskRepository.getOne(createCommand.getTask()));
        return this.map(repository.save(entity));
    }

    @Override
    public List<Booking> getAll() {
        return repository.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private Booking map(BookingEntity entity) {
        return mapper.map(entity, Booking.class);
    }
}
