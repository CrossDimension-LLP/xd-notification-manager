package in.crossdimension.notificationmanager.repository;

import in.crossdimension.notificationmanager.entity.SMS;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMessageRepository extends MongoRepository<SMS, String> {
}
