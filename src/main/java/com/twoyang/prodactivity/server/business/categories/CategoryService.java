package com.twoyang.prodactivity.server.business.categories;

import com.twoyang.prodactivity.server.api.Category;
import com.twoyang.prodactivity.server.api.CategoryCreation;
import com.twoyang.prodactivity.server.business.util.AuthService;
import com.twoyang.prodactivity.server.business.util.CRUDService;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements CRUDService<Category, CategoryCreation> {
    private final CategoryRepository repository;
    private final AuthService authService;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository repository, AuthService authService, ModelMapper mapper) {
        this.repository = repository;
        this.authService = authService;
        this.mapper = mapper;
    }

    @Override
    public Category create(CategoryCreation createCommand) {
        val entity = mapper.map(createCommand, CategoryEntity.class);
        entity.setUsr(authService.userEntity());
        return map(repository.save(entity));
    }

    @Override
    public List<Category> getAllForUser() {
        return repository.findAllForUser().stream().map(this::map).collect(Collectors.toList());
    }

    public void delete(Long id) {
        val entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        entity.setDisabled(true);
        repository.save(entity);
    }

    private Category map(CategoryEntity entity) {
        return mapper.map(entity, Category.class);
    }
}
