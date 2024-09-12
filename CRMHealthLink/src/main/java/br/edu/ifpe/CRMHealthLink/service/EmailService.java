package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.service.dto.email.Email;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender sender;

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void send(Email email){
        var message = new SimpleMailMessage();
        message.setFrom("noreplay@email.com");
        message.setTo(email.to());
        message.setSubject(email.subject());
        message.setText(email.body());
        sender.send(message);
    }
}
