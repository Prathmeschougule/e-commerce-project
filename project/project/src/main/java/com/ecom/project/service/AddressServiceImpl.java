package com.ecom.project.service;

import com.ecom.project.model.Address;
import com.ecom.project.model.User;
import com.ecom.project.payload.AddressDto;
import com.ecom.project.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    public AddressRepository addressRepository;


    @Autowired
    ModelMapper modelMapper;


    @Override
    public AddressDto createAddress(AddressDto address, User user){


        Address address1 = modelMapper.map(address,Address.class);

        List<Address>addressList = user.getAddresses();

        addressList.add(address1);

        user.setAddresses(addressList);

        address1.setUser(user);
        Address savedAddress = addressRepository.save(address1);

        return  modelMapper.map(savedAddress,AddressDto.class);

//        Address address1 = new Address();
//
//        address1.setAddressId(address.getAddressId());
//        address1.setCity(address.getCity());
//        address1.setState(address.getState());
//        address1.setCountry(address.getCountry());
//        address1.setStreet(address.getStreet());
//        address1.setBuildingName(address.getBuildingName());
//        address1.setPinCode(address.getPinCode());
//
//        addressRepository.save(address1);


    }
}
