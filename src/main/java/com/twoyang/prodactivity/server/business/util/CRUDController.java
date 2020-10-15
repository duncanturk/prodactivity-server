package com.twoyang.prodactivity.server.business.util;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CRUDController<T, C> {
    CRUDService<T, C> getService();

    @GetMapping("")
    default List<T> getAll() {
        return getService().getAllForUser();
    }

    @PostMapping("")
    default T create(@RequestBody C create) {
        return getService().create(create);
    }

    @DeleteMapping("{id}")
    default void delete(@PathVariable Long id) {
        getService().delete(id);
    }
}
