package com.revature.p1.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Dispatcher
{
    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        switch (request.getRequestURI())
        {
            case "/p1_war/user":
                response.getWriter().println("<h2> user.data! we are good! </h2>");
                //System.out.println(request.getRequestURI());
                break;
            case "/p1_war/data":
                response.getWriter().println("auth.data! we are good!");
                //System.out.println(request.getRequestURI());
                break;
        }
        //System.out.println(request.getRequestURI());

    }
}
