package com.skrbomb.eCommerce.service.interf;

import com.skrbomb.eCommerce.dto.AddressDto;
import com.skrbomb.eCommerce.dto.Response;

public interface AddressService {

    Response saveAndUpdateAddress(AddressDto addressDto);
}
