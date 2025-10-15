package com.ecom.project.service;

import com.ecom.project.exceptions.APIException;
import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Address;
import com.ecom.project.model.User;
import com.ecom.project.payload.AddressDto;
import com.ecom.project.repository.AddressRepository;
import com.ecom.project.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    public AddressRepository addressRepository;


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;


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

    @Override
    public List<AddressDto> getUserAddressesList() {

        List<Address> addressList = addressRepository.findAll();

        List<AddressDto> addressDtoList = addressList.stream()
                .map(address -> modelMapper.map(address,AddressDto.class))
                .collect(Collectors.toList());

        return addressDtoList;
    }

    @Override
    public AddressDto getAddressById(Long id) {

        Address userAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address" , "ID",id));

        if (userAddress == null){
            throw new APIException("Address Not Found");
        }

        return modelMapper.map(userAddress,AddressDto.class);
    }

    @Override
    public List<AddressDto> getAddressByUser(User user) {
        List<Address> addressList = user.getAddresses();

        List<AddressDto> userAddress = addressList.stream()
                .map(address -> modelMapper.map(address,AddressDto.class))
                .collect(Collectors.toList());

        return userAddress;
    }

    @Override
    public AddressDto updateAddress(Long addressId , AddressDto addressDto) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address", "ID",addressId));


         address.setPinCode(addressDto.getPinCode());
         address.setCity(addressDto.getCity());
         address.setBuildingName(addressDto.getBuildingName());
         address.setPinCode(addressDto.getPinCode());
         address.setCountry(addressDto.getCountry());
         address.setState(addressDto.getState());
         address.setStreet(addressDto.getStreet());

        Address updateAddress =  addressRepository.save(address);

        User user = address.getUser();
        user.getAddresses().removeIf(address1 -> address1.getAddressId().equals(addressId));
        user.getAddresses().add(updateAddress);
        userRepository.save(user);


        return modelMapper.map(updateAddress,AddressDto.class);
    }

    @Override
    public String deleteAddressById(Long addressId) {

        Address addressDelete = addressRepository.findById(addressId)
                .orElseThrow ( () -> new ResourceNotFoundException("Address" , "addressId", addressId));
        User user = addressDelete.getUser();
        user.getAddresses().removeIf(address1 -> address1.getAddressId().equals(addressId));

        userRepository.save(user);

        addressRepository.deleteById(addressId);

        return "Address Delete Successfully !!";
    }


}
