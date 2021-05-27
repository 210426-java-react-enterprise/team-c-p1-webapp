package com.revature.p1.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.p1.services.HtmlService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WelcomeServlet extends HttpServlet
{
    private final ObjectMapper objectMapper;
    private final HtmlService htmlService;

    public WelcomeServlet(ObjectMapper objectMapper, HtmlService htmlService)
    {
        this.objectMapper = objectMapper;
        this.htmlService = htmlService;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.getWriter().println("<h1> Nothing interesting here yet! </h1>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter writer = response.getWriter();
        writer.println("<h1 style=\"color:red;\"> Welcome to your friendly neighborhood bank. </h1>");
        writer.println("<h2 style=\"color:blue;\"> To log in to your account do:\t GET to /user/login</h2>");
        writer.println("<h2 style=\"color:blue;\"> To register for a new account do:\t GET /user/new </h2>");


    }
}