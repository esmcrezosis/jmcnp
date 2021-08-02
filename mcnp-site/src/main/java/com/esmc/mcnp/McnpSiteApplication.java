package com.esmc.mcnp;

import com.esmc.mcnp.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.esmc.mcnp.config.logging.LogConfig;

@SpringBootApplication
@Import({ CoreApplicationConfig.class, DataConfig.class, CacheConfig.class, WebMvcConfig.class, SecSecurityConfig.class,
		LogConfig.class, CaptchaConfig.class, LoginNotificationConfig.class })
public class McnpSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(McnpSiteApplication.class, args);
	}
}
