package demo.producer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSendEventRepository extends JpaRepository<EmailSendEvent, Long> {
}
