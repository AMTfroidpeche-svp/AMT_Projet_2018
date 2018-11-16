package ch.heigvd.amt.projet.model;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A utility class for sending e-mail messages
 * @author www.codejava.net
 *
 */
public class EmailUtility {

    private static String host = "smtp.gmail.com";
    private static String port = "587";
    private static String user = "xxx";
    private static String pass = "xxx";
    private static boolean isSetUp = false;

    public static void sendEmail(String toAddress, String subject, String message) throws MessagingException {

        if(!isSetUp){
            Properties p = new Properties();
            try {
                InputStream is = EmailUtility.class.getResourceAsStream("/config/configSMTP.utf8");
                p.load(is);
            } catch (IOException e){
            throw new RuntimeException(e);
        }
            EmailUtility.host = p.getProperty("host");
            EmailUtility.port = p.getProperty("port");
            EmailUtility.user = p.getProperty("user");
            EmailUtility.pass = p.getProperty("pass");
            EmailUtility.isSetUp = true;
        }
        if(user.equals("xxx") || pass.equals("xxx")){
            throw new RuntimeException("parameters not set up");
        }
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(user));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);

        // sends the e-mail
        Transport.send(msg);

    }
}