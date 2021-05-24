package com.revature.p1.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class BankingServlet extends HttpServlet
{
    private final Dispatcher dispatcher = new Dispatcher();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(request.getReader());

        String line;
        while ((line = reader.readLine()) != null)
        {
            stringBuilder.append(line);
        }
        String data = stringBuilder.toString();
//
        Map<String,Object> map;
        map = objectMapper.readValue(data, Map.class);
        Map<String, Object> finalMap = map;
        map.keySet().forEach(key -> {
            try
            {
                response.getWriter().println(key + "\t" + finalMap.get(key));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });

        response.getWriter().println("<h1> Post - We are good! </h1>");
        dispatcher.dispatch(request, response);
//        Map<String,String[]> map = request.;
//        for (String s : map.keySet())
//        {
//            response.getWriter().println("<h2> " + s + "\t:\t" + Arrays.toString(map.get(s)) + " </h2>");
//
//        }
//        System.out.println("We are good!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        dispatcher.dispatch(request, response);
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.getWriter().println("<h1> username: " + username +" </h1>");
        response.getWriter().println("<h1> password: " + password +" </h1>");


        //System.out.println(request.getRequestURI());

    }
}