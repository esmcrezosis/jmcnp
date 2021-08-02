package com.esmc.mcnp.infrastructure.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final EuUtilisateur user;

    public OnRegistrationCompleteEvent(final EuUtilisateur user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    //

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public EuUtilisateur getUser() {
        return user;
    }

}
