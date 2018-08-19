package com.epam.gmailwebdriver.drivermanagers;

import com.epam.gmailwebdriver.decorator.CustomDriverDecorator;
import com.epam.gmailwebdriver.factorymethod.ChromeDriverCreator;
import com.epam.gmailwebdriver.factorymethod.WebDriverCreator;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public class WebDriverSingleton {
    private static WebDriver instance;

    private WebDriverSingleton() {
    }

    public static WebDriver getWebDriverInstance() {
        if (instance == null) {
            try {
                instance = init();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private static WebDriver init() throws MalformedURLException {
        WebDriverCreator webDriverCreator = new ChromeDriverCreator();
        return new CustomDriverDecorator(webDriverCreator.createWebDriver());
    }

    public static void kill() {
        if (instance != null) {
            try {
                instance.quit();
            } catch (Exception e) {
                System.out.println("Cannot kill browser");
            } finally {
                instance = null;
            }
        }
    }
}
