package com.esmc.mcnp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.esmc.mcnp.CoreApplicationConfig;
import com.esmc.mcnp.config.logging.LogConfig;

@SpringBootApplication
@Import({ CoreApplicationConfig.class, DataConfig.class, CacheConfig.class, WebMvcConfig.class, SecurityConfig.class,
		LogConfig.class })
public class McnpSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(McnpSiteApplication.class, args);
	}
}
