package com.epam.gmailwebdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;

public class AbstractPage {

    private static final int WAIT_FOR_ELEMENT_TIMEOUT_SECONDS = 10;
    protected WebDriver driver;

    protected AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    protected void waitForElementVisible(WebElement element) {
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForElementVisible(By locator) {
        return (new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT_SECONDS))
                .until(new ExpectedCondition<WebElement>() {
                    @Nullable
                    public WebElement apply(@Nullable WebDriver webDriver) {
                        return webDriver.findElement(locator);
                    }
        });
    }

    protected void waitForElementInvisible(By locator) {
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
