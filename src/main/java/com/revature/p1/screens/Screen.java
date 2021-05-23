package com.revature.p1.screens;

public abstract class Screen {

        private final String identifier;


        protected Screen(String identifier)
        {
            this.identifier = identifier;
        }

        public abstract void render();

        public String getIdentifier()
        {
            return identifier;
        }


}
