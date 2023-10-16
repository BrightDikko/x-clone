package com.xclonebackend.xclone.services;

import com.xclonebackend.xclone.exceptions.EmailAlreadyTakenException;
import com.xclonebackend.xclone.models.ApplicationUser;
import com.xclonebackend.xclone.models.RegistrationObject;
import com.xclonebackend.xclone.models.Role;
import com.xclonebackend.xclone.repositories.RoleRepository;
import com.xclonebackend.xclone.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
//@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ApplicationUser registerUser(RegistrationObject registrationRequest) {

        ApplicationUser user = ApplicationUser.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .dateOfBirth(registrationRequest.getDob())
                .build();

        String name = user.getFirstName() + user.getLastName();

        boolean isNameUnavailable = true;

        String tempName = "";
        while(isNameUnavailable) {
            tempName = generateUsername(name);

            if (userRepository.findByUsername(tempName).isEmpty()) {
                isNameUnavailable = false;
            }
        }
        user.setUsername(tempName);

        Set<Role> roles = user.getAuthorities();
        if(roles == null) {
            roles = new HashSet<>();
        }
        roles.add(roleRepository.findByAuthority("USER").get());
        user.setAuthorities(roles);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }

    }

    private String generateUsername(String name) {
        long generatedNumber = (long) Math.floor(Math.random() * 1_000_000_000);
        return name + generatedNumber;
    }

}
