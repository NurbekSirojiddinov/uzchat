package com.developers.uzchat.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity(name = "\"user\"")
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private Instant status; //last seen

    @Column(unique = true)
    private String username;

    private byte[] profilePhoto;
    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Group> groups;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ChannelEntity> channels; // List of my channels

    @ManyToMany(mappedBy = "members")
    List<Group> membershipGroups; //List of Groups that I'm member of

    @ManyToMany(mappedBy = "participants")
    List<Conversation> conversations;

    public User() {
        groups = new ArrayList<>();
        membershipGroups = new ArrayList<>();
        conversations = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "bio='" + bio + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", groups=" + groups +
                ", membershipGroups=" + membershipGroups +
                ", conversations=" + conversations +
                '}';
    }

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
