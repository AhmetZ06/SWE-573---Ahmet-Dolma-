package com.community.Community.models.Users;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.community.Community.models.Users.Profile;


@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long UserID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String surname;

    @Column(nullable = false, unique=true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique=true)
    private String username;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id") // This points to the foreign key column in the users table.
    private Profile profile;

    public User(long userID, String name, String surname, String email, String password, String username) {
        UserID = userID;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.username = username;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", UserID=" + UserID +
                '}';
    }

}
