package com.Daksh.user.service.Controller;

import com.Daksh.user.service.Exception.UserException;
import com.Daksh.user.service.Model.User;
import com.Daksh.user.service.Repository.Repository;
import com.Daksh.user.service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    private Repository userRepository;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    public User createUser(@RequestBody User user) {
        return userService.CeateUser(user);
    }

    @GetMapping("/api/users")
    public List<User> getUser() {
        List<User> users = userService.getAllUsers();
        return users;
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable Long id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("User not found ");
    }

    @PutMapping("/api/user/{id}")
    public User upateUser(@RequestBody User user, @PathVariable Long id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            throw new UserException("User not found with id" + id);
        }

        User existingUser=opt.get();

        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    @DeleteMapping("/api/users/{id}")
    public String deleteUserId(@PathVariable Long id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            throw new UserException("User not found with id" + id);
        }
        userRepository.deleteById(id);
        return "User deleted with id";
    }


}
