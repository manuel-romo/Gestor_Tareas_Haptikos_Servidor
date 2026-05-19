package com.haptikos.gestor_tareas_haptikos_servidor.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class HomeInviteService {

    private final JavaMailSender mailSender;

    @Autowired
    public HomeInviteService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendInviteEmail(String email, String homeName, String inviteCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Te invitaron al hogar \"" + homeName + "\"");

        String html = "<div style='font-family:sans-serif;max-width:480px;margin:auto'>" +
                "<h2 style='color:#FF8A00'>¡Tienes una invitación!</h2>" +
                "<p>Alguien te invitó a unirse al hogar <strong>" + homeName + "</strong>.</p>" +
                "<p>Usa este código en la app:</p>" +
                "<div style='font-size:32px;font-weight:bold;letter-spacing:6px;" +
                "color:#FF8A00;background:#FFF6EB;padding:16px;" +
                "border-radius:12px;text-align:center'>" + inviteCode + "</div>" +
                "<p style='color:#888;font-size:12px;margin-top:24px'>" +
                "Si no esperabas esta invitación puedes ignorar este mensaje.</p>" +
                "</div>";

        helper.setText(html, true);
        mailSender.send(message);
    }
}