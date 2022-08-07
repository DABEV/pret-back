package mx.edu.utez.pret.model;

import lombok.Data;

@Data
public class MailRequest {

    private String name;
    private String toEmail;
    private String from;
    private String subject;
    
}
