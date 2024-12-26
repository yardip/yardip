/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.mail.model;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public final class MailConfig {
    public final String SMTP_HOST;
    public final String SMTP_PORT;
    public final String SMTP_AUTH;
    public final String STARTTLS;
    public final String AUTH_USER;
    public final String AUTH_PASSWORD;
    public final String DEFAULT_SENDER;
    public final String DEFAULT_SENDER_LABEL;

    public MailConfig(String SMTP_HOST, String SMTP_PORT, String SMTP_AUTH, String STARTTLS, String AUTH_USER, String AUTH_PASSWORD, String DEFAULT_SENDER, String DEFAULT_SENDER_LABEL) {
        this.SMTP_HOST = SMTP_HOST;
        this.SMTP_PORT = SMTP_PORT;
        this.SMTP_AUTH = SMTP_AUTH;
        this.STARTTLS = STARTTLS;
        this.AUTH_USER = AUTH_USER;
        this.AUTH_PASSWORD = AUTH_PASSWORD;
        this.DEFAULT_SENDER = DEFAULT_SENDER;
        this.DEFAULT_SENDER_LABEL = DEFAULT_SENDER_LABEL;
    }
}
