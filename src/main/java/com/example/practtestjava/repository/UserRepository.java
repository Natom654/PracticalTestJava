package com.example.practtestjava.repository;

import com.example.practtestjava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    User findUserByBirthDate(LocalDate birthDate);

    List<User> findAllByBirthDate(LocalDate birthDate);

    List<User> findAllByBirthDateBetween(
            LocalDate birthDateFrom,
            LocalDate birthDateTo);
}


