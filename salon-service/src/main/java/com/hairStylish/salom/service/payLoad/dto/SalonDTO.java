package com.hairStylish.salom.service.payLoad.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class SalonDTO {

//private UserDTO userDTO;

    private Long id;

    private String name;

    private List<String> image;

    private String address;

    private String phoneNumber;

    private String email;

    private String city;

    private Long ownerId;


    private LocalTime openTime;



    private LocalTime closeTime;


}
