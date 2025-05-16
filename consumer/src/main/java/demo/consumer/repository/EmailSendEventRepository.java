package demo.consumer.repository;

import demo.consumer.repository.EmailSendEvent.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSendEventRepository extends JpaRepository<EmailSendEvent, Long> {
    List<EmailSendEvent> findTop10ByStatusOrderByCreatedAtAsc(Status status);
}
