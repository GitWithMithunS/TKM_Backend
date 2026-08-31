package com.toyota.jdpService.repository;

import com.toyota.jdpService.models.JdpCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface JdpCutomerRepository extends JpaRepository<JdpCustomer , Long> {

//    public Page<JdpCustomer> findAll(Pageable pageable);

    @Query("""
    SELECT c FROM JdpCustomer c
    WHERE (:isDisabled IS NULL
        OR c.isDisabled = :isDisabled)
        AND (:saleDateFrom IS NULL
        OR c.saleDateFrom >= :saleDateFrom)
        AND (:saleDateTo IS NULL
        OR c.saleDateTo <= :saleDateTo)
    """)
    public Page<JdpCustomer> searchCustomers(
            @Param("isDisabled") Boolean isDisabled,
            @Param("saleDateFrom") LocalDate saleDateFrom,
            @Param("saleDateTo")LocalDate saleDateTo,
            Pageable pageable
            );

}
