package com.revature.p1.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class BankingServlet extends HttpServlet
{
    private final Dispatcher dispatcher = new Dispatcher();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.getWriter().println("<h1> Post - We are good! </h1>");
        dispatcher.dispatch(request, response);
        Map<String,String[]> map = request.getParameterMap();
        for (String s : map.keySet())
        {
            //System.out.println(s + "\t:\t" + Arrays.toString(map.get(s)));
        }
        //System.out.println("We are good!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.getWriter().println("<h1> Get - We are good! </h1>");
        response.getWriter().println(request.getServerPort());
        //System.out.println(request.getRequestURI());

    }
}