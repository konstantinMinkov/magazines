package com.kpi.magazines.utils.mail;

import lombok.extern.log4j.Log4j2;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Konstantin Minkov on 31.07.2016.
 *
 * Class, that allows sending messages over Gmail SMTP.
 */

@Log4j2
public class GmailService {

    private final static Session session;
    private final static ResourceBundle resources;

    static {
        resources = ResourceBundle.getBundle("gmail");
        final Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(resources.getString("email"),
                                resources.getString("password"));
                    }
                }
        );
    }

    /**
     * Sends message from the account, provided in gmail.properties
     * @param subject - Message subject.
     * @param messageText - Test of the message.
     * @param to - email address of the receiver (like "example@mail.com").
     * @return true, if no errors occurred.
     */
    public static boolean sendMessage(String subject, String messageText, String to) {
        final Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(resources.getString("email")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(messageText, "text/html; charset=UTF-8");
            Transport.send(message);
        } catch (MessagingException e) {
            log.catching(e);
            return false;
        }
        return true;
    }
}
