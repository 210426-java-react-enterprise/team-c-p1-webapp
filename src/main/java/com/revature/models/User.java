package com.revature.models;

import com.revature.p1.utils.annotations.*;

/**
 * User
 * <p>
 * POJO to represent Users within the banking application
 */
@Entity(name = "users")
public class User {

    @Key
    @Column(name = "userid")
    private int userID;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private int age;

    public User() {

    }

    public User(int userID, String username, String password, String email, String firstName, String lastName,
                int age) {
        this.userID = userID;
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAge(age);
    }

    public User(String username, String password, String email, String firstName, String lastName,
                int age) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAge(age);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userID=").append(userID);
        sb.append(", userName='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
