package com.epam.gmailwebdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DraftEmailPage extends HomePage {

    @FindBy(name = "to")
    WebElement emailReceiverField;

    @FindBy(xpath = "//div[@class='oL aDm az9']/child::span")
    WebElement emailReceiverAddress;

    @FindBy(name = "subjectbox")
    WebElement emailSubjectField;

    @FindBy(xpath = "//div[@aria-label='Message Body']")
    WebElement emailBodyField;

    @FindBy(xpath = "//img[@aria-label='Save & Close']")
    WebElement saveToDraft;

    @FindBy(xpath = "//div[text()='Send' and @role='button']")
    WebElement sendEmail;

    @FindBy(xpath = "//div[@aria-label='Discard draft' and @role='button']")
    WebElement removeEmail;

    protected DraftEmailPage(WebDriver driver) {
        super(driver);
    }

    public DraftEmailPage fillEmailReceiver(String receiverAddress) {
        waitForElementVisible(emailReceiverField);
        emailReceiverField.sendKeys(receiverAddress);
        return this;
    }

    public DraftEmailPage fillEmailSubject(String subject) {
        waitForElementVisible(emailSubjectField);
        emailSubjectField.sendKeys(subject);
        return this;
    }

    public DraftEmailPage fillEmailBody(String body) {
        waitForElementVisible(emailBodyField);
        emailBodyField.sendKeys(body);
        return this;
    }

    public HomePage saveToDraft() {
        waitForElementVisible(saveToDraft);
        saveToDraft.click();
        return this;
    }

    public String getEmailReceiverAddress() {
        waitForElementVisible(emailReceiverAddress);
        return emailReceiverAddress.getText();
    }

    public String getEmailSubject() {
        waitForElementVisible(emailSubjectField);
        return emailSubjectField.getAttribute("value");
    }

    public String getEmailBody() {
        waitForElementVisible(emailBodyField);
        return emailBodyField.getText();
    }

    public HomePage sendEmail() {
        waitForElementVisible(sendEmail);
        sendEmail.click();
        return new HomePage(driver);
    }

    public HomePage removeEmail() {
        waitForElementVisible(removeEmail);
        removeEmail.click();
        return new HomePage(driver);
    }
}
