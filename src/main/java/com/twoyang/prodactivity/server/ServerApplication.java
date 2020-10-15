package com.twoyang.prodactivity.server;

import com.twoyang.prodactivity.server.business.booking.BookingEntity;
import com.twoyang.prodactivity.server.business.categories.CategoryEntity;
import com.twoyang.prodactivity.server.business.tasks.TaskEntity;
import com.twoyang.prodactivity.server.business.users.UserEntity;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Primitives;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        val mapper = new ModelMapper();
        mapper.getConfiguration().getConverters().add(toIdConverter());
        return mapper;
    }

    @Bean
    public ConditionalConverter<Object, Long> toIdConverter() {
        return new ConditionalConverter<>() {
            @Override
            public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
                if (!Long.class.isAssignableFrom(Primitives.wrapperFor(destinationType)))
                    return null;
                Stream<Class<?>> classes = Stream.of(
                    TaskEntity.class,
                    UserEntity.class,
                    CategoryEntity.class,
                    BookingEntity.class);

                return classes.anyMatch(clazz -> clazz.isAssignableFrom(sourceType)) ? MatchResult.FULL : MatchResult.PARTIAL;
            }

            @Override
            public Long convert(MappingContext<Object, Long> context) {
                if (context.getSource() == null)
                    return null;
                val src = context.getSource();
                if (src instanceof TaskEntity)
                    return ((TaskEntity) src).getId();
                if (src instanceof UserEntity)
                    return ((UserEntity) src).getId();
                if (src instanceof CategoryEntity)
                    return ((CategoryEntity) src).getId();
                if (src instanceof BookingEntity)
                    return ((BookingEntity) src).getId();
                return null;
            }
        };
    }
}
