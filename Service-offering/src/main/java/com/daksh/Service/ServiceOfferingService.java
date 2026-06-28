package com.daksh.Service;

import com.daksh.Model.ServiceOffering;
import com.daksh.dto.CategoryDto;
import com.daksh.dto.SalonDTO;
import com.daksh.dto.ServiceDto;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDTO, ServiceDto serDto, CategoryDto catDto);

ServiceOffering updateService(Long salonId, ServiceOffering ser) throws Exception;

//Set<ServiceOffering> getServiceOfferings(Long salonId, Long categoryId);

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);

    Set<ServiceOffering> getServiceOfferings(Set<Long> ids);
    ServiceOffering getServiceById(Long id) throws Exception;

   // Set<ServiceOffering> getServicesByIds(Set<Long> ids);

    Set<ServiceOffering> getServicesByIds(Set<Long> ids);
}
