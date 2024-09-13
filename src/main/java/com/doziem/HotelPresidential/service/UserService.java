package com.doziem.HotelPresidential.service;

import com.doziem.HotelPresidential.exception.UserAlreadyExistException;
import com.doziem.HotelPresidential.model.Role;
import com.doziem.HotelPresidential.model.Users;
import com.doziem.HotelPresidential.repository.RoleRepository;
import com.doziem.HotelPresidential.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public Users registerUSer(Users user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistException(user.getEmail() + " already exist");
        }
        String password = user.getPassword();

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        user.setPassword(passwordEncoder.encode(password));


        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));
        user.setRoles(Collections.singletonList(userRole));

        return userRepository.save(user);
    }

    @Override
    public List<Users> getUsers() {

        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        Users theUser = getUser(email);
        if(theUser != null){
            userRepository.deleteByEmail(email);
        };
    }

    @Override
    public Users getUser(String email) {

        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
