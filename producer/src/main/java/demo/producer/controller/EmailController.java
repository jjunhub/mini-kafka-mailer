package demo.producer.controller;

import demo.producer.facade.EmailFacade;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mails")
public class EmailController {

    private final EmailFacade emailFacade;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(
            @RequestBody List<String> emails
    ) {
        emailFacade.createSendEvent(emails);
        return ResponseEntity.ok("Email sent successfully");
    }
}
