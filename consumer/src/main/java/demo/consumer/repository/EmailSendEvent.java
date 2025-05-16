package demo.consumer.repository;

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

    private LocalDateTime createdAt;

    public EmailSendEvent(String email) {
        this.email = email;
        this.status = Status.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void markProcessing() {
        this.status = Status.PROCESSING;
    }

    public void markSent() {
        this.status = Status.SENT;
    }

    public void markFailed() {
        this.status = Status.FAILED;
    }

    public enum Status {
        PENDING, SENT, FAILED, PROCESSING
    }
}
