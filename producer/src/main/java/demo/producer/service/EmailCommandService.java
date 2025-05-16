package demo.producer.service;

import demo.producer.repository.EmailSendEvent;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EmailCommandService {

    public List<EmailSendEvent> createEmailSendEvent(List<String> emails) {

        return emails.stream()
                .map(EmailSendEvent::new)
                .toList();
    }
}
