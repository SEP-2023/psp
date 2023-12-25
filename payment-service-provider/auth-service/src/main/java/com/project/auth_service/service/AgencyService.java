package com.project.auth_service.service;

import com.project.auth_service.dto.*;
import com.project.auth_service.model.Role;
import com.project.auth_service.model.Agency;
import com.project.auth_service.repository.AgencyRepository;
import com.project.auth_service.repository.RoleRepository;
import com.project.auth_service.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgencyService {

    private final AgencyRepository agencyRepository;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;


    public RegisterAgencyResponse registerShop(RegisterAgencyDto registerAgencyDto) {
        RegisterAgencyResponse response = new RegisterAgencyResponse();
        Agency agency = new Agency();
        agency.setAgencyId(String.valueOf(UUID.randomUUID()));
        agency.setName(registerAgencyDto.getName());
        agency.setMail(registerAgencyDto.getMail());
        agency.setPassword(new BCryptPasswordEncoder().encode(registerAgencyDto.getPassword()));
        List<Role> roles = new ArrayList<Role>();
        Optional<Role> role = roleRepository.findById(1L);
        role.ifPresent(roles::add);
        agency.setRoles(roles);
        agencyRepository.save(agency);
        response.setAgencyId(agency.getAgencyId());
        return response;
    }

    public LoginResponse loginAgency(LoginDto dto){
        Agency agency = agencyRepository.getAgencyByMail(dto.getMail());
        System.out.println(agency);
        if (agency !=null){

            String roles = "";
            for(Role role : agency.getRoles()){
                roles += role.getName() + " ";
            }

            String token = null;
            if (new BCryptPasswordEncoder().matches(dto.getPassword(), agency.getPassword())) {
                token =jwtUtil.createToken(agency.getAgencyId(), roles);
                LoginResponse loginResponse=new LoginResponse();
                loginResponse.setToken(token);
                loginResponse.setRoles(roles);
                loginResponse.setAgencyId(agency.getAgencyId());
                return loginResponse;
            }
        }
        LoginResponse loginResponse=new LoginResponse();
        loginResponse.setToken(null);
        return loginResponse;
    }
}
