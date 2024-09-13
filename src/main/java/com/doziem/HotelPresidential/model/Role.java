package com.doziem.HotelPresidential.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Role(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    private Collection<Users> users = new HashSet<>();

    public void assignRoleToUser(Users user){
        user.getRoles().add(this);
        this.getUsers().add(user);
    }

    public void removeUserFromRole(Users user){
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

    public void removeAllUsersFromRole(){
       if(this.getUsers() != null){
           List<Users> roleUser = this.getUsers().stream().toList();
           roleUser.forEach(this :: removeUserFromRole);
       }
    }

    public String getName(){
        return name !=null? name: "";
    }
}
