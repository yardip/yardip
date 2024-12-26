/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.mail;

import id.my.mdn.kupu.core.mail.model.MailConfig;
import id.my.mdn.kupu.core.mail.model.MailMessage;
import java.io.Serializable;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.commons.configuration2.Configuration;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "mailer")
@ApplicationScoped
public class Mailer implements Serializable {

    @Inject
    private MailService mailService;

    @Inject
    private Configuration configuration;
    
    private MailConfig config;
    
    @PostConstruct
    private void init() {
        config = new MailConfig(
                configuration.getString("mail.SMTPHost"), 
                configuration.getString("mail.SMTPPort"), 
                configuration.getString("mail.SMTPAuth"), 
                configuration.getString("mail.STARTTLS"),  
                configuration.getString("mail.AuthUser"), 
                configuration.getString("mail.AuthPassword"), 
                configuration.getString("mail.DefaultSender"), 
                configuration.getString("mail.DefaultSenderLabel")
        );
    }

    public void test(ActionEvent evt) {
        final String destination = "aphasan57@gmail.com"; // can be any email id 
        final String subject = "Test Email";
        final String body = "It works!";
        
        MailMessage mailMessage = new MailMessage(body, null, null, null);
        
        mailService.send(config, 
                config.DEFAULT_SENDER,
                destination,  
                config.DEFAULT_SENDER_LABEL,
                config.DEFAULT_SENDER,
                subject, 
                mailMessage);
    }

}
