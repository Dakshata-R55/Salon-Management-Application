package com.hairStylish.salom.service.Controller;


import com.hairStylish.salom.service.Mapper.SalonMapper;
import com.hairStylish.salom.service.Service.SalonService;
import com.hairStylish.salom.service.model.Salon;
import com.hairStylish.salom.service.payLoad.dto.SalonDTO;
import com.hairStylish.salom.service.payLoad.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @PostMapping
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO) {
        UserDTO userDto = new UserDTO();
        userDto.setId(1L);
        Salon salon = salonService.createSalon(salonDTO, userDto);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    @PatchMapping("/{salonId}")
    public ResponseEntity<SalonDTO> updateSalon(
            @PathVariable Long salonId,
            @RequestBody SalonDTO salonDto) throws Exception {
        UserDTO userDto = new UserDTO();
        userDto.setId(1L);
        Salon salon = salonService.updateSalon(salonDto, userDto, salonId);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    @GetMapping
    public ResponseEntity<List<SalonDTO>> getSalons() throws Exception {

        List<Salon> salons = salonService.getAllSalons();
        List<SalonDTO> salonDTOS = salons.stream().map((salon) ->
                {
                    SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
                    return salonDTO;
                }
        ).toList();
        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long salonId) throws Exception {

        Salon salon = salonService.getSalonById(salonId);
        SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO);

    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> searchSalons(@RequestParam("city") String city) throws Exception {

        List<Salon> salons = salonService.searchSalonByCity(city);
        List<SalonDTO> salonDTOS = salons.stream().map((salon) ->
                {
                    SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
                    return salonDTO;
                }
        ).toList();
        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<SalonDTO> getSalonByOwnerId(@PathVariable Long ownerId) throws Exception {
        UserDTO userDto = new UserDTO();
        userDto.setId(1L);
        Salon salon = salonService.getSalonByOwnerId(ownerId);
        SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO);

    }

    }
