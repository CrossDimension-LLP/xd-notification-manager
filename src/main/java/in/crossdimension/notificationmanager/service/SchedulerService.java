package in.crossdimension.notificationmanager.service;

import in.crossdimension.notificationmanager.entity.SMS;
import in.crossdimension.notificationmanager.repository.IMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private IMessageRepository messageRepository;

    @Scheduled(cron = "0 */60 * ? * *")
    public void runHourlyJobForOTP() {
        String currentTime = getTimeStamp();
        List<SMS> smsList = messageRepository.findByTimeStamp(currentTime);
        System.out.println(smsList);
        messageRepository.deleteAll(smsList);
    }

    private String getTimeStamp() {
        LocalDateTime currentTime = LocalDateTime.now();
        currentTime = currentTime.minusMinutes(3);
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(currentTime);
    }
}
