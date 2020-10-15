package com.twoyang.prodactivity.server.business.booking.suggestions;

import com.twoyang.prodactivity.server.business.tasks.TaskEntity;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingSuggestionService {
    public List<Long> getSuggestions(final TaskEntity task) {
        val days = touchedDays(task.getInterval());
        val amountPerDay = task.getGoal() / days;
        val quartersPerDay = quarters(amountPerDay);
        return List.of(
            10L * 60 * quartersPerDay,
            15L * 60 * quartersPerDay,
            5L * 60 * quartersPerDay
        );
    }

    private int touchedDays(double intervalSize) {
        return (int) Math.ceil(intervalSize / (60 * 60 * 24));
    }

    private int quarters(double intervalSize) {
        return (int) Math.ceil(intervalSize / (60 * 15));
    }
}
