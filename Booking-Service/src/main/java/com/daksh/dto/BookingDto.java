package com.daksh.dto;

import com.daksh.Domain.BookingStatus;
import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {

    private Long id;

    private Long salonId;

    private Long customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    private Set<Long> serviceIds;

    private BookingStatus status=BookingStatus.PENDING;

    public void setId(Long id) {
    }

    //private int totalPrice;
}
