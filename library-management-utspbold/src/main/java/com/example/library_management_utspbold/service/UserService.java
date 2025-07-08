package com.example.library_management_utspbold.service;

import com.example.library_management_utspbold.model.User;
import com.example.library_management_utspbold.model.Role;
import com.example.library_management_utspbold.repository.UserRepository;
import com.example.library_management_utspbold.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void seedDefaultUsers() {
        // Seed roles
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
        Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        // Seed admin
        if (userRepository.findByUsername("Alogo").isEmpty()) {
            User admin = new User();
            admin.setUsername("Alogo");
            admin.setPassword(passwordEncoder.encode("Alogo.24"));
            admin.setEnabled(true);
            admin.setRoles(new HashSet<>(Arrays.asList(adminRole)));
            userRepository.save(admin);
        }
        // Seed user
        if (userRepository.findByUsername("Ronaldo").isEmpty()) {
            User user = new User();
            user.setUsername("Ronaldo");
            user.setPassword(passwordEncoder.encode("Ronaldo.7"));
            user.setEnabled(true);
            user.setRoles(new HashSet<>(Arrays.asList(userRole)));
            userRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,
                authorities
        );
    }

    // Additional user CRUD methods can be added here
} 