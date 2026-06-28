package com.Daksh.user.service.Service;

import com.Daksh.user.service.Model.User;

import java.util.List;

public interface UserService {

    User CeateUser( User user);
    User getUser( User user);
    List<User> getAllUsers();
    void deleteUser( User user);
User updateUser( User user);

    List<User> getAllUsers();
}
