package com.esmc.mcnp.model.sys;

import com.esmc.mcnp.commons.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Classe d'entité sys_log
 */
@Data
@Entity
@Table(name = "sys_log")
public class SysLog extends PageBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 20)
    private Long id;

    /**
     * Identifiant d'utilisateur
     */
    @Column(name = "user_id", length = 20)
    private Long userId;

    /**
     * Nom d'utilisateur
     */
    @Column(name = "username", length = 50)
    private String username;

    /**
     * Action de l'utilisateur
     */
    @Column(name = "operation", length = 50)
    private String operation;

    /**
     *Temps de réponse
     */
    @Column(name = "time", length = 11)
    private Integer time;

    /**
     * Méthode de demande
     */
    @Column(name = "method", length = 200)
    private String method;

    /**
     * Paramètre de demande
     */
    @Column(name = "params")
    private String params;

    /**
     * adresse IP
     */
    @Column(name = "ip", length = 64)
    private String ip;

    /**
     * Mode d'accès 0: PC 1: téléphone portable 2: inconnu
     */
    @Column(name = "device_type", nullable = false, length = 4)
    private Short deviceType;

    /**
     * Type 0: enregistrement du journal général 1: journal des erreurs anormales
     */
    @Column(name = "log_type", nullable = false, length = 4)
    private Short logType;

    /**
     * Détails de l'exception
     */
    @Column(name = "exception_detail")
    private String exceptionDetail;

    /**
     * Temps de creation
     */
    @Column(name = "gmt_create")
    private Timestamp gmtCreate;

}

