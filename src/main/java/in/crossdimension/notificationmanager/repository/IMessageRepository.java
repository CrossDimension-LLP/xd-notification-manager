package in.crossdimension.notificationmanager.repository;

import in.crossdimension.notificationmanager.entity.SMS;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IMessageRepository extends MongoRepository<SMS, String> {

    @Query("{ 'timeStamp' : { $lt: ?0 } }")
    public List<SMS> findByTimeStamp(String timestamp);
}
