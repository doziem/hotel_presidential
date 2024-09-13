package com.doziem.HotelPresidential.service;

import com.doziem.HotelPresidential.model.Role;
import com.doziem.HotelPresidential.model.Users;

import java.util.List;

public interface IRoleService {

    List<Role> getRoles();

    Role createRole(Role role);

    void deleteRole(Long id);

    Role findByName(String name);

    Users removeUserFromRole(Long userId, Long roleId);

    Users assignRoleToUser(Long userId, Long roleId);

    Role removeAllUsersFromRole(Long roleId);
}
