package com.challenge.steps;

import com.challenge.utils.Environment;
import com.challenge.utils.UtilityClass;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class ConfigSteps {

    public Environment environment;

    public ConfigSteps(Environment environment){
        this.environment = environment;
    }

    @Before("@chrome")
    public void setChromeDriver(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        environment.setDriver(driver);
        UtilityClass.getInstance(driver);
    }

    @Before("@firefox")
    public void setFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        environment.setDriver(driver);
        UtilityClass.getInstance(driver);
    }

    @Before("@edge")
    public void setEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();
        environment.setDriver(driver);
        UtilityClass.getInstance(driver);
    }

    @Before("@ie")
    public void setIEDriver() {
        WebDriverManager.iedriver().setup();
        WebDriver driver = new InternetExplorerDriver();
        environment.setDriver(driver);
        UtilityClass.getInstance(driver);
    }

    @After("@challenge")
    public void writeReport(){
        environment.getDriver().close();
    }
}
