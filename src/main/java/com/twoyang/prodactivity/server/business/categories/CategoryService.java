package com.twoyang.prodactivity.server.business.categories;

import com.twoyang.prodactivity.server.api.Category;
import com.twoyang.prodactivity.server.api.CategoryCreation;
import com.twoyang.prodactivity.server.business.util.AuthService;
import com.twoyang.prodactivity.server.business.util.CRUDService;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements CRUDService<Category, CategoryCreation> {
    private final CategoryRepository categoryRepository;
    private final AuthService authService;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository categoryRepository, AuthService authService, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.authService = authService;
        this.mapper = mapper;
    }

    @Override
    public Category create(CategoryCreation createCommand) {
        val entity = mapper.map(createCommand, CategoryEntity.class);
        entity.setUsr(authService.userEntity());
        return map(categoryRepository.save(entity));
    }

    @Override
    public List<Category> getAllForUser() {
        return categoryRepository.findAllForUser().stream().map(this::map).collect(Collectors.toList());
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private Category map(CategoryEntity entity) {
        return mapper.map(entity, Category.class);
    }
}
