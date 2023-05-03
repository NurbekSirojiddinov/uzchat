package com.developers.uzchat.context;


import java.io.Serializable;
import java.util.Objects;

public class UserPrincipal implements Serializable {

    private String email;
    private String fullName;
    private String username;

    public UserPrincipal() {}

    public UserPrincipal(final String username) {
        this.username = username;
    }

    public UserPrincipal(final String username, final String fullName) {
        this.fullName = fullName;
        this.username = username;
    }

    public UserPrincipal(final String username, final String email, final String fullName) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPrincipal that)) return false;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "UserPrincipal{"
                + "email='"
                + email
                + '\''
                + ", fullName='"
                + fullName
                + '\''
                + ", username='"
                + username
                + '\''
                + '}';
    }
}

