package com.epam.gmailwebdriver.factorymethod;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class FirefoxDriverCreator implements WebDriverCreator {
    public WebDriver createWebDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        return new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
    }
}
