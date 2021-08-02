package com.esmc.mcnp.infrastructure.security;

import java.util.ArrayList;
import java.util.List;

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

public class ActiveUserStore {

    public List<EuUtilisateur> users;

    public ActiveUserStore() {
        users = new ArrayList<>();
    }

    public List<EuUtilisateur> getUsers() {
        return users;
    }

    public void setUsers(List<EuUtilisateur> users) {
        this.users = users;
    }
}
