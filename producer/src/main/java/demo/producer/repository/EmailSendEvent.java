package demo.producer.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "email_send_event")
public class EmailSendEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime producedAt;

    private LocalDateTime consumedAt;

    public EmailSendEvent(String email) {
        this.email = email;
        this.status = Status.PENDING;
        this.producedAt = LocalDateTime.now();
    }

    public enum Status {
        PENDING, SENT, FAILED, PROCESSING
    }
}
