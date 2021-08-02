package com.esmc.mcnp.domain.entity.sys;

import com.esmc.mcnp.domain.enums.ValidEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_notice")
@Getter
@Setter
public class SysNotice implements Serializable {

    private static final long serialVersionUID = -4069819609472529502L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "remark")
    private String remark;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "valid")
    private ValidEnum valid;

}
