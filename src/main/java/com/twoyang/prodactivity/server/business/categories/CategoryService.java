package com.twoyang.prodactivity.server.business.categories;

import com.twoyang.prodactivity.server.api.Category;
import com.twoyang.prodactivity.server.api.CategoryCreation;
import com.twoyang.prodactivity.server.business.tasks.TaskEntity;
import com.twoyang.prodactivity.server.business.util.CRUDService;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements CRUDService<Category, CategoryCreation> {
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public Category create(CategoryCreation createCommand) {
        return map(categoryRepository.save(mapper.map(createCommand, CategoryEntity.class)));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private Category map(CategoryEntity entity) {
        return mapper.map(entity, Category.class);
    }
}
