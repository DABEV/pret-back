package mx.edu.utez.pret.service;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import mx.edu.utez.pret.pojo.MailRequestPojo;
import mx.edu.utez.pret.pojo.MailResponsePojo;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine thyTemplateEngine;

    @Value("${app.email}")
    private String email;

    public MailResponsePojo sendEmail(MailRequestPojo request, Map<String, Object> model){
        MailResponsePojo response = new MailResponsePojo();
        MimeMessage message = mailSender.createMimeMessage();
        Context context = new Context();
        context.setVariables(model);
        String htmlBody = thyTemplateEngine.process("send-email.html", context);

        try {
            
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(request.getToEmail());
            helper.setSubject(request.getSubject());
            helper.setFrom(email);
            helper.setText(htmlBody, true);
            mailSender.send(message);

            response.setMessage("Email enviado: " + request.getToEmail());
            response.setStatus(Boolean.TRUE);

        } catch (Exception e) {
            e.printStackTrace();
            response = null;
        }
        return response;
    }

}
