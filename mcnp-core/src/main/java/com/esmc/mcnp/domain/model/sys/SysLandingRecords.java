package com.esmc.mcnp.model.sys;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Classe d'entité sys_landing_records
 */
@Data
@Entity 
@Table(name = "sys_landing_records")
public class SysLandingRecords implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 11)
	private Integer id;

   /**
    * Identifiant d'utilisateur
    */ 
	@Column(name = "user_id", nullable = false, length = 20)
	private Long userId;

   /**
    * Heure de la dernière connexion
    */ 
	@Column(name = "login_date", nullable = false)
	private Timestamp loginDate;

   /**
    * Récemment connecté
    */ 
	@Column(name = "place", nullable = false, length = 10)
	private String place;

   /**
    * IP récemment connecté
    */ 
	@Column(name = "ip", nullable = false, length = 15)
	private String ip;

   /**
    * Méthode de connexion
    */ 
	@Column(name = "login_way", nullable = false, length = 10)
	private String loginWay;

}

