package com.project.auth_service.service;

import com.project.auth_service.dto.*;
import com.project.auth_service.model.Role;
import com.project.auth_service.model.Agency;
import com.project.auth_service.repository.AgencyRepository;
import com.project.auth_service.repository.RoleRepository;
import com.project.auth_service.utils.JwtUtil;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrDataFactory;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import static dev.samstevens.totp.util.Utils.getDataUriForImage;

import java.text.MessageFormat;
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
    private final QrDataFactory qrDataFactory;
    private final QrGenerator qrGenerator;
    private final SecretGenerator secretGenerator;
    private final CodeVerifier verifier;

    private final LoggerService logger = new LoggerService(this.getClass());


    public RegisterAgencyResponse registerShop(RegisterAgencyDto registerAgencyDto) throws QrGenerationException {
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
        agency.setSecret(secretGenerator.generate());
        agencyRepository.save(agency);
        response.setAgencyId(agency.getAgencyId());
        QrData data = qrDataFactory.newBuilder().label(registerAgencyDto.getMail()).secret(agency.getSecret()).issuer("PSP").build();
        String qrCodeImage = getDataUriForImage(qrGenerator.generate(data), qrGenerator.getImageMimeType());
        response.setQrCode(qrCodeImage);
        logger.info(MessageFormat.format("Agency with mail {0} successfully registered", registerAgencyDto.getMail()));
        return response;
    }

    public LoginResponse loginAgency(LoginDto dto){
        Agency agency = agencyRepository.getAgencyByMail(dto.getMail());
        System.out.println(dto.getPin());
        if(!verifier.isValidCode(agency.getSecret(), dto.getPin())){
            agency = null;
        }
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
