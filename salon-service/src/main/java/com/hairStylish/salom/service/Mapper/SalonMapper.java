package com.hairStylish.salom.service.Mapper;

import com.hairStylish.salom.service.model.Salon;
import com.hairStylish.salom.service.payLoad.dto.SalonDTO;

public class SalonMapper {

    public static SalonDTO mapToDTO(Salon salon){
        SalonDTO salonDTO=new SalonDTO();
        salonDTO.setId(salon.getId());
        salonDTO.setName(salon.getName());
        salonDTO.setAddress(salon.getAddress());
        salonDTO.setEmail(salon.getEmail());
        salonDTO.setPhoneNumber(salon.getPhoneNumber());
        salonDTO.setOwnerId(salon.getOwnerId());
        salonDTO.setCity(salon.getCity());
        salonDTO.setOpenTime(salon.getOpenTime());
        salonDTO.setCloseTime(salon.getCloseTime());
        salonDTO.setImage(salon.getImage());
        return salonDTO;
    }


}
