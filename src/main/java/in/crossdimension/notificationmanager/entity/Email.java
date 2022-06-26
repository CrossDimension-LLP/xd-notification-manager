package in.crossdimension.notificationmanager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Email {

    private String senderAddress;
    private String senderPassword;
    private String recipientAddress;
    private String recipientName;
    private String mailSubject;
    private String mailBody;
}
