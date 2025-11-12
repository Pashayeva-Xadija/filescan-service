package az.devlab.filescanservice.serviceimpl;

import az.devlab.filescanservice.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "mail.enabled", havingValue = "true")
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final JavaMailSender mailSender;

    @Value("${mail.from:no-reply@filescan.local}")
    private String from;

    @Value("${mail.to:admin@filescan.local}")
    private String to;

    @Override
    public void notifyInfection(String traceId, String filename, String reason, String quarantinedPath) {
        String subject = "[FileScan] INFECTED detected";
        String body = """
                Virus detected.

                Trace ID   : %s
                Filename   : %s
                Reason     : %s
                Quarantined: %s
                """.formatted(traceId, filename, reason, quarantinedPath == null ? "-" : quarantinedPath);
        send(subject, body);
    }

    @Override
    public void notifyFailure(String traceId, String filename, String errorMessage) {
        String subject = "[FileScan] SCAN FAILED";
        String body = """
                Scan failed.

                Trace ID : %s
                Filename : %s
                Error    : %s
                """.formatted(traceId, filename, errorMessage);
        send(subject, body);
    }

    private void send(String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(splitRecipients(to));
        msg.setSubject(subject);
        msg.setText(text);
        mailSender.send(msg);
    }

    private String[] splitRecipients(String recipients) {
        return recipients == null ? new String[0]
                : recipients.replace(";", ",").split("\\s*,\\s*");
    }
}
