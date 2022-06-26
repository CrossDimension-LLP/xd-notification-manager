package in.crossdimension.notificationmanager.service;


import in.crossdimension.notificationmanager.entity.Email;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {
    public void sendmail(Email emailRequest) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailRequest.getSenderAddress(), emailRequest.getSenderPassword());
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(emailRequest.getSenderAddress(), false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRequest.getRecipientAddress()));
        msg.setSubject(emailRequest.getMailSubject());
        msg.setContent(emailRequest.getMailBody(), "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailRequest.getMailBody(), "text/html");

        Transport.send(msg);
    }
}
