package com.revature.p1.utilities;

import com.revature.p1.exceptions.IllegalInputException;
import com.revature.p1.screens.Screen;

import java.util.List;

public class ScreenManager {

    private List<Screen> screens;

    public ScreenManager(List<Screen> screens)
    {
        this.screens = screens;
    }

    public void navigate(String identifier) throws IllegalInputException
    {
        for (Screen screen : screens) {
            if (screen.getIdentifier().equals(identifier))
            {
                screen.render();
            }
        }
    }


}
