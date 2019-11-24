package service;

import model.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendNotificationApproved(RegistrationRequest req) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(req.getEmail());
        mail.setFrom("klinickicentartest@gmail.com");
        mail.setSubject("Registracija klinicki centar");
        mail.setText("Postovani, Vas zahtev za registraciju naloga za Klinicki centar je prihvacen. Mozete se ulogovati u svoj nalog.");

        javaMailSender.send(mail);
    }


    public void sendNotificationDenied(RegistrationRequest req, String reply) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(req.getEmail());
        mail.setFrom("klinickicentartest@gmail.com");
        mail.setSubject("Registracija klinicki centar");
        mail.setText("Postovani, Vas zahtev za registraciju naloga za Klinicki centar je odbijen. Razlog odbijanja zahteva je sledeci: "+reply);

        javaMailSender.send(mail);
    }

}
