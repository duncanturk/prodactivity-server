package com.twoyang.prodactivity.server.business.categories;

import com.twoyang.prodactivity.server.api.Category;
import com.twoyang.prodactivity.server.api.CategoryCreation;
import com.twoyang.prodactivity.server.business.util.CRUDController;
import com.twoyang.prodactivity.server.business.util.CRUDService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController implements CRUDController<Category, CategoryCreation> {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Override
    public CRUDService<Category, CategoryCreation> getService() {
        return service;
    }
}
