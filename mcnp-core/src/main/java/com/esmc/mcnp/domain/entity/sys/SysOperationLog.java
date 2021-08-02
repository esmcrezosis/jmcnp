package com.kreatech.data.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.kreatech.data.entity.base.BaseEntity;

@Entity
@Table(name = "sys_operation_log")
@Getter
@Setter
@ApiModel(value = "Table du journal de fonctionnement du système")
public class SysOperationLog extends BaseEntity {

    private static final long serialVersionUID = 1356324351602160880L;

    @ApiModelProperty("ID de clé primaire")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty("Titre du module")
    @Column(name = "title")
    private String title;

    @ApiModelProperty("Type de module")
    @Column(name = "business_type")
    private String businessType;

    @ApiModelProperty("Nom de la méthode")
    @Column(name = "method_name")
    private String methodName;

    @ApiModelProperty("Méthode de demande")
    @Column(name = "request_method")
    private String requestMethod;

    @ApiModelProperty("URL de la demande")
    @Column(name = "url")
    private String url;

    @ApiModelProperty("Adresse IP de connexion")
    @Column(name = "ip")
    private String ip;

    @ApiModelProperty("Emplacement de connexion")
    @Column(name = "location")
    private String location;

    @ApiModelProperty("Paramètre de demande")
    @Column(name = "request_params")
    private String requestParams;

    @ApiModelProperty("résultat")
    @Column(name = "result")
    private String result;

    @ApiModelProperty("Message d'erreur")
    @Column(name = "error_msg")
    private String errorMsg;
}
