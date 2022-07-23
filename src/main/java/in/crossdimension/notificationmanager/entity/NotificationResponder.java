package in.crossdimension.notificationmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include. NON_NULL)
public class NotificationResponder {
    private String validityTimestamp;
    private boolean otpValid;
}
