package ru.boraldan.tack2;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Table(name= "Employee")
public class Employee {

    @Column(name = "id", primaryKey = true)
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    public Employee(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
