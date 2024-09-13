package com.doziem.HotelPresidential.controller;

import com.doziem.HotelPresidential.exception.RoleAlreadyExistException;
import com.doziem.HotelPresidential.model.Role;
import com.doziem.HotelPresidential.model.Users;
import com.doziem.HotelPresidential.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles(){

        return new ResponseEntity<>(roleService.getRoles(),FOUND);
    }

    @PostMapping("/create-role")
    public ResponseEntity<String> createRole(@RequestBody Role role){

        try{
            roleService.createRole(role);
            return ResponseEntity.ok("New role created successfully!");
        }catch (RoleAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId){
       roleService.deleteRole(roleId);
    }

    @PostMapping("/remove-all-users-role/{roleId}")
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId) {
        return roleService.removeAllUsersFromRole(roleId);
    }

    @PostMapping("/remove-user-from-role")
    public Users removeUserFromRole(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId){
        return roleService.removeUserFromRole(userId,roleId);
    }

    @PostMapping("/assign-role-to-user")
    public Users assignRoleToUser(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId){
        return roleService.assignRoleToUser(userId,roleId);
    }
}
