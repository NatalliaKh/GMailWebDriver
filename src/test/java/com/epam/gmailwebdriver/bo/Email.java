package com.epam.gmailwebdriver.bo;

public class Email {
    private final String receiver;
    private final String subject;
    private final String body;

    public Email(String receiver, String subject, String body){
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    public String getReceiver() {
        return receiver;
    }
    public String getSubject() {
        return subject;
    }
    public String getBody() {
        return body;
    }
}
