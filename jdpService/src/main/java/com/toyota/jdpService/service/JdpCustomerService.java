package com.toyota.jdpService.service;

import com.toyota.jdpService.models.JdpCustomer;
import com.toyota.jdpService.repository.JdpCutomerRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JdpCustomerService {

    private final JdpCutomerRepository repo;

    public JdpCustomerService(JdpCutomerRepository repo){
        this.repo = repo;
    }

    public Page<JdpCustomer> getAll(Integer page , Integer size){
        //if no page or size is mention retungoin all the data.
        if(page == null || size == null) {
            List<JdpCustomer> allJdpCustomers = repo.findAll();
            return new PageImpl<>(allJdpCustomers);
        }
        Pageable pageable =  PageRequest.of(page , size);
        return repo.findAll(pageable);
    }

    public JdpCustomer getJdpCustomerById(Long id){
        return repo.findById(id).orElseThrow(() ->
                new NotFoundException("Customer not found with id : " + id)
        );
    }

    public JdpCustomer saveJdpCustomer(JdpCustomer jdpCustomer){
        if(jdpCustomer.getSaleDateTo().isBefore(jdpCustomer.getSaleDateFrom())){
            throw new IllegalArgumentException(
                    "Sale Date To cannot be before Sale Date From"
            );
        }
        return repo.save(jdpCustomer);
    }

    public List<JdpCustomer> saveAllJdpCustomers(List<JdpCustomer> JdpCustomerList){
        for(JdpCustomer jdpCustomer : JdpCustomerList){
            if(jdpCustomer.getSaleDateTo().isBefore(jdpCustomer.getSaleDateFrom())){
                throw new IllegalArgumentException(
                        "Sale Date To cannot be before Sale Date From"
                );
            }
        }
        return repo.saveAll(JdpCustomerList);
    }

    public void deleteJdpCustomerId(Long id){
        if(!repo.existsById(id)){
            throw new NotFoundException("Customer not found with id : " + id);
        }
        repo.deleteById(id);
    }

    public void deleteJdpCustomers(List<Long> ids){
        repo.deleteAllById(ids);
    }

    public JdpCustomer updateJdpCustomer(Long id , JdpCustomer updateJdpCustomer){
        Optional<JdpCustomer> oldJdpCustomer = repo.findById(id);

        if(oldJdpCustomer.isEmpty()){
            throw new NotFoundException(
                    "Customer not found with id : " + id);
        }

        JdpCustomer customer = oldJdpCustomer.get();

        //validation
        if(updateJdpCustomer.getSaleDateTo().isBefore(updateJdpCustomer.getSaleDateFrom())){
            throw new IllegalArgumentException(
                    "Sale Date To cannot be before Sale Date From"
            );
        }
        customer.setSaleDateFrom(updateJdpCustomer.getSaleDateFrom());
        customer.setSaleDateTo(updateJdpCustomer.getSaleDateTo());

        return repo.save(customer);
    }

    // more safer version for production-chechk if present and only then update
    public List<JdpCustomer> updateJdpCustomers(
            List<JdpCustomer> customers) {

        for(JdpCustomer customer : customers){

            if(!repo.existsById(customer.getId())){
                throw new NotFoundException(
                        "Customer not found with id : "
                                + customer.getId()
                );
            }
            if(customer.getSaleDateTo().isBefore(customer.getSaleDateFrom())){
                throw new IllegalArgumentException(
                        "Sale Date To cannot be before Sale Date From"
                );
            }
        }

        return repo.saveAll(customers);
    }

    public Page<JdpCustomer> searchCustomers(
            Boolean isDisabled,
            LocalDate saleDateFrom,
            LocalDate saleDateTo,
            int page,
            int size
    ){
        Pageable pageable = PageRequest.of(page , size);
        return repo.searchCustomers(isDisabled, saleDateFrom , saleDateTo , pageable);
    }

}