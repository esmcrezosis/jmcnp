package com.kreatech.data.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kreatech.data.entity.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sys_login_record")
@Getter
@Setter
@ApiModel(value = "Tableau des enregistrements de connexion système")
public class SysLoginRecord extends BaseEntity {

    private static final long serialVersionUID = 1786930229875190846L;

    @ApiModelProperty("ID de clé primaire")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ApiModelProperty("nom utilisateur de connexion")
    @Column(name = "login_name")
    private String loginName;

    @ApiModelProperty("Adresse IP de connexion")
    @Column(name = "ip")
    private String ip;

    @ApiModelProperty("Emplacement de connexion")
    @Column(name = "location")
    private String location;

    @ApiModelProperty("Navigateur")
    @Column(name = "browser")
    private String browser;

    @ApiModelProperty("Système d'Exploitation")
    @Column(name = "os")
    private String os;
    
    @ApiModelProperty("Message de connexion")
    @Column(name = "msg")
    private String msg;
    
    private String status;

}
