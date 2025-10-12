package com.ecom.project.controller;

import com.ecom.project.model.User;
import com.ecom.project.payload.AddressDto;
import com.ecom.project.repository.AddressRepository;
import com.ecom.project.service.AddressService;
import com.ecom.project.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDto>createAddress(@RequestBody AddressDto address){
        User user = authUtil.loggedInUser();
        AddressDto saveAddressDto = addressService.createAddress(address,user);
        return new ResponseEntity<AddressDto>(saveAddressDto, HttpStatus.CREATED);
    }
}
