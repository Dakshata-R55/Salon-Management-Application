package com.hairStylish.salom.service.Service.Impl;

import com.hairStylish.salom.service.Repository.SalonRepository;
import com.hairStylish.salom.service.Service.SalonService;
import com.hairStylish.salom.service.model.Salon;
import com.hairStylish.salom.service.payLoad.dto.SalonDTO;
import com.hairStylish.salom.service.payLoad.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {
        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setCity(req.getCity());
        salon.setEmail(req.getEmail());
        salon.setPhoneNumber(req.getPhoneNumber());
        salon.setCloseTime(req.getCloseTime());
        salon.setOpenTime(req.getOpenTime());
        salon.setOwnerId(user.getId());
        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception {
        // Load existing salon and validate existence
        Salon existingSalon = salonRepository.findById(salonId).orElse(null);
        if (existingSalon == null) {
            throw new Exception("Salon not exist");
        }

        // Check permission: only the current owner may update
        if (existingSalon.getOwnerId() == null || !existingSalon.getOwnerId().equals(user.getId())) {
            throw new Exception("You don't have permission to update the salon");
        }

        // Apply allowed updates (do not overwrite ownerId here)
        existingSalon.setName(salon.getName());
        existingSalon.setAddress(salon.getAddress());
        existingSalon.setCity(salon.getCity());
        existingSalon.setEmail(salon.getEmail());
        existingSalon.setPhoneNumber(salon.getPhoneNumber());
        existingSalon.setCloseTime(salon.getCloseTime());
        existingSalon.setOpenTime(salon.getOpenTime());

        return salonRepository.save(existingSalon);
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        Salon salon= salonRepository.findById(salonId).orElse(null);
        if(salon == null){
            throw new Exception("Salon not exist");
        }
        return salon;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalons(city);
    }


}
