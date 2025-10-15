package com.ecom.project.service;

import com.ecom.project.model.User;
import com.ecom.project.payload.AddressDto;

import java.util.List;

public interface AddressService {
    public AddressDto createAddress(AddressDto address, User user);

    List<AddressDto> getUserAddressesList();

    AddressDto getAddressById(Long id);

    List<AddressDto> getAddressByUser(User user);

    AddressDto updateAddress(Long addressId , AddressDto addressDto);


    String deleteAddressById(Long addressId);

}
