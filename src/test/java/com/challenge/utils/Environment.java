package com.challenge.utils;

import org.openqa.selenium.WebDriver;

public class Environment {

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
