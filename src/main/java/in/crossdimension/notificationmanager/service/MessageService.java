package in.crossdimension.notificationmanager.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import in.crossdimension.notificationmanager.entity.SMS;
import in.crossdimension.notificationmanager.repository.IMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

    //Twillio Account Password "CrossDimensionLLP&2022"
    //Twillio Account id "info@crossdimension.in"

    @Autowired
    private IMessageRepository messageRepository;

    @Value("${messaging.twilio.accountSid}")
    private String ACCOUNT_SID;

    @Value("${messaging.twilio.authToken}")
    private String AUTH_TOKEN;

    @Value("${messaging.twilio.fromNumber}")
    private String FROM_NUMBER;

    public void send(SMS sms, String timestamp) {
        //Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String otp = getRandomNumber(99999, 10000);

//        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage()+" "+
//                        otp)
//                .create();
//        System.out.println("here is my id:"+message.getSid());

        sms.setNotificationId(sms.getTo());
        sms.setMessage(otp);
        sms.setTimeStamp(timestamp);
        messageRepository.save(sms);
    }
    private String getRandomNumber(int max, int min) {
        int random = (int)(Math.random()*(max-min+1)+min);
        return Integer.toString(random);
    }
}
