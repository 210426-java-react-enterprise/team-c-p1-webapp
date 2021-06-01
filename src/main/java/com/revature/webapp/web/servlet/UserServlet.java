package com.revature.webapp.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.orm.util.HtmlBuilder;
import com.revature.webapp.dtos.UserDTO;
import com.revature.webapp.dtos.UserDataRequestDTO;
import com.revature.webapp.exceptions.ObjectNotSaved;
import com.revature.webapp.models.AppUser;
import com.revature.webapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class UserServlet extends HttpServlet {

    private final UserService userService;

    public UserServlet(UserService userService){
        this.userService = userService;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter write = resp.getWriter();
        resp.setContentType("application/json");
        
        try{
            UserDTO userData = mapper.readValue(req.getInputStream(), UserDTO.class);
    
            AppUser newUser = userService.saveUser(userData.getId(),userData.getUsername(),userData.getPassword(),
                                                   userData.getFirstName(), userData.getLastName(),
                                                   userData.getEmail());
            
            resp.setStatus(200);
            write.write("The user was saved!!!");
            
        }catch (ObjectNotSaved e){
            resp.setStatus(409);
            write.write("The user info could not be saved!!!");
            
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    
    
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 
    
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter write = resp.getWriter();
        resp.setContentType("application/json");
    
        UserDataRequestDTO userRequest = mapper.readValue(req.getInputStream(), UserDataRequestDTO.class);
        AppUser user = new AppUser();
        user = userService.bringUser(AppUser.class, userRequest.getUserField(),userRequest.getUserFieldValue());
        if (user == null){
            resp.setStatus(404);
            write.write("The user couldn't be found");
        }else{
            resp.setStatus(200);
            ArrayList<String> arrayData = new ArrayList<String>(Arrays.asList(user.toString().split(",")));
            HashMap<String,String> dataTable = new HashMap<>();
            arrayData.forEach(str -> {
                String[] supportArray = str.split("=");
                dataTable.put(supportArray[0].trim(),supportArray[1].trim());
            });
            write.write(HtmlBuilder.buildHtmlTable("Here is your user data",dataTable));
        }
        
    }
    
}

