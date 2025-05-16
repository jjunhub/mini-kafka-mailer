package demo.producer.facade;

import demo.producer.repository.EmailSendEvent;
import demo.producer.service.EmailCommandService;
import demo.producer.service.EmailDispatchService;
import demo.producer.service.FakeSideEffectService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailFacade {

    private final FakeSideEffectService fakeSideEffectService;
    private final EmailCommandService emailCommandService;
    private final EmailDispatchService emailDispatchService;

    @Transactional
    public void createSendEvent(List<String> emails) {
        List<EmailSendEvent> events = emailCommandService.createEmailSendEvent(emails);
        emailDispatchService.register(events);
        fakeSideEffectService.doSomething();
    }
}
