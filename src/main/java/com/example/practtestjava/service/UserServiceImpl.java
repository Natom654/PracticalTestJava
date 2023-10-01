package com.example.practtestjava.service;

import com.example.practtestjava.model.User;
import com.example.practtestjava.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl {

    private UserRepository userRepository;
    private static List<User> users = new ArrayList<>();
    private static long idCounter = 0;

    static {
        users.add(new User(++idCounter, "111@gmail.com", "Nataly", "Omel", "Odessa", "12345", LocalDate.of(1981, 12, 31)));
        users.add(new User(++idCounter, "222@gmail.com", "Andrey", "Kol","Lviv", "54321", LocalDate.of(1986, 3, 10)));
        users.add(new User(++idCounter, "333@gmail.com", "Ivan", "Val","NY", "35264", LocalDate.of(2015, 9, 25)));
        users.add(new User(++idCounter, "444@gmail.com", "Kate", "Doll","london", "33224", LocalDate.of(1990, 05, 12)));
    }



    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == -1 || user.getId() == 0) {
            users.add(user);
        } else {
            deleteById(user.getId());
            users.add(user);
        }
        return user;
    }

    public User deleteById(long id) {
        User user = findById(id);

        if (user == null)
            return null;

        if (users.remove(user)) {
            return user;
        }
        return null;
    }

    public User findById(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}


