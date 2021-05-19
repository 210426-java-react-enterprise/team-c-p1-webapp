package com.revature.p1.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dispatcher
{
    public void dispatch(HttpServletRequest request, HttpServletResponse response)
    {
        switch (request.getRequestURI())
        {
            case "p1/user.data":
                System.out.println(request.getRequestURI());
                break;
            case "p1/auth.data":
                System.out.println(request.getRequestURI());
        }
        System.out.println(request.getRequestURI());

    }
}
