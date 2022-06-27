package in.crossdimension.notificationmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomingSMS {
    private String from;
    private String otp;
    private String incomingTimestamp;
}
