package com.revature.p1.utilities;

import com.revature.p1.exceptions.IllegalInputException;
import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.persistance.ConnectionManager;
import com.revature.p1.screens.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Controller {

    boolean appRunning;
    private final Scanner scanner;
    private final Connection connection;
    private final Properties properties;
    private final ConnectionManager connectionManager;
    private final MyObjectRelationalMapper orm;
    private final InputValidator inputValidator;
    private final List<Screen> screens;
    private final StartScreen startScreen;
    private final CreateUserAccountScreen createUserAccountScreen;
    private final UserAccountLoginScreen userAccountLoginScreen;
    private final UserAccountScreen userAccountScreen;
    private final DepositScreen depositScreen;
    private final WithdrawalScreen withdrawalScreen;
    private final BankAccountScreen bankAccountScreen;
    private final ScreenManager screenManager;


    public Controller()
    {
        properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/application.properties"));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        this.connectionManager = new ConnectionManager(properties);
        this.connection = this.connectionManager.getConnection();

        this.orm = new MyObjectRelationalMapper(connection);

        this.inputValidator = new InputValidator(orm);

        scanner = new Scanner(System.in);
        this.screens = new ArrayList<>();
        this.screenManager = new ScreenManager(screens);

        this.startScreen = new StartScreen(scanner,inputValidator, screenManager,this);
        this.createUserAccountScreen = new CreateUserAccountScreen(scanner, inputValidator, orm);
        this.userAccountLoginScreen = new UserAccountLoginScreen(scanner, inputValidator, screenManager, orm);
        this.depositScreen = new DepositScreen(scanner, inputValidator, orm);
        this.withdrawalScreen = new WithdrawalScreen(scanner, inputValidator, orm);
        this.bankAccountScreen = new BankAccountScreen();
        this.userAccountScreen = new UserAccountScreen(scanner, inputValidator, orm, screenManager);

        screens.add(startScreen);
        screens.add(createUserAccountScreen);
        screens.add(userAccountLoginScreen);
        screens.add(depositScreen);
        screens.add(withdrawalScreen);
        screens.add(bankAccountScreen);
        screens.add(userAccountScreen);

        appRunning = true;
    }

    public void run()
    {
        while (appRunning)
        {
            try
            {
                screenManager.navigate("/start");
            } catch (IllegalInputException e)
            {
                e.printStackTrace();
            }
        }
    }
    public Scanner getScanner()
    {
        return scanner;
    }

    public boolean isAppRunning() {
        return appRunning;
    }

    public void setAppRunning(boolean appRunning) {
        this.appRunning = appRunning;
    }
}
