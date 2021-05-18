package com.revature.p1.screens;

import com.revature.p1.exceptions.IllegalInputException;

public abstract class Screen {

        private final String identifier;


        protected Screen(String identifier)
        {
            this.identifier = identifier;
        }

        public abstract void render() throws IllegalInputException;

        public String getIdentifier()
        {
            return identifier;
        }


}
