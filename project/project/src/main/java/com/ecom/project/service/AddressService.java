package com.ecom.project.service;

import com.ecom.project.model.User;
import com.ecom.project.payload.AddressDto;

public interface AddressService {
    public AddressDto createAddress(AddressDto address, User user);
}
