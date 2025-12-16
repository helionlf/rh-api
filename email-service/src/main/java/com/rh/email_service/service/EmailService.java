package com.app.email_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.app.rh_api.model.Usuario;
import com.app.rh_api.model.Vaga;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(Vaga vaga, Usuario usuario, MultipartFile arquivo) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(vaga.getEmailRecrutador());
            helper.setSubject("Nova Candidatura: " + usuario.getFirstName() + " " + usuario.getLastName());
            helper.setText("Segue o currículo do candidato " + usuario.getFirstName() + " para vaga: " + vaga.getTitle());

            helper.addAttachment(arquivo.getOriginalFilename(), new ByteArrayResource(arquivo.getBytes()));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar o e-mail", e);
        }
    }
}
