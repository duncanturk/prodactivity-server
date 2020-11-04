package com.twoyang.prodactivity.server.business.booking.suggestions;

import com.twoyang.prodactivity.server.business.booking.BookingEntity;
import com.twoyang.prodactivity.server.business.booking.BookingRepository;
import com.twoyang.prodactivity.server.business.tasks.TaskEntity;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookingSuggestionService {

    private final BookingRepository bookingRepository;
    private static final int INTERVAL_COUNT_TO_INCLUDE = 4;

    public BookingSuggestionService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Long> getSuggestions(final TaskEntity task) {
        val bookings = bookingRepository.findByEndTimeGreaterThanAndTask((System.currentTimeMillis() / 1000) - task.getInterval() * INTERVAL_COUNT_TO_INCLUDE, task);
        val suggestions = bookings.stream()
            .map(BookingEntity::getAmount)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.comparingLong(l -> -l)))
            .map(Map.Entry::getKey).collect(Collectors.toCollection(ArrayList::new));
        val days = touchedDays(task.getInterval());
        val amountPerDay = task.getGoal() / days;
        val quartersPerDay = quarters(amountPerDay);
        if (suggestions.size() < 5)
            suggestions.addAll(List.of(
                10L * 60 * quartersPerDay,
                15L * 60 * quartersPerDay,
                5L * 60 * quartersPerDay,
                20L * 60 * quartersPerDay,
                25L * 60 * quartersPerDay
            ));
        return suggestions.stream().distinct().collect(Collectors.toList());
    }

    private int touchedDays(double intervalSize) {
        return (int) Math.ceil(intervalSize / (60 * 60 * 24));
    }

    private int quarters(double intervalSize) {
        return (int) Math.ceil(intervalSize / (60 * 15));
    }
}
