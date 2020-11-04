package com.twoyang.prodactivity.server.business.booking;

import com.twoyang.prodactivity.server.business.tasks.TaskEntity;
import com.twoyang.prodactivity.server.business.users.UserEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "bookings")
@Data
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    private TaskEntity task;

    private Long amount, endTime;

    private boolean disabled = false;

    @ManyToOne(optional = false)
    private UserEntity usr;
}
