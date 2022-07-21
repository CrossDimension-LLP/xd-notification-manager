package in.crossdimension.notificationmanager.service;

import static in.crossdimension.notificationmanager.config.NotificationConstants.*;
import in.crossdimension.notificationmanager.entity.IncomingSMS;
import in.crossdimension.notificationmanager.entity.SMS;
import in.crossdimension.notificationmanager.repository.IMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class OTPValidation {

    @Autowired
    private IMessageRepository messageRepository;

    public String isOtpValid(IncomingSMS incomingSMS) {
        Optional<SMS> sms = messageRepository.findById(incomingSMS.getFrom());
        log.debug("SMS find by id of incoming message and found sms {}, {} ", incomingSMS, sms);
        if (sms.isPresent() && sms.get().getMessage().equalsIgnoreCase(incomingSMS.getOtp())) {
            if (getTimeStampValidation(sms.get().getTimeStamp(), incomingSMS.getIncomingTimestamp())) {
                return VALID;
            }
            return EXPIRED;
        }
        return INVALID;
    }

    private boolean getTimeStampValidation(String storedTimeStamp, String inComingTimeStamp) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern( DATE_TIME_FORMAT );
        LocalDateTime stored = formatter.parseLocalDateTime( storedTimeStamp );
        LocalDateTime incoming = formatter.parseLocalDateTime( inComingTimeStamp );
        return stored.isAfter(incoming) || stored.isEqual(incoming);
    }
}
