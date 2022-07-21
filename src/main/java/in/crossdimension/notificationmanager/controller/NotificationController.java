package in.crossdimension.notificationmanager.controller;

import com.twilio.exception.ApiException;
import in.crossdimension.notificationmanager.entity.Email;
import in.crossdimension.notificationmanager.entity.IncomingSMS;
import in.crossdimension.notificationmanager.entity.SMS;
import in.crossdimension.notificationmanager.service.EmailService;
import in.crossdimension.notificationmanager.service.MessageService;
import in.crossdimension.notificationmanager.service.OTPValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static in.crossdimension.notificationmanager.config.NotificationConstants.DATE_TIME_FORMAT;

@RestController("/notification")
@Slf4j
public class NotificationController {
    @Autowired
    MessageService service;

    @Autowired
    EmailService emailService;

    @Autowired
    OTPValidation otpValidation;

    @Autowired
    private SimpMessagingTemplate webSocket;

    private final String  TOPIC_DESTINATION = "/topic/sms";

    @RequestMapping(value = "/sms", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String smsSubmit(@RequestBody SMS sms) {
        String timeStamp = getTimeStamp();
        try{
            log.debug("Request Body for sms service is {}", sms);
            service.send(sms, timeStamp);
        }
        catch(ApiException e){

            webSocket.convertAndSend(TOPIC_DESTINATION, timeStamp + ": Error sending the SMS: "+e.getMessage());
            log.error("API exception for SMS service", e);
            throw e;
        }
        webSocket.convertAndSend(TOPIC_DESTINATION, timeStamp + ": SMS has been sent!: "+sms.getTo());
        return "Code is valid till " + timeStamp;
    }

    @PostMapping("/email")
    public String sendEmailNotification(@RequestBody Email emailRequest) throws AddressException, MessagingException, IOException {
        log.debug("Sending Email for Email Service with payload {}", emailRequest);
        emailService.sendmail(emailRequest);
        return "Email Sent Successfully";
    }


    @PostMapping("/validation")
    public String otpValidate(@RequestBody IncomingSMS incomingSMS){
        log.debug("Starting up the OTP Validation {}", incomingSMS);
        return otpValidation.isOtpValid(incomingSMS);
    }

    private String getTimeStamp() {
        LocalDateTime currentTime = LocalDateTime.now();
        currentTime = currentTime.plusMinutes(1);
        return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(currentTime);
    }
}
