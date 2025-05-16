package demo.consumer.schedular;

import demo.consumer.external.EmailClient;
import demo.consumer.repository.EmailSendEvent;
import demo.consumer.repository.EmailSendEventRepository;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSendScheduler {

    private final EmailSendEventRepository repository;
    private final EmailClient emailClient;

    @Scheduled(fixedRate = 1000) // 1초마다 실행
    public void fetchPendingEmails() {
        // 이벤트 조회
        List<EmailSendEvent> events = repository.findTop10ByStatusOrderByProducedAtAsc(EmailSendEvent.Status.PENDING);
        if (events.isEmpty()) {
            log.info("No pending email events found");
            return;
        }
        log.info("Fetched {} pending email events", events.size());

        // 이벤트 상태를 PROCESSING으로 변경
        events.forEach(EmailSendEvent::markProcessing);
        repository.saveAll(events);

        // 각 이벤트 비동기 처리
        Flux.fromIterable(events)
                .delayElements(Duration.ofMillis(1000)) // 1초마다 1개씩 처리
                .flatMap(this::sendEmail, 1)
                .subscribe();
    }

    private Mono<Void> sendEmail(EmailSendEvent event) {
        return emailClient.send(event.getEmail(), "subject", "body")
                .doOnSuccess(unused -> {
                    event.markSent();
                    repository.save(event);
                    log.info("[O] Sent email to {}", event.getEmail());
                })
                .doOnError(error -> {
                    event.markFailed();
                    repository.save(event);
                    log.warn("[X] Failed to send email to {}", event.getEmail(), error);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
