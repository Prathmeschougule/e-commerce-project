package com.ecom.project.controller;

import com.ecom.project.model.User;
import com.ecom.project.payload.AddressDto;
import com.ecom.project.service.AddressService;
import com.ecom.project.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDto>>gettingAddress(){
        List<AddressDto> getUserAddressList = addressService.getUserAddressesList();
        return new ResponseEntity<>(getUserAddressList, HttpStatus.OK);
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<AddressDto>getAddressById(@PathVariable Long id ){
         AddressDto userAddress = addressService.getAddressById(id);
        return new ResponseEntity<>(userAddress, HttpStatus.OK);
    }

    @GetMapping("/user/addresses")
    public ResponseEntity <List<AddressDto>>getAddressByUser(){
        User user = authUtil.loggedInUser();
        List<AddressDto> userAddress = addressService.getAddressByUser(user);
        return new ResponseEntity<>(userAddress, HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity <AddressDto> updateAddressById(@PathVariable Long addressId , @RequestBody AddressDto addressDto){
        AddressDto userAddress = addressService.updateAddress(addressId,addressDto);
        return new ResponseEntity<>(userAddress, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId ){
        String status = addressService.deleteAddressById(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
