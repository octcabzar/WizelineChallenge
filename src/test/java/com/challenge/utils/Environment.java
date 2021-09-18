package com.challenge.utils;

import com.challenge.pages.SwagLabsParentPage;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Environment {

    private WebDriver driver;
    private UtilityClass tools;
    private List<SwagLabsParentPage> instances = new ArrayList<>();

    public UtilityClass getTools() {
        return tools;
    }

    public void setTools(WebDriver driver) {
        this.tools = UtilityClass.getInstance(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public SwagLabsParentPage getInstance(Class clazz){
        SwagLabsParentPage page = null;
        Optional<SwagLabsParentPage> optional = this.instances.stream()
                .filter(instance-> clazz.isInstance(instance))
                .findAny();
        if(optional.isPresent()){
            page = optional.get();
        }else{
            try{
                Constructor<SwagLabsParentPage> constructor = clazz.getDeclaredConstructor(WebDriver.class);
                page = constructor.newInstance(this.driver);
                this.instances.add(page);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Assert.assertNotNull(page);
        return page;
    }
}
