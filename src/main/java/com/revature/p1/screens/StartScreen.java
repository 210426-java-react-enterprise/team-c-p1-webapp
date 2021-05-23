package com.revature.p1.screens;

import com.revature.p1.utilities.Controller;
import com.revature.p1.utilities.InputValidator;
import com.revature.p1.utilities.ScreenManager;

import java.util.Scanner;

public class StartScreen extends Screen {

    private Scanner scanner;
    private InputValidator inputValidator;
    private ScreenManager screenManager;
    private Controller controller;

    public StartScreen(Scanner scanner, InputValidator inputValidator, ScreenManager screenManager, Controller controller) {
        super("/start");
        this.scanner = scanner;
        this.inputValidator = inputValidator;
        this.screenManager = screenManager;
        this.controller = controller;
    }

    @Override
    public void render() {
        int input = 0;

        System.out.println("\n\nWelcome to Sean's banking app.");
        System.out.println("******************************\n");
        System.out.print("What would you like to do,\n\n1.Create an account\n2.Login to your account\n3.Quit\n\nPlease enter your choice: ");
        try {
            String readLine = scanner.nextLine();
            input = inputValidator.validate(readLine,1, 3);
            if(input != -1)
            {
                switch (input)
                {
                    case 1:
                        screenManager.navigate("/create user");
                        break;
                    case 2:
                        screenManager.navigate("/login");
                        break;
                    case 3:
                        System.out.println("Thank you for visiting.\n\nShutting down!\n");
                        controller.setAppRunning(false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
