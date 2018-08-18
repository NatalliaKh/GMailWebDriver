package com.epam.gmailwebdriver.service;

import com.epam.gmailwebdriver.bo.Email;
import com.epam.gmailwebdriver.bo.User;
import com.epam.gmailwebdriver.pages.HomePage;
import com.epam.gmailwebdriver.pages.LoginPage;

public class MailService {
    public HomePage loginToMail(User user) {
        return new LoginPage()
                .open()
                .fillUsername(user.getUserName())
                .enterUsername()
                .fillPassword(user.getPassword())
                .enterPassword();
    }

    public void createDraftEmail(HomePage homePage, Email email) {
        homePage.openNewEmail()
                .fillEmail(email)
                .saveToDraft();
    }
}
