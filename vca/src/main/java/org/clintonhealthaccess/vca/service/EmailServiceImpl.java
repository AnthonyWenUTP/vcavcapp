package org.clintonhealthaccess.vca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailServiceImpl")
public class EmailServiceImpl {
	
	@Autowired
    private JavaMailSender mailSender;
	
	
	public void sendEmail(String to, String from, String sub, String mensaje){
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(sub);
        message.setText(mensaje);
        mailSender.send(message);
    }

}
