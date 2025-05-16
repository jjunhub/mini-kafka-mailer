package demo.producer.service;

import demo.producer.repository.EmailSendEvent;
import demo.producer.repository.EmailSendEventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailDispatchService {

    private final EmailSendEventRepository emailSendEventRepository;

    public void register(List<EmailSendEvent> events) {
        emailSendEventRepository.saveAll(events);
    }
}
