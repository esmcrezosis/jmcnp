package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_access database table.
 *
 */
@Entity
@Table(name = "eu_access")
@NamedQuery(name = "EuAccess.findAll", query = "SELECT e FROM EuAccess e")
public class EuAccess implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long neng;
    private Integer disabled;
    private String masteractivation;
    private String user;
    private String userpassword;

    public EuAccess() {
    }

    @Id
    @Column(unique = true, nullable = false)
    public Long getNeng() {
        return this.neng;
    }

    public void setNeng(Long neng) {
        this.neng = neng;
    }

    public Integer getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    @Column(nullable = false, length = 200)
    public String getMasteractivation() {
        return this.masteractivation;
    }

    public void setMasteractivation(String masteractivation) {
        this.masteractivation = masteractivation;
    }

    @Column(name = "user_", nullable = false, length = 120)
    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Column(nullable = false, length = 200)
    public String getUserpassword() {
        return this.userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

}
