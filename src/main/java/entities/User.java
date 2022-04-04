package entities;

import dao.Identifiable;
import entities.enums.Gender;
import entities.enums.Role;

import java.io.Serializable;

import static entities.enums.Role.CLIENT;

public class User implements Identifiable<Long>, Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telNumber;
    private String username;
    private String password;
    private Gender gender;
    private Role role = CLIENT;

    public User() {}

    public User(String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telNumber = telNumber;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public User(String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telNumber = telNumber;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.role = role;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("| id= ").append(id);
        sb.append(" | firstName=").append(firstName);
        sb.append(" | lastName=").append(lastName);
        sb.append(" | email=").append(email);
        sb.append(" | telNumber=").append(telNumber);
        sb.append(" | username=").append(username);
        sb.append(" | password=").append(password);
        sb.append(" | gender=").append(gender);
        sb.append(" | role=").append(role);
        return sb.toString();
    }
}
