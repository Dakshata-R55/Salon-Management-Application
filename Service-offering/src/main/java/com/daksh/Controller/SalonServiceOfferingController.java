package com.daksh.Controller;

import com.daksh.Model.ServiceOffering;
import com.daksh.Service.ServiceOfferingService;
import com.daksh.dto.CategoryDto;
import com.daksh.dto.SalonDTO;
import com.daksh.dto.ServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

private final ServiceOfferingService serviceOfferingService;

@PostMapping
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDto  serviceDto
       )
{
    SalonDTO salonDTO = new SalonDTO();
    CategoryDto categoryDto = new CategoryDto();
    categoryDto.setId(serviceDto.getCategoryId());
    salonDTO.setId(serviceDto.getSalonId());
    ServiceOffering serviceOfferings = serviceOfferingService.createService(salonDTO,serviceDto,categoryDto);
    return ResponseEntity.ok(serviceOfferings);
}

    @PostMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService(
            @PathVariable Long serviceId,
            @RequestBody ServiceOffering serviceOffering
    )throws Exception
    {


        ServiceOffering serviceOfferings = serviceOfferingService.updateService(serviceId,serviceOffering);
        return ResponseEntity.ok(serviceOfferings);
    }



}
