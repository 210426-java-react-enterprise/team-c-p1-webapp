package com.revature.webapp.service;

import com.revature.orm.services.ObjectService;
import com.revature.webapp.dtos.LoginDTO;
import com.revature.webapp.exceptions.CustomerNotFound;
import com.revature.orm.MyObjectRelationalMapper;
import com.revature.webapp.models.AppUser;

import javax.naming.AuthenticationException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    private ObjectService objectService;
   

    public UserService(ObjectService objectService) {
       this.objectService = objectService;
    }

   public AppUser saveUser(int id, String username, String password, String firstName, String lastName, String email){
        
        AppUser user = new AppUser(id, username, password, firstName, lastName, email);
        
        
        
        
        retunr
   }

}
