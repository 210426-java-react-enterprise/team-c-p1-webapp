package com.revature.webapp.service;

import com.revature.orm.services.ObjectService;
import com.revature.webapp.exceptions.ObjectNotSaved;
import com.revature.webapp.models.AppUser;

public class UserService {
    private ObjectService objectService;
   

    public UserService(ObjectService objectService) {
       this.objectService = objectService;
    }

   public AppUser saveUser(int id, String username, String password, String firstName, String lastName, String email){
        
        AppUser user = new AppUser(id, username, password, firstName, lastName, email);
        if(!objectService.sendObjectToDB(user)){
            throw new ObjectNotSaved();
        }
        
        return user;
   }
   
   public AppUser bringUser(Class<?> AppUser, String field,String fieldValue){
        AppUser userData = new AppUser();
        userData= objectService.bringObjectFromDbByField(AppUser, field, fieldValue);
        return userData;
   }
   

}
