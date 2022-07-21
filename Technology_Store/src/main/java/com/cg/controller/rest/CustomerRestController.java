package com.cg.controller.rest;

import com.cg.model.Customer;
import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.service.product.IProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {
    @Autowired
    private ICustomerService customerService;


    @GetMapping
    public ResponseEntity<?> showListCustomer() {

        Iterable<Customer> customers = customerService.findAll();

        if (customers == null) {
            return new ResponseEntity<>("Danh sách trống!", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);

        if (!customer.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy customer có id là:" + id + "!", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/block/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doBlock(@PathVariable Long id, BindingResult bindingResult){
        Optional<Customer> customer = customerService.findById(id);

        if (!customer.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy customer có id là:" + id + "!", HttpStatus.NO_CONTENT);
        }


        if(bindingResult.hasErrors()){
            return AppUtils.errors(bindingResult);
        }

        try{
            if(customer.get().getStatus().getId() == 1) {
                customerService.blockCustomer(id);
            }else {
                customerService.unlockCustomer(id);
            }


            return new ResponseEntity<>(id,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Server không xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
