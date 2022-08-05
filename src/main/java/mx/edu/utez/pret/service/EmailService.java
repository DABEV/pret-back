package mx.edu.utez.pret.service;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import mx.edu.utez.pret.model.MailRequest;
import mx.edu.utez.pret.model.MailResponse;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine thyTemplateEngine;

    private final String toFrom = "pretproject2022@gmail.com";

    public MailResponse sendEmail(MailRequest request, Map<String, Object> model){
        MailResponse response = new MailResponse();
        MimeMessage message = mailSender.createMimeMessage();
        Context context = new Context();
        context.setVariables(model);
        String htmlBody = thyTemplateEngine.process("index.html", context);

        try {
            
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(request.getToEmail());
            helper.setSubject(request.getSubject());
            helper.setFrom(toFrom);
            helper.setText(htmlBody, true);
            mailSender.send(message);

            response.setMessage("Email enviado :" + request.getToEmail());
            response.setStatus(Boolean.TRUE);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
