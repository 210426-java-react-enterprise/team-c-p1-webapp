package com.revature.services;

import com.revature.dtos.Credentials;
import com.revature.dtos.newUserDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.User;
import com.revature.p1.utils.EntityManager;

import java.util.List;

public class UserService {
    private EntityManager em;

    public UserService(EntityManager em) {
        this.em = em;
    }

    public User validateUser(Credentials creds) {
        List<User> soughtUser = em.getAllOnCondition(User.class, "username", creds.getUsername());

        if (soughtUser != null && !soughtUser.isEmpty() && soughtUser.get(0).getPassword().equals(creds.getPassword())) {
            return soughtUser.get(0);
        } else {
            throw new AuthenticationException("The username or password was invalid");
        }
    }

    public User getUser(int id) {
        return (User) em.get(User.class, id);
    }


    public User validateUser(newUserDTO user) {

        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty() || user.getFirstName().length() > 50)
            throw new InvalidRequestException("The first name was invalid");

        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getEmail().length() > 255)
            throw new InvalidRequestException("The email was invalid");

        if (user.getPassword() == null || user.getPassword().trim().isEmpty())
            throw new InvalidRequestException("The password was invalid");

        if (user.getLastName() == null || user.getLastName().trim().isEmpty() || user.getLastName().length() > 50)
            throw new InvalidRequestException("The last name was invalid");

        if (user.getUsername() == null || user.getUsername().trim().isEmpty() || user.getUsername().length() > 50)
            throw new InvalidRequestException("The first name was invalid");

       try {
           int age = Integer.parseInt(user.getAge());
           if(age < 0 || age >= 200)
               throw new InvalidRequestException("The age was invalid");

           User userToBeSaved = new User(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(),
            user.getLastName(), age);
           return em.save(userToBeSaved);
       } catch (NumberFormatException e) {
           throw new InvalidRequestException("The age was invalid");
       }

    }

}

