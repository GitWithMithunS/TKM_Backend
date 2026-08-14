package com.toyota.jdpService.repository;

import com.toyota.jdpService.models.JdpCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JdpCutomerRepository extends JpaRepository<JdpCustomer , Long> {
    
}
