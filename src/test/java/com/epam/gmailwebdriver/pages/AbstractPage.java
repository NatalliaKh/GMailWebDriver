package com.epam.gmailwebdriver.pages;

import com.epam.gmailwebdriver.reporting.MyLogger;
import com.epam.gmailwebdriver.utils.WebDriverSingleton;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

public class AbstractPage {

    private static final int WAIT_FOR_ELEMENT_TIMEOUT_SECONDS = 10;
    private static final int POLLING_TIME_SECONDS = 5;
    protected WebDriver driver;

    protected AbstractPage() {
        this.driver = WebDriverSingleton.getWebDriverInstance();
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
        new FluentWait<>(driver)
                .withTimeout(WAIT_FOR_ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .pollingEvery(POLLING_TIME_SECONDS, TimeUnit.SECONDS)
                .ignoring(UnhandledAlertException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void highlightElement(WebDriver driver, WebElement element) {
        MyLogger.debug("Highlight element with tag name: '" + element.getTagName() + "'");
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
    }

    protected void unHighlightElement(WebDriver driver, WebElement element) {
        MyLogger.debug("Unhighlight element with tag name: '" + element.getTagName() + "'");
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='0px'", element);
    }
}
