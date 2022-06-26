package in.crossdimension.notificationmanager.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import in.crossdimension.notificationmanager.entity.SMS;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

    //Twillio Account Password "CrossDimensionLLP&2022"
    //Twillio Account id "info@crossdimension.in"

    private String ACCOUNT_SID = "ACaabaf7514b819edceeddecbe5975a155";

    private String AUTH_TOKEN = "e1a42725e782a28545cbeb1c7ec34f87";

    private String FROM_NUMBER= "+19207988494";

    public void send(SMS sms) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage()+" "+
                        getRandomNumber(99999, 10000))
                .create();
        System.out.println("here is my id:"+message.getSid());

    }

    private String getRandomNumber(int max, int min) {
        int random = (int)(Math.random()*(max-min+1)+min);
        return Integer.toString(random);
    }
}
