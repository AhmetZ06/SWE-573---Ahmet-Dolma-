package com.community.Community.models.Users;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.community.Community.models.Posts.Post;
import com.community.Community.models.Discussion.Comments;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @ManyToMany(fetch = LAZY)
    private Set<Roles_In_Communities> rolesInCommunities;

    @OneToMany(fetch = LAZY)
    private Set<User> followers;

    @OneToMany(fetch = LAZY)
    private Set<User> following;

    @OneToMany(fetch = LAZY)
    private Set<Post> posts;

    @OneToMany(fetch = LAZY)
    private Set<Comments> comments;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profileId", referencedColumnName = "profileId")
    private Profile profile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}