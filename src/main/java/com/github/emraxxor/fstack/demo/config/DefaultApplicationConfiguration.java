package com.github.emraxxor.fstack.demo.config;

import lombok.AllArgsConstructor;

public class DefaultApplicationConfiguration {

    @AllArgsConstructor
    public enum ES_APP_INDICES {

        PHOTO("picture");

        String name;

        public String val() {
            return name;
        }
    }
}
