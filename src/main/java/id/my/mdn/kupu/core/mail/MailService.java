/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.core.mail;

import id.my.mdn.kupu.core.mail.model.MailConfig;
import id.my.mdn.kupu.core.mail.model.MailMessage;
import id.my.mdn.kupu.core.security.dao.ApplicationUserFacade;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ejb.Stateless;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class MailService {

    private Session createSession(MailConfig config) {
        Properties props = new Properties();
        props.put("mail.smtp.host", config.SMTP_HOST); //SMTP Host
        props.put("mail.smtp.port", config.SMTP_PORT); //TLS Port
        props.put("mail.smtp.auth", config.SMTP_AUTH); //enable authentication
        props.put("mail.smtp.starttls.enable", config.STARTTLS); //enable STARTTLS
        final String authUser = config.AUTH_USER; //requires valid gmail id
        final String authPassword = config.AUTH_PASSWORD; // correct password for gmail id

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(authUser, authPassword);
            }
        };
        return Session.getInstance(props, auth);
    }

    public void send(MailConfig config, String sender, String destination,
            String label, String replyTo, String subject, MailMessage mailMessage) {

        Session session = createSession(config);

        try {
            MimeMessage msg = new MimeMessage(session);

            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(sender, label));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destination, false));
            msg.setSubject(subject, "UTF-8");
            msg.setReplyTo(InternetAddress.parse(replyTo, false));

            BodyPart messageBody = new MimeBodyPart();
            messageBody.setText(mailMessage.BODY);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBody);

            if (mailMessage.ATTACHMENTS != null) {
                MimeBodyPart messageAttachment = new MimeBodyPart();
                messageAttachment.attachFile(mailMessage.ATTACHMENTS);
                
                multipart.addBodyPart(messageAttachment);
            }
            
            msg.setContent(multipart);

            
            msg.setSentDate(new Date());
            Transport.send(msg);

            
        } catch (MessagingException | UnsupportedEncodingException ex) {
            Logger.getLogger(ApplicationUserFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
