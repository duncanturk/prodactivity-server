package com.twoyang.prodactivity.server.business.tasks;

import com.twoyang.prodactivity.server.business.categories.CategoryEntity;
import com.twoyang.prodactivity.server.business.users.UserEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        mappedBy = "tasks")
    private List<CategoryEntity> categories = new ArrayList<>();
    private Long interval;
    private Long goal;

    @ManyToOne(optional = false)
    private UserEntity usr;
}
