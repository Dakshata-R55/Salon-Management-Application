package com.daksh.Service.impl;

import com.daksh.Model.ServiceOffering;
import com.daksh.Repository.ServiceRepository;
import com.daksh.Service.ServiceOfferingService;
import com.daksh.dto.CategoryDto;
import com.daksh.dto.SalonDTO;
import com.daksh.dto.ServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

  private final ServiceRepository serviceRepository;


    @Override
    public ServiceOffering createService(SalonDTO salonDTO, ServiceDto serDto, CategoryDto catDto) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setName(serDto.getName());
        serviceOffering.setImage(serDto.getImage());
        serviceOffering.setSalonId(1L);
        serviceOffering.setPrice(serDto.getPrice());
        serviceOffering.setCategoryId(catDto.getId());
        serviceOffering.setId(serDto.getId());
        serviceOffering.setDuration(serDto.getDuration());
        serviceOffering.setDescription(serDto.getDescription());

        return serviceRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serId, ServiceOffering ser) throws Exception {

        // find by id for update
        ServiceOffering serviceOffering = serviceRepository.findById(serId).orElse(null);

        if(serviceOffering== null){
            throw new Exception("Service not found with id: " + serId);
        }


        serviceOffering.setName(ser.getName());
        serviceOffering.setImage(ser.getImage());
       serviceOffering.setDescription(ser.getDescription());
        serviceOffering.setPrice(ser.getPrice());
        serviceOffering.setDuration(ser.getDuration());



        return serviceRepository.save(serviceOffering);
    }



    @Override
    public Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId) {
        // fetch all services for the salon first, then filter by category if provided
        Set<ServiceOffering> services = serviceRepository.findBySalonId(salonId);

        if(categoryId!=null){
            services=services.stream().filter(service -> service.getCategoryId() !=null && service.getCategoryId().equals(categoryId)).collect(Collectors.toSet());
        }

        return services;
    }

    @Override
    public Set<ServiceOffering> getServiceOfferings(Set<Long> ids) {
        // findAllById accepts Iterable<Long>
        List<ServiceOffering> service = new java.util.ArrayList<>();
        serviceRepository.findAllById(ids).forEach(service::add);
        return new HashSet<>(service);

    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {

        ServiceOffering serviceOffering = serviceRepository.findById(id).orElse(null);

        if(serviceOffering== null){
            throw new Exception("Service not found with id: " + id);
        }

return serviceOffering;
    }

    @Override
    public Set<ServiceOffering> getServicesByIds(Set<Long> ids) {
        List<ServiceOffering> list = serviceRepository.findByIdIn(ids);
        return new HashSet<>(list);
    }


}
