package com.toyota.jdpService.controller;

import com.toyota.jdpService.models.JdpCustomer;
import com.toyota.jdpService.service.JdpCustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Tag(
        name = "JDP Customer Master",
        description = "CRUD APIs for Customer Management"
)
@RestController
@RequestMapping("/api/jdpCustomer")
@CrossOrigin(origins = "*")  //request allowed from anywhere
public class JdpCustomerController {

    private final JdpCustomerService service;

    public JdpCustomerController(JdpCustomerService jdpCustomerService){
        this.service = jdpCustomerService;
    }

    @Operation(
            summary = "Create customeby ID",
            description = "Cretate customer details for a given ID"
    )
    @PostMapping
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

    @Operation(
            summary = "Create customes",
            description = "Create new customers for the customer details"
    )
    @PostMapping("/bulk")
    public ResponseEntity<?> saveCustomers(@RequestBody List<JdpCustomer> customers){
        try{
            List<JdpCustomer> savedCustomers = service.saveAllJdpCustomers(customers);
            return new ResponseEntity<>(savedCustomers , HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @Operation(
    summary = "Get all customers",
    description = "Returns all customers from database"
            )
    @GetMapping
    public ResponseEntity<?> getAllJdpCustomers(
            @RequestParam(name = "page", required = false) Integer page ,
            @RequestParam(name = "size", required = false) Integer size ){
        try{
            Page<JdpCustomer> jdpCustomers = service.getAll(page , size);

//                        List<JdpCustomer> jdpCustomers = service.getAll(page , size);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jdpCustomers);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @Operation(
            summary = "Get customeby ID",
            description = "Returns customer details for a given ID"
    )
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
    @Operation(
            summary = "Delete customer by ID",
            description = "Delete customer from database by ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try{
            service.deleteJdpCustomerId(id);
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
    @Operation(
            summary = "Delete customers",
            description = "Delete all customers from database"
    )
    @DeleteMapping("/bulk")
    public ResponseEntity<?> deleteCustomers(@RequestBody List<Long> ids){
        try{
            service.deleteJdpCustomers(ids);
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
    @Operation(
            summary = "Update customerby ID",
            description = "Update customer details for a given ID"
    )
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
                    .body(e.getMessage());
        }
    }


    @PutMapping("/bulk")
    @Operation(
            summary = "Update customers",
            description = "Update all customers from database"
    )
    public ResponseEntity<?> updateCustomers(@RequestBody List<JdpCustomer> customers){
        try{
            List<JdpCustomer> updatedJdpCustomers = service.updateJdpCustomers(customers);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updatedJdpCustomers);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search filter on Customers",
            description = "Apply filter and get  those specific jdpcustomers"
    )
    public ResponseEntity<?> searchCustomers(
            @RequestParam(name = "isDisabled", required = false)
            Boolean isDisabled,
            @RequestParam(name = "saleDateFrom", required = false)
            LocalDate saleDateFrom,
            @RequestParam(name = "saleDateTo", required = false)
            LocalDate saleDateTo,
            @RequestParam(name = "page", defaultValue = "0")
            int page,
            @RequestParam(name = "size", defaultValue = "10")
            int size) {

        try {
            Page<JdpCustomer> customers =
                    service.searchCustomers(
                            isDisabled,
                            saleDateFrom,
                            saleDateTo,
                            page,
                            size);

            return ResponseEntity.ok(customers);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
