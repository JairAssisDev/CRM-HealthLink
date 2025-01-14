package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.email.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender; // Mock do JavaMailSender

    @InjectMocks
    private EmailService emailService; // EmailService com o mock injetado


    @Test
    void testSendEmail() {
        // Arrange
        Email email = new Email("recipient@email.com", "Test Subject", "Test Body");

        // Act
        emailService.send(email);

        // Assert
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class)); // Verifica se o método send foi chamado
    }
}
