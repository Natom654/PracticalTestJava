package com.example.practtestjava.service;

import com.example.practtestjava.model.User;



public interface UserService {

    public User create(User user);

    public User update(User user);

    public User delete(User user);
}
