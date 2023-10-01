package com.example.practtestjava.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String firstName;
    private String lastName;

    private String address;
    private String phoneNumber;

    //    @NotNull(message = "The date of birth is required.")
//    @BirthDate(message = "The birth date must be greater or equal than 18")
//    @Past(message = "The date of birth must be in the past.")
    private LocalDate birthDate;

    public User(long id, String email, String firstName, String lastName, String address, String phoneNumber, LocalDate birthDate) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public User(long id, String firstName, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.birthDate = birthDate;
    }

    public User() {
    }
}
