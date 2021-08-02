package com.esmc.mcnp.infrastructure.registration.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.registration.OnRegistrationCompleteEvent;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreMoraleService;
import com.esmc.mcnp.infrastructure.services.cm.EuMembreService;
import com.esmc.mcnp.infrastructure.services.security.IUserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private IUserService service;
    @Autowired
    private EuMembreService membreService;
    @Autowired
    private EuMembreMoraleService moraleService;
    @Autowired
    private MessageSource messages;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Environment env;

    // API

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final EuUtilisateur user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    //

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final EuUtilisateur user, final String token) {
    	String email = StringUtils.EMPTY;
    	if(StringUtils.isNotBlank(user.getCodeMembre())) {
    		if(user.getCodeMembre().endsWith("P")) {
    			EuMembre membre = membreService.findById(user.getCodeMembre());
    			email = membre.getEmailMembre();
    		} else {
    			EuMembreMorale morale = moraleService.findById(user.getCodeMembre());
    			email = morale.getEmailMembre();
    		}
    	}
    	final String recipientAddress = email;
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        final String message = messages.getMessage("message.regSuccLink", null, "You registered successfully. To confirm your registration, please click on the below link.", event.getLocale());
        final SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(recipientAddress);
        mail.setSubject(subject);
        mail.setText(mail + " \r\n" + confirmationUrl);
        mail.setFrom(env.getProperty("support.email"));
        return mail;
    }
    

}
