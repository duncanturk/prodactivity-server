package com.twoyang.prodactivity.server.business.booking;

import com.twoyang.prodactivity.server.api.Booking;
import com.twoyang.prodactivity.server.api.BookingCreation;
import com.twoyang.prodactivity.server.business.util.CRUDController;
import com.twoyang.prodactivity.server.business.util.CRUDService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController implements CRUDController<Booking, BookingCreation> {
    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @Override
    public CRUDService<Booking, BookingCreation> getService() {
        return service;
    }
}
