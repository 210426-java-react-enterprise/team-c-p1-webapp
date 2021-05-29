package com.revature.p1.services;

public class HtmlService
{

    public HtmlService()
    {
    }

    public <T> String singleRow(T content, String contentColor, String backGroundColor)
    {
        StringBuilder html = new StringBuilder("<!DOCTYPE html>\n<html lang=\"en\">\n<body style=\"background-color:");
        html.append(backGroundColor).append(";\">\n")
            .append("<h1 style=\"color:")
            .append(contentColor)
            .append(";\"> ")
            .append(content)
            .append("</h1>")
            .append("\n</body>\n")
            .append("</html>");
        return html.toString();
    }
}
