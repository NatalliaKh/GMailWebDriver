package com.epam.gmailwebdriver.pages;

import com.epam.gmailwebdriver.bo.Email;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DraftEmailPage extends HomePage {

    @FindBy(name = "to")
    private WebElement emailReceiverField;

    @FindBy(xpath = "//div[@class='oL aDm az9']/child::span")
    private WebElement emailReceiverAddress;

    @FindBy(name = "subjectbox")
    private WebElement emailSubjectField;

    @FindBy(xpath = "//div[@aria-label='Message Body']")
    private WebElement emailBodyField;

    @FindBy(xpath = "//img[@aria-label='Save & Close']")
    private WebElement saveToDraft;

    @FindBy(xpath = "//div[text()='Send' and @role='button']")
    private WebElement sendEmail;

    @FindBy(xpath = "//div[@aria-label='Discard draft' and @role='button']")
    private WebElement removeEmail;

    public DraftEmailPage() {
        super();
    }

    public DraftEmailPage fillEmail(Email email) {
        waitForElementVisible(emailReceiverField);
        emailReceiverField.sendKeys(email.getReceiver());
        waitForElementVisible(emailSubjectField);
        emailSubjectField.sendKeys(email.getSubject());
        waitForElementVisible(emailBodyField);
        emailBodyField.sendKeys(email.getBody());
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
        return new HomePage();
    }

    public HomePage removeEmail() {
        waitForElementVisible(removeEmail);
        removeEmail.click();
        return new HomePage();
    }
}
