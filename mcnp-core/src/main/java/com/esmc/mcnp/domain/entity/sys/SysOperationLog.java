package com.esmc.mcnp.domain.entity.sys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "sys_operation_log")
@Getter
@Setter
public class SysOperationLog implements Serializable {

    private static final long serialVersionUID = 1356324351602160880L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "request_method")
    private String requestMethod;

    @Column(name = "url")
    private String url;

    @Column(name = "ip")
    private String ip;

    @Column(name = "location")
    private String location;

    @Column(name = "request_params")
    private String requestParams;

    @Column(name = "result")
    private String result;

    @Column(name = "error_msg")
    private String errorMsg;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
