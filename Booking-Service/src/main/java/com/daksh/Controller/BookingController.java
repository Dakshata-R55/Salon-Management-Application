package com.daksh.Controller;

import com.daksh.Domain.BookingStatus;
import com.daksh.Mapper.BookingMapper;
import com.daksh.Service.BokingService;
import com.daksh.dto.*;
import com.daksh.model.Booking;
import com.daksh.model.SalonReport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BokingService bokingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long salonId,
            @RequestBody BookingRequest bookingRequest
    ) throws Exception {

        UserDTO user = new UserDTO();
        user.setId(1L);
        SalonDTO salon = new SalonDTO();
        salon.setId(salonId);
        salon.setOpenTime(LocalTime.now());
salon.setCloseTime(LocalTime.now().plusHours(12));
        Set<ServiceDto> serviceDtoSet = new HashSet<>();

        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(1L);
        serviceDto.setPrice(399);
        serviceDto.setDuration(45);
        serviceDto.setName("Hair cut for men");
        serviceDtoSet.add(serviceDto);

        Booking booking = bokingService.createBooking(bookingRequest, user, salon, serviceDtoSet);

        return ResponseEntity.ok(booking);
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<BookingDto>> getBookingsBySalon(
            @PathVariable Long salonId
    ) {
        List<Booking> bookings = bokingService.getBookingsBySalon(salonId);
        return ResponseEntity.ok(getBookingDtos(bookings));
    }


    private Set<BookingDto> getBookingDtos(List<Booking> bookings) {
        return bookings.stream().map(booking -> {
            return BookingMapper.toDto(booking);
        }).collect(Collectors.toSet());


    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingsById(
            @PathVariable Long bookingId

    ) throws Exception {


        Booking bookings = bokingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toDto(bookings));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDto> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status
    ) throws Exception {
        Booking bookings = bokingService.updateBooking(bookingId, status);
        return ResponseEntity.ok(BookingMapper.toDto(bookings));
    }

    @GetMapping("/slots/salon/{salonId}/{date}")
    public ResponseEntity<List<BookingSlotDto>> getBookedSlot(
            @PathVariable Long salonId,
            @RequestParam LocalDate date

    ) throws Exception {


        List<Booking> bookings = bokingService.getBookingByData(date, salonId);
        List<BookingSlotDto> slotsDto = bookings.stream().map(booking -> {
            BookingSlotDto slotDto = new BookingSlotDto();
            slotDto.setStartTime(booking.getStartTime());
            slotDto.setEndTime(booking.getEndTime());
            return slotDto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(slotsDto);

    }

    @GetMapping("/report/salon/{salonId}")
    public ResponseEntity<SalonReport> getSalonReport(
            @PathVariable Long salonId,
            @RequestParam(required = false) LocalDate date
    ) throws Exception {
        SalonReport report = bokingService.getSalonReport(salonId);
        return ResponseEntity.ok(report);
    }
}
