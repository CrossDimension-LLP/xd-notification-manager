package in.crossdimension.notificationmanager.service;

import static in.crossdimension.notificationmanager.config.NotificationConstants.*;
import in.crossdimension.notificationmanager.entity.IncomingSMS;
import in.crossdimension.notificationmanager.entity.SMS;
import in.crossdimension.notificationmanager.repository.IMessageRepository;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OTPValidation {

    @Autowired
    private IMessageRepository messageRepository;

    public String isOtpValid(IncomingSMS incomingSMS) {
        Optional<SMS> sms = messageRepository.findById(incomingSMS.getFrom());
        if (sms.isPresent() && sms.get().getMessage().equalsIgnoreCase(incomingSMS.getOtp())) {
            if (getTimeStampValidation(sms.get().getTimeStamp(), incomingSMS.getIncomingTimestamp())) {
                return VALID;
            }
            return EXPIRED;
        }
        return INVALID;
    }

    private boolean getTimeStampValidation(String storedTimeStamp, String inComingTimeStamp) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern( "yyyy-MM-dd HH:mm:ss" );
        LocalDateTime stored = formatter.parseLocalDateTime( storedTimeStamp );
        LocalDateTime incoming = formatter.parseLocalDateTime( inComingTimeStamp );
        return stored.isAfter(incoming) || stored.isEqual(incoming);
    }
}
