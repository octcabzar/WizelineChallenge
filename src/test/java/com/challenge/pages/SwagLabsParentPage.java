package com.challenge.pages;

import com.challenge.utils.UtilityClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.SlowLoadableComponent;

import java.time.Clock;

public abstract class SwagLabsParentPage<T extends SwagLabsParentPage<T>> extends SlowLoadableComponent<T> {

    public SwagLabsParentPage(WebDriver driver) {
        super(Clock.systemDefaultZone(), 30);
        this.driver = driver;
        this.tools = UtilityClass.getInstance(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {

    }

    protected WebDriver driver;
    protected UtilityClass tools;
}
