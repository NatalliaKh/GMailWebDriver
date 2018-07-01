package com.epam.gmailwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WebDriverGMail {

    private static final int POLLING_TIME = 3;
    private static final int USER_TIMEOUT = 30;
    private WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "d:\\_webdriver\\chromedriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    private WebElement testEnterEmail() {
        WebElement gmailLogin = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.cssSelector("input#identifierId.whsOnd.zHQkBf"));
            }
        });
        Assert.assertTrue(gmailLogin.isDisplayed(), "Login field is not found.");
        WebElement gmailLoginNextButton = (new WebDriverWait (driver, 10)).until(new ExpectedCondition<WebElement>() {

            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.cssSelector("div#identifierNext"));
            }
        });
        Assert.assertTrue(gmailLoginNextButton.isDisplayed(), "Next button on login page is not found.");
        gmailLogin.sendKeys("natallia.khudzinskaya");
        gmailLoginNextButton.click();

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(USER_TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(POLLING_TIME, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        WebElement gmailPassword = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.name("password"));
            }
        });
        Assert.assertTrue(gmailPassword.isDisplayed(), "Password page is not found.");
        return gmailPassword;
    }

    private WebElement testPasswordEnter(WebElement gmailPassword) {
        WebElement gmailPasswordnNextButton = (new WebDriverWait (driver, 10)).until(new ExpectedCondition<WebElement>() {

            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.id("passwordNext"));
            }
        });
        Assert.assertTrue(gmailPasswordnNextButton.isDisplayed(), "Next button on the password page is not found.");
        gmailPassword.sendKeys("webdriver123");
        gmailPasswordnNextButton.click();
        Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
                .withTimeout(USER_TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(POLLING_TIME, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        WebElement createEmail = wait1.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.xpath("//div[@class='T-I J-J5-Ji T-I-KE L3']"));
            }
        });
        Assert.assertTrue(createEmail.isDisplayed(), "Login is failed.");
        return createEmail;
    }
    private void testCreateEmail(WebElement createEmail) {
        createEmail.click();
        WebElement toField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.name("to"));
            }
        });
        Assert.assertTrue(toField.isDisplayed(), "'To' field is not found.");
    }

    @Test
    public void testGmailOperations() {

        driver.get("https://gmail.com");
        WebElement gmailPassword = testEnterEmail();
        WebElement createEmail = testPasswordEnter(gmailPassword);
        testCreateEmail(createEmail);

    }
}
