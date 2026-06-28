package com.hairStylish.salom.service.Service;

import com.hairStylish.salom.service.model.Salon;
import com.hairStylish.salom.service.payLoad.dto.SalonDTO;
import com.hairStylish.salom.service.payLoad.dto.UserDTO;

import java.util.List;

public interface SalonService {

    Salon updateSalon(SalonDTO salon, UserDTO user,Long salonId) throws Exception;
List<Salon> getAllSalons();
Salon getSalonById(Long salonId) throws Exception;
Salon getSalonByOwnerId(Long ownerId);
List<Salon> searchSalonByCity(String city);

    Salon createSalon(SalonDTO salonDTO, UserDTO userDto);
}
