package demo.consumer.external;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
public class GmailClient implements EmailClient {

    private final JavaMailSender mailSender;

    @Override
    public Mono<Void> send(String to, String subject, String body) {
        return Mono.<Void>fromCallable(() -> {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

                    helper.setTo(to);
                    helper.setSubject(subject);
                    helper.setText(body, false);

                    mailSender.send(message);
                    return null;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorMap(e -> new RuntimeException("Failed to send email", e));

    }
}