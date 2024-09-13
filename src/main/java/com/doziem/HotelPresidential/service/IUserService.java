package com.doziem.HotelPresidential.service;

import com.doziem.HotelPresidential.model.Users;

import java.util.List;

public interface IUserService {

    Users registerUSer(Users user);

    List<Users> getUsers();

    void deleteUser(String email);

    Users getUser(String email);
}
