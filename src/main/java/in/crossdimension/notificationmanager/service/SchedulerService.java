package in.crossdimension.notificationmanager.service;

import in.crossdimension.notificationmanager.entity.SMS;
import in.crossdimension.notificationmanager.repository.IMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static in.crossdimension.notificationmanager.config.NotificationConstants.DATE_TIME_FORMAT;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private IMessageRepository messageRepository;

    @Scheduled(cron = "0 */60 * ? * *")
    public void runHourlyJobForOTP() {
        String currentTime = getTimeStamp();
        List<SMS> smsList = messageRepository.findByTimeStamp(currentTime);
        log.info("Deleted SMS list in this round {}", smsList);
        messageRepository.deleteAll(smsList);
    }

    private String getTimeStamp() {
        LocalDateTime currentTime = LocalDateTime.now();
        currentTime = currentTime.minusMinutes(2);
        return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(currentTime);
    }
}
