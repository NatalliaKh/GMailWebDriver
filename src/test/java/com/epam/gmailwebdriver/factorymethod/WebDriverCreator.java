package com.epam.gmailwebdriver.factorymethod;

import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public interface WebDriverCreator {
    WebDriver createWebDriver() throws MalformedURLException;
}
