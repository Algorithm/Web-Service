package com.maruti.papers.controllers;

import com.maruti.papers.services.UserService;
import com.maruti.papers.utility.RequestBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        String jsonBody  = RequestBody.getBody(request);
        JSONObject responseJson = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonBody);
            String emailAddress = (String) jsonObject.get("emailAddress");
            String password = (String) jsonObject.get("password");
            if(userService.isUserExist(emailAddress)){
                if (userService.matchPassword(emailAddress,password)){
                    responseJson.put("status",400);
                    responseJson.put("message","Login Success");

                }
                else {
                    responseJson.put("status",200);
                    responseJson.put("message","Wrong Password");
                }
            }
            else {
                responseJson.put("status",200);
                responseJson.put("message","User not exist in the database");
            }
        } catch (ParseException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.print(responseJson);
    }
}
