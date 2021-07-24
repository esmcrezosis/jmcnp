package com.esmc.mcnp.web.logging;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class LogConfig {
	@Autowired
	private Environment env;

	@PostConstruct
	public void onStartUp() {
		String url = env.getProperty("spring.datasource.url");
		String userName = env.getProperty("spring.datasource.username");
		String password = env.getProperty("spring.datasource.password");
		String validationQuery = env.getProperty("spring.datasource.validation-query");

		// Create a new connectionSource build from the Spring properties
		JdbcConnectionSource connectionSource = new JdbcConnectionSource(url, userName, password, validationQuery);

		// This is the mapping between the columns in the table and what to
		// insert in it.
		ColumnConfig[] columnConfigs = new ColumnConfig[6];
		columnConfigs[0] = ColumnConfig.createColumnConfig(null, "application", "ACCESS", null, null, "false", null);
		columnConfigs[1] = ColumnConfig.createColumnConfig(null, "log_date", null, null, "true", null, null);
		columnConfigs[2] = ColumnConfig.createColumnConfig(null, "logger", "%logger", null, null, "false", null);
		columnConfigs[3] = ColumnConfig.createColumnConfig(null, "log_level", "%level", null, null, "false", null);
		columnConfigs[4] = ColumnConfig.createColumnConfig(null, "message", "%message", null, null, "false", null);
		columnConfigs[5] = ColumnConfig.createColumnConfig(null, "exception", "%ex{full}", null, null, "false", null);

		// filter for the appender to keep only errors
		ThresholdFilter filter = ThresholdFilter.createFilter(Level.ERROR, null, null);

		// The creation of the new database appender passing:
		// - the name of the appender
		// - ignore exceptions encountered when appending events are logged
		// - the filter created previously
		// - the connectionSource,
		// - log buffer size,
		// - the name of the table
		// - the config of the columns.
		JdbcAppender appender = JdbcAppender.createAppender("DB", "true", filter, connectionSource, "1", "logs",
				columnConfigs);

		// start the appender, and this is it...
		appender.start();
		((Logger) LogManager.getRootLogger()).addAppender(appender);
	}
}
