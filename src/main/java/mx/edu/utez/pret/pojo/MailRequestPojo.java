package mx.edu.utez.pret.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailRequestPojo {
    private String name;
    private String toEmail;
    private String from;
    private String subject;
}
