package com.kreatech.data.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kreatech.common.constant.ValidEnum;
import com.kreatech.data.entity.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sys_notice")
@Getter
@Setter
@ApiModel(value = "Formulaire de notification système")
public class SysNotice extends BaseEntity {

    private static final long serialVersionUID = -4069819609472529502L;

    @ApiModelProperty("ID de clé primaire")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty("Identifiant du type de dictionnaire")
    @Column(name = "type_id")
    private Long typeId;

    @ApiModelProperty("Titre")
    @Column(name = "title")
    private String title;

    @ApiModelProperty("contenu")
    @Column(name = "content")
    private String content;

    @ApiModelProperty("Remarques")
    @Column(name = "remark")
    private String remark;

    @ApiModelProperty("Indicateur de suppression logicielle，Y/N")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "valid")
    private ValidEnum valid;

}
