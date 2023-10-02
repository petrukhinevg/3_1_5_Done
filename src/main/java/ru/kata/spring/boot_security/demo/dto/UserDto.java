package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.Objects;
import java.util.Set;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private int age;
    private Set<Role> roles;

    public UserDto() {
    }

    public UserDto(Long id, String username, String password, String firstName, String lastName, int age, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = firstName;
        this.age = age;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && age == userDto.age && Objects.equals(username, userDto.username) && Objects.equals(password, userDto.password)  && Objects.equals(email, userDto.email) && Objects.equals(roles, userDto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, age, roles);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + email + '\'' +
                ", age=" + age +
                ", roles=" + roles +
                '}';
    }
}
