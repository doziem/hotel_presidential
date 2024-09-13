package com.doziem.HotelPresidential.service;

import com.doziem.HotelPresidential.exception.RoleAlreadyExistException;
import com.doziem.HotelPresidential.model.Role;
import com.doziem.HotelPresidential.model.Users;
import com.doziem.HotelPresidential.repository.RoleRepository;
import com.doziem.HotelPresidential.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role role) {
        String roleName = "ROLE_"+role.getName().toUpperCase();

        Role newRole = new Role(roleName);
        if(roleRepository.existsByName(newRole)){
            throw new RoleAlreadyExistException(role.getName() + " role already exist");
        }
        return roleRepository.save(newRole);
    }

    @Override
    public void deleteRole(Long roleId) {

        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public Users removeUserFromRole(Long userId, Long roleId) {
        Optional<Users> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);

        if(role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }

        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public Users assignRoleToUser(Long userId, Long roleId) {
        Optional<Users> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);

        if(user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new RoleAlreadyExistException(
                    user.get().getFirstName() + " is already assigned to the role " + role.get().getName());
        }
        if(role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepository.save(role.get());

        }
        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.get().removeAllUsersFromRole();
        return roleRepository.save(role.get());
    }
}
