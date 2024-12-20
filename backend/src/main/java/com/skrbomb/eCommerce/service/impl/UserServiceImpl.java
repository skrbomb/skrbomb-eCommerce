package com.skrbomb.eCommerce.service.impl;

import com.skrbomb.eCommerce.dto.LoginRequest;
import com.skrbomb.eCommerce.dto.Response;
import com.skrbomb.eCommerce.dto.UserDto;
import com.skrbomb.eCommerce.entity.User;
import com.skrbomb.eCommerce.enums.UserRole;
import com.skrbomb.eCommerce.exception.InvalidCredentialsException;
import com.skrbomb.eCommerce.exception.NotFoundException;
import com.skrbomb.eCommerce.mapper.EntityDtoMapper;
import com.skrbomb.eCommerce.repository.UserRepo;
import com.skrbomb.eCommerce.security.JwtUtils;
import com.skrbomb.eCommerce.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EntityDtoMapper entityDtoMapper;


    @Override
    public Response registerUser(UserDto registrationRequest) {

        UserRole role=UserRole.USER;

        if(registrationRequest.getUserRole()!=null && registrationRequest.getUserRole().equalsIgnoreCase("admin")){
            role = UserRole.ADMIN;
        }

        User user=User.builder()
                .name(registrationRequest.getName())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .userRole(role)
                .build();

        User savedUser=userRepo.save(user);

        UserDto userDto=entityDtoMapper.mapUserToDtoBasic(savedUser);

        return Response.builder()
                .status(200)
                .message("User registered Successfully")
                .user(userDto)
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {

        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Email not found."));

        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw new InvalidCredentialsException("Password does not match.");
        }

        String token= jwtUtils.generateToken(user);

        return Response.builder()
                .status(200)
                .message("Login Success")
                .token(token)
                .expirationTime("6 Months")
                .role(user.getUserRole().name())
                .build();
    }

    @Override
    public Response getAllUsers() {

        List<User> users = userRepo.findAll();
        List<UserDto> userDtos= users.stream()
                .map(entityDtoMapper::mapUserToDtoBasic)
                .toList();

        return Response.builder()
                .status(200)
                .message("Successful")
                .userList(userDtos)
                .build();
    }

    @Override
    public User getLoginUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        log.info("User Email :"+email);

        return userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found."));
    }

    @Override
    public Response getUserInfoAndOrderHistory() {

        User user=getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoPlusAddressAndOrderItem(user);

        return Response.builder()
                .status(200)
                .user(userDto)
                .build();
    }
}
