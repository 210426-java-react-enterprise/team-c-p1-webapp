package com.revature.p1.utilities;

import com.revature.orm.MyObjectRelationalMapper;
import com.revature.p1.persistance.ConnectionManager;
import com.revature.p1.screens.*;
import com.revature.p1.services.UserService;
import com.revature.p1.services.WebUserService;
import com.revature.p1.utilities.datasource.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {

    boolean appRunning;
    private final Scanner scanner;
    private final Connection connection;

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
    private final Session session;
    private final UserService userService;
    private final WebUserService webUserService;
    private final Logger logger;

    public Controller()
    {
        this.logger = LogManager.getLogger();
        logger.info("Initializing objects and dependencies");
        this.connectionManager = new ConnectionManager();
        this.connection = connectionManager.getConnection();
        this.orm = new MyObjectRelationalMapper(connection);
        this.session = new Session(null, null, null, null,orm);
        this.userService = new UserService(orm, null, session);

        this.inputValidator = new InputValidator(orm);
        this.webUserService = new WebUserService(orm,inputValidator,logger);
        scanner = new Scanner(System.in);
        this.screens = new ArrayList<>();
        this.screenManager = new ScreenManager(screens);

        this.startScreen = new StartScreen(scanner,inputValidator, screenManager,this);
        this.createUserAccountScreen = new CreateUserAccountScreen(scanner, inputValidator, orm);
        this.userAccountLoginScreen = new UserAccountLoginScreen(scanner, inputValidator, screenManager, orm,session);
        this.depositScreen = new DepositScreen(scanner, inputValidator, orm,session);
        this.withdrawalScreen = new WithdrawalScreen(scanner, inputValidator, orm,session);
        this.bankAccountScreen = new BankAccountScreen(session);
        this.userAccountScreen = new UserAccountScreen(scanner, inputValidator, orm, screenManager,session);

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
            screenManager.navigate("/start");
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
