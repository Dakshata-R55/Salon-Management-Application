package com.daksh.Mapper;

import com.daksh.dto.BookingDto;
import com.daksh.model.Booking;

public class BookingMapper {

    public static BookingDto toDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setCustomerId(booking.getCustomerId());
        bookingDto.setSalonId(booking.getSalonId());
        bookingDto.setServiceIds(booking.getServiceIds());
        bookingDto.setStartTime(booking.getStartTime());
        bookingDto.setEndTime(booking.getEndTime());
        bookingDto.setStatus(booking.getStatus());
        return bookingDto;


    }

}
