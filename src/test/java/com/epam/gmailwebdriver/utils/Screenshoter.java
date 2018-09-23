package com.epam.gmailwebdriver.utils;

import com.epam.gmailwebdriver.reporting.MyLogger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Screenshoter {
    private static final String SCREENSHOTS_NAME_TPL = "screenshots/scr";

    public static String takeScreenshot() {
        WebDriver driver = WebDriverSingleton.getWebDriverInstance();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = SCREENSHOTS_NAME_TPL + System.nanoTime();
            String srcPath = screenshotName + ".jpg";
            File copy = new File(srcPath);
            FileUtils.copyFile(screenshot, copy);
            MyLogger.info("Saved screenshot: " + screenshotName);
            MyLogger.attach(srcPath, "Screenshot");
            return srcPath;
        } catch (IOException e) {
            MyLogger.warn("Failed to make screenshot");
            return "";
        }
    }
}


