package com.toyota.jdpService.controller;

import com.toyota.jdpService.models.JdpCustomer;
import com.toyota.jdpService.service.JdpCustomerService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jdpCustomer")
@CrossOrigin(origins = "*")  //request allowed from anywhere
public class JdpCustomerController {

    private final JdpCustomerService    service;

    public JdpCustomerController(JdpCustomerService jdpCustomerService){
        this.service = jdpCustomerService;
    }

    @PostMapping("/abc")
    public ResponseEntity<?> saveCustomer(@RequestBody JdpCustomer customer){
        try{
            JdpCustomer savedCustomer = service.saveJdpCustomer(customer);
            return new ResponseEntity<>(savedCustomer , HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> saveCustomer(@RequestBody List<JdpCustomer> customers){
        try{
            List<JdpCustomer> savedCustomers = service.saveAllJdpCustomers(customers);
            return new ResponseEntity<>(savedCustomers , HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<?> getAllJdpCustomers(){
        try{
            List<JdpCustomer> jdpCustomers = service.getAll();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jdpCustomers);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJdpCustomerById(@PathVariable Long id){
        try{
            JdpCustomer customer = service.getJdpCustomerById(id);
            return new ResponseEntity<>(customer , HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    //delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try{
            service.deleteJdpCustomerbyId(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(id + ", Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    //delete all
    @DeleteMapping("/bulk")
    public ResponseEntity<?> deleteCustomers(@RequestBody List<JdpCustomer> jdpCustomers){
        try{
            service.deleteJdpCustomers(jdpCustomers);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body( "All Jdp Customers Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    //update by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@RequestBody JdpCustomer customer , @PathVariable Long id){
        try{
            JdpCustomer jdpCustomer = service.updateJdpCustomer(id , customer);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jdpCustomer);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());        }
    }

}
