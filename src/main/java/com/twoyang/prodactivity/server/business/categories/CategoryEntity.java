package com.twoyang.prodactivity.server.business.categories;

import com.twoyang.prodactivity.server.business.tasks.TaskEntity;
import com.twoyang.prodactivity.server.business.users.UserEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@ToString(exclude = "tasks")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String color;
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL)
    @JoinTable(
        name = "task_categories",
        joinColumns = @JoinColumn(name = "category"),
        inverseJoinColumns = @JoinColumn(name = "task")
    )
    private List<TaskEntity> tasks = new ArrayList<>();

    @ManyToOne(optional = false)
    private UserEntity usr;
}
