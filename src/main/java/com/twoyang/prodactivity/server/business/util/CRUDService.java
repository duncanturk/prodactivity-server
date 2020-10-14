package com.twoyang.prodactivity.server.business.util;

import java.util.List;

public interface CRUDService<T, C> {
    T create(C createCommand);

    List<T> getAll();

    void delete(Long id);
}
