package demo.consumer.external;

import reactor.core.publisher.Mono;

public interface EmailClient {
    Mono<Void> send(String to, String subject, String body);
}
