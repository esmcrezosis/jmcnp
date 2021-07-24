package com.esmc.mcnp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.esmc.mcnp.CoreApplicationConfig;
import com.esmc.mcnp.web.fingerprint.BiometricConfig;
import com.esmc.mcnp.web.logging.LogConfig;

@SpringBootApplication
@Import({ CoreApplicationConfig.class, SecurityConfig.class, LogConfig.class })
public class McnpApiApplication {

	@Bean
    public BiometricConfig getConfig() {
        BiometricConfig config = new BiometricConfig();
        config.setMatchingServiceEnabled(true);
        config.setFingerprintScanningEnabled(false);
        config.setMatchingSpeed(BiometricConfig.MatchingSpeed.LOW);
        config.setMatchingThreshold(72);
        config.setTemplateSize(BiometricConfig.TemplateSize.LARGE);
        return config;
    }
	
	@Bean
	public FileStorageProperties getFileProperty() {
		return new FileStorageProperties();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(McnpApiApplication.class, args);
	}

}
