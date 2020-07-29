package com.elinext.model;

import com.elinext.enums.Gender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class User {
    private Long id;
    private String firstName;
    private String secondName;
    private String role;
    private Gender gender;
    private String phoneNumber;

    public User(String firstName, String secondName,String role, Gender gender, String phoneNumber) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }
}
