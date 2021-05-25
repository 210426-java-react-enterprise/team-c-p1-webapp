package controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController {

    public void authenticate (HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //Parsing the JSON
        Map<String, Object> jsonMap = new ObjectMapper().readValue(req.getInputStream(), HashMap.class);

        //Call #authenticate with username and password from json




    }


}
