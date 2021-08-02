package com.esmc.mcnp.infrastructure.security;

import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.stereotype.Component;

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

@Component
public class LoggedUser implements HttpSessionBindingListener {

    private EuUtilisateur user;
    private ActiveUserStore activeUserStore;

    public LoggedUser(EuUtilisateur user, ActiveUserStore activeUserStore) {
        this.user = user;
        this.activeUserStore = activeUserStore;
    }

    public LoggedUser() {
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        List<EuUtilisateur> users = activeUserStore.getUsers();
        LoggedUser loggedUser = (LoggedUser) event.getValue();
        if (!users.contains(loggedUser.getUser())) {
            users.add(loggedUser.getUser());
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        List<EuUtilisateur> users = activeUserStore.getUsers();
        LoggedUser loggedUser = (LoggedUser) event.getValue();
        users.remove(loggedUser.getUser());
    }

    public EuUtilisateur getUser() {
        return user;
    }

    public void setUser(EuUtilisateur user) {
        this.user = user;
    }
}
