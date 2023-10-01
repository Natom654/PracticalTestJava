package com.example.practtestjava.controller;

import com.example.practtestjava.repository.UserRepository;
import com.example.practtestjava.model.User;
import com.example.practtestjava.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class UserController {

    private final UserRepository repository;

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/persons")
    public List<User> getAllUsers() {
        return userService.findAll();
    }


    @GetMapping("/persons/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User user = userService.findById(id);
        return (ResponseEntity<User>) ResponseEntity.status(HttpStatus.OK);
    }


    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/persons/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable long id, @RequestBody User user) {
        if (user.getId() != id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!repository.existsById(id)) {
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/persons")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(
                userService.save(user),
                HttpStatus.CREATED);
    }


    @GetMapping("/persons/{birthDateFrom}/{birthDateTo}")
    public List<User> searchUsersByBirthDateBetween(@PathVariable(value = "birthDateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate, @PathVariable(value = "birthDateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        return repository.findAllByBirthDateBetween(fromDate, toDate);
    }

    @GetMapping("/getHello")
    public String getHello() {
        return "Hello, Nataly!!!";
    }
}
