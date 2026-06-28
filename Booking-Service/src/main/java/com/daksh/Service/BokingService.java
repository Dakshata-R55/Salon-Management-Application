package com.daksh.Service;

import com.daksh.Domain.BookingStatus;
import com.daksh.dto.BookingRequest;
import com.daksh.dto.SalonDTO;
import com.daksh.dto.ServiceDto;
import com.daksh.dto.UserDTO;
import com.daksh.model.Booking;
import com.daksh.model.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BokingService {

Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDto> seviceDtoSet) throws Exception;

List<Booking> getBookingsByCustomer(Long customerId);
List<Booking> getBookingsBySalon(Long serviceId);
Booking getBookingById(Long id) throws Exception;
Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;
List<Booking> getBookingByData(LocalDate date, Long salonId);
SalonReport getSalonReport(Long salonId);
}
