package com.skrbomb.eCommerce.service.impl;


import com.skrbomb.eCommerce.dto.AddressDto;
import com.skrbomb.eCommerce.dto.Response;
import com.skrbomb.eCommerce.entity.Address;
import com.skrbomb.eCommerce.entity.User;
import com.skrbomb.eCommerce.mapper.EntityDtoMapper;
import com.skrbomb.eCommerce.repository.AddressRepo;
import com.skrbomb.eCommerce.service.interf.AddressService;
import com.skrbomb.eCommerce.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;
    private final UserService userService;
    private final EntityDtoMapper entityDtoMapper;


    @Override
    public Response saveAndUpdateAddress(AddressDto addressDto) {

        User user=userService.getLoginUser();

        Address address=user.getAddress();

        if(address==null){
            address=new Address();
            address.setUser(user);
        }

        if(addressDto.getStreet()!=null) address.setStreet(addressDto.getStreet());
        if(addressDto.getCity()!=null) address.setCity(addressDto.getCity());
        if(addressDto.getState()!=null) address.setState(addressDto.getState());
        if(addressDto.getCountry()!=null) address.setCountry(addressDto.getCountry());
        if(addressDto.getZipCode()!=null) address.setZipCode(addressDto.getZipCode());

        addressRepo.save(address);
        String message=(user.getAddress()==null)?"Address Created Successfully":"Address Updated Successfully";

        return Response.builder()
                .status(200)
                .message(message)
                .build();
    }
}
