package com.daksh.Service.Impl;

import com.daksh.Domain.BookingStatus;
import com.daksh.Repository.BookingRepository;
import com.daksh.Service.BokingService;
import com.daksh.dto.BookingRequest;
import com.daksh.dto.SalonDTO;
import com.daksh.dto.ServiceDto;
import com.daksh.dto.UserDTO;
import com.daksh.model.Booking;
import com.daksh.model.SalonReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BokingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDto> serviceDtoSet) throws Exception {

       int totalDuration = serviceDtoSet.stream().mapToInt(ServiceDto::getDuration).sum();

       LocalDateTime bookingStartTime = booking.getStartTime();
       LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

Boolean isSlotAvl=isTimeSlotAvl(salon,bookingStartTime,bookingEndTime);

int totalPrice=serviceDtoSet.stream().mapToInt(ServiceDto::getPrice) .sum();
Set<Long> idList=serviceDtoSet.stream().map(ServiceDto::getId).collect(Collectors.toSet());

Booking newBooking=new Booking();
newBooking.setCustomerId(user.getId());
newBooking.setSalonId(salon.getId());
newBooking.setStatus(BookingStatus.PENDING);
newBooking.setStartTime(bookingStartTime);
newBooking.setEndTime(bookingEndTime);
newBooking.setServiceIds(idList);
newBooking.setTotalPrice(totalPrice);

        return bookingRepository.save(newBooking);
    }

    public boolean isTimeSlotAvl(SalonDTO salonDTO,LocalDateTime bookingStartTime,LocalDateTime bookingEndTime) throws Exception {

        List<Booking> existingBookings =getBookingsBySalon(salonDTO.getId());
LocalDateTime salonOpenTime= salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
LocalDateTime salonCloseTime= salonDTO.getCloseTime().atDate(bookingStartTime.toLocalDate());

if(bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime))
{
throw new Exception("Booking time must be within salon's working time");
}

for(Booking existingBooking:existingBookings)
{
    LocalDateTime existiingBookingStartTime = existingBooking.getStartTime();
    LocalDateTime existiingBookingEndTime = existingBooking.getEndTime();

    if(bookingStartTime.isBefore(existiingBookingEndTime) && bookingEndTime.isAfter(existiingBookingStartTime)){

        throw new Exception("Slot not available");
    }

    if(bookingStartTime.isEqual(existiingBookingStartTime) || bookingEndTime.isEqual(existiingBookingEndTime))
    {

        throw new Exception("Slot not available,choose different time");
    }

}


        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {

        Booking booking=bookingRepository.findById(id).orElse(null);
        if(booking==null)
        {
            throw new Exception("Booking not found with id: "+id);
        }

        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {

        Booking booking=getBookingById(bookingId);

       booking.setStatus(status);

       return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingByData(LocalDate date, Long salonId) {

        List<Booking> allBooking=getBookingsBySalon(salonId);
        if(date ==null)
        {
            return allBooking;
        }
 return allBooking.stream().filter(booking -> isSameDate(booking.getStartTime(),date) || isSameDate(booking.getEndTime(),date)).collect(Collectors.toList());


    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
  return dateTime.toLocalDate().isEqual(date);

    }

    @Override
    public SalonReport getSalonReport(Long salonId) {

      List<Booking> bookings=getBookingsBySalon(salonId);
      int totalEarnings=bookings.stream().mapToInt(Booking::getTotalPrice).sum();

     Integer totalBookings=bookings.size();

     List<Booking> cancelledBookings=bookings.stream().filter(booking -> booking.getStatus()==BookingStatus.CANCELLED).collect(Collectors.toList());

     Double totalRefund=cancelledBookings.stream().mapToDouble(Booking::getTotalPrice).sum();

     SalonReport report=new SalonReport();
     report.setSalonId(salonId);
     report.setCancelledBookings(cancelledBookings.size());
     report.setTotalEarnings(totalEarnings);
     report.setTotalRefund(totalRefund);
     report.setTotalBookings(totalBookings);


        return report;
    }
}
