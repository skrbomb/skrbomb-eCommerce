package com.skrbomb.eCommerce.service.interf;

import com.skrbomb.eCommerce.dto.LoginRequest;
import com.skrbomb.eCommerce.dto.Response;
import com.skrbomb.eCommerce.dto.UserDto;
import com.skrbomb.eCommerce.entity.User;

public interface UserService {


    Response registerUser(UserDto registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();
}
