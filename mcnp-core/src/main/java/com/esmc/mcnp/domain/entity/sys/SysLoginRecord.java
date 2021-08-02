package com.esmc.mcnp.domain.entity.sys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_login_record")
@Getter
@Setter
public class SysLoginRecord implements Serializable {

    private static final long serialVersionUID = 1786930229875190846L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "ip")
    private String ip;

    @Column(name = "location")
    private String location;

    @Column(name = "browser")
    private String browser;

    @Column(name = "os")
    private String os;

    @Column(name = "msg")
    private String msg;
    
    private String status;

}
