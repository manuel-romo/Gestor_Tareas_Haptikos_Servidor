package com.haptikos.gestor_tareas_haptikos_servidor.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private final JavaMailSender mailSender;

    @Autowired
    public PasswordResetService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetEmail(String email, String resetCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Recupera tu contraseña - Haptikos");

        String html = "<div style='font-family:sans-serif;max-width:480px;margin:auto'>" +
                "<h2 style='color:#FF8A00'>Recupera tu contraseña</h2>" +
                "<p>Recibimos una solicitud para restablecer la contraseña de tu cuenta.</p>" +
                "<p>Usa este código en la app:</p>" +
                "<div style='font-size:32px;font-weight:bold;letter-spacing:6px;" +
                "color:#FF8A00;background:#FFF6EB;padding:16px;" +
                "border-radius:12px;text-align:center'>" + resetCode + "</div>" +
                "<p>Este código expira en <strong>15 minutos</strong>.</p>" +
                "<p style='color:#888;font-size:12px;margin-top:24px'>" +
                "Si no solicitaste esto puedes ignorar este mensaje.</p>" +
                "</div>";

        helper.setText(html, true);
        mailSender.send(message);
    }
}