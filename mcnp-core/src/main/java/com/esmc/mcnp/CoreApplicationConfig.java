package com.esmc.mcnp;

import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.Executor;

import com.esmc.mcnp.core.utils.MD5PasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.esmc.mcnp.config.AppProperties;
import com.esmc.mcnp.repositories.base.BaseRepositoryImpl;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * Created with Eclipse User: Mawuli AKLASSOU Date: 21/7/16 Time: 11:20 AM
 */
@Configuration
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = "com.esmc.mcnp")
@EntityScan(basePackages = "com.esmc.mcnp.model")
@EnableJpaRepositories(basePackages = "com.esmc.mcnp.repositories", repositoryBaseClass = BaseRepositoryImpl.class)
@EnableConfigurationProperties(AppProperties.class)
@PropertySource("classpath:application.properties")
public class CoreApplicationConfig implements AsyncConfigurer, SchedulingConfigurer {

	private static final Logger log = LogManager.getLogger();
	private static final Logger schedulingLogger = LogManager.getLogger(log.getName() + ".[scheduling]");
	private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private Environment env;

	@Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateTimeFormat);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }

	@Bean
	public JavaMailSenderImpl javaMailSenderImpl() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setHost(env.getProperty("spring.mail.host"));
		// mailSenderImpl.setPort(env.getProperty("spring.mail.port",
		// Integer.class));
		mailSenderImpl.setProtocol(env.getProperty("spring.mail.protocol"));
		mailSenderImpl.setUsername(env.getProperty("spring.mail.username"));
		mailSenderImpl.setPassword(env.getProperty("spring.mail.password"));

		Properties javaMailProps = new Properties();
		javaMailProps.put("mail.smtp.auth", true);
		javaMailProps.put("mail.smtp.starttls.enable", true);

		mailSenderImpl.setJavaMailProperties(javaMailProps);

		return mailSenderImpl;
	}

	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {
		log.info("Setting up thread pool task scheduler with 20 threads.");
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(20);
		scheduler.setThreadNamePrefix("task-mcnp");
		scheduler.setAwaitTerminationSeconds(60);
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		scheduler.setErrorHandler(t -> schedulingLogger.error("Unknown error occurred while executing task.", t));
		scheduler.setRejectedExecutionHandler(
				(r, e) -> schedulingLogger.error("Execution of task {} was rejected for unknown reasons.", r));
		return scheduler;
	}

	@Override
	public Executor getAsyncExecutor() {
		Executor executor = this.taskScheduler();
		log.info("Configuring asynchronous method executor {}.", executor);
		return executor;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar registrar) {
		TaskScheduler scheduler = this.taskScheduler();
		log.info("Configuring scheduled method executor {}.", scheduler);
		registrar.setTaskScheduler(scheduler);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> log.error("Uncaught async error", ex);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new MD5PasswordEncoder();
	}

}
