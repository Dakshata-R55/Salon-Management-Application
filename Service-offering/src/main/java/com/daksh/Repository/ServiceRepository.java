package com.daksh.Repository;

import com.daksh.Model.ServiceOffering;
import com.daksh.Service.ServiceOfferingService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ServiceRepository extends JpaRepository<ServiceOffering, Long> {

	// Find all services for a given salon
	Set<ServiceOffering> findBySalonId(Long salonId);

	// Find services for a salon filtered by category
	Set<ServiceOffering> findBySalonIdAndCategoryId(Long salonId, Long categoryId);

	// Find services by a set of ids
	List<ServiceOffering> findByIdIn(Set<Long> ids);
}
