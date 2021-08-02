package com.esmc.mcnp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({ "com.esmc.mcnp.infrastructure.task" })
public class SpringTaskConfig {

}
