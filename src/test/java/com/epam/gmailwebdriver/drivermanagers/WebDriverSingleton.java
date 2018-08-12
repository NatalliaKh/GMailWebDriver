package com.epam.gmailwebdriver.drivermanagers;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

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
//        DesiredCapabilities capabilities = DesiredCapabilities.firefox(); // use firefox node
        DesiredCapabilities capabilities = DesiredCapabilities.chrome(); // use chrome node
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--lang=en");
        options.setAcceptInsecureCerts(true);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
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
