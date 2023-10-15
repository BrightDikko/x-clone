package com.xclonebackend.xclone.services;

import com.xclonebackend.xclone.models.ApplicationUser;
import com.xclonebackend.xclone.models.Role;
import com.xclonebackend.xclone.repositories.RoleRepository;
import com.xclonebackend.xclone.repositories.UserRepository;
import jakarta.transaction.Transactional;
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

    public ApplicationUser registerUser(ApplicationUser user) {

        Set<Role> roles = user.getAuthorities();

        if(roles == null) {
            roles = new HashSet<>();
        }

        roles.add(roleRepository.findByAuthority("USER").get());
        user.setAuthorities(roles);

        return userRepository.save(user);
    }

}
