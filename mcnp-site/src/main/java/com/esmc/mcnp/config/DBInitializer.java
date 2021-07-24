/**
 * 
 */
package com.esmc.mcnp.config;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.esmc.mcnp.services.security.UserService;
import com.esmc.mcnp.model.security.EuRole;
import com.esmc.mcnp.model.security.EuUser;
import com.esmc.mcnp.model.security.EuUserRole;
import com.esmc.mcnp.services.security.EuRoleService;
import com.esmc.mcnp.services.security.EuRoleUtilisateurService;

/**
 * @author Siva
 * 
 */
@Component
public class DBInitializer {
	private static Logger logger = LoggerFactory.getLogger(DBInitializer.class);

	@Autowired
	private UserService userService;
	@Autowired
	private EuRoleService roleService;
	@Autowired
	private EuRoleUtilisateurService uroleService;

	@Value("${init-db:false}")
	private String initDatabase;

	@PostConstruct
	public void init() {
		try {
			logger.info("############## InitDatabase :" + initDatabase + " ########################");
			if (Boolean.parseBoolean(initDatabase)) {
				initDatabase();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void initDatabase() {

		logger.info("Initializing Database with sample data");

		EuRole role1 = new EuRole("ROLE_USER");
		EuRole role2 = new EuRole("ROLE_ADMIN");
		roleService.add(role1);
		roleService.add(role2);

		EuUser user1 = new EuUser("admin", "admin", true, false, new Date(), false);
		EuUser user2 = new EuUser("siva", "siva", true, false, new Date(), false);
		userService.saveUser(user1, user1.getHashedPassword());
		userService.saveUser(user2, user2.getHashedPassword());

		EuUserRole urole1 = new EuUserRole(role1, user1);
		EuUserRole urole2 = new EuUserRole(role2, user2);
		uroleService.add(urole1);
		uroleService.add(urole2);

	}
}
