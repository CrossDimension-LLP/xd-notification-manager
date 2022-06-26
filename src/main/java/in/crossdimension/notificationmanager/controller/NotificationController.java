package in.crossdimension.notificationmanager.controller;

import com.twilio.exception.ApiException;
import in.crossdimension.notificationmanager.entity.Email;
import in.crossdimension.notificationmanager.entity.SMS;
import in.crossdimension.notificationmanager.service.EmailService;
import in.crossdimension.notificationmanager.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class NotificationController {
    @Autowired
    MessageService service;

    @Autowired
    EmailService emailService;

    @Autowired
    private SimpMessagingTemplate webSocket;

    private final String  TOPIC_DESTINATION = "/topic/sms";

    @RequestMapping(value = "/sms", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String smsSubmit(@RequestBody SMS sms) {
        try{
            service.send(sms);
        }
        catch(ApiException e){

            webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Error sending the SMS: "+e.getMessage());
            throw e;
        }
        webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": SMS has been sent!: "+sms.getTo());
        return "Code is valid till " + getTimeStamp();
    }

    @PostMapping("/email")
    public String sendEmailNotification(@RequestBody Email emailRequest) throws AddressException, MessagingException, IOException {
        emailService.sendmail(emailRequest);
        return "Email Sent Successfully";
    }

    private String getTimeStamp() {
        LocalDateTime currentTime = LocalDateTime.now();
        currentTime = currentTime.plusMinutes(1);
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(currentTime);
    }
}
