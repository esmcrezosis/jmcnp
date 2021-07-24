package com.esmc.mcnp.config.logging;

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
		ColumnConfig[] columnConfigs = new ColumnConfig[7];
		columnConfigs[0] = ColumnConfig.newBuilder().setConfiguration(null).setName("id").setPattern("%u").setLiteral(null).setEventTimestamp(false).setUnicode(false).setClob(false).build();
		columnConfigs[1] = ColumnConfig.newBuilder().setConfiguration(null).setName("application").setPattern("ACCESS").setLiteral(null).setEventTimestamp(false).setUnicode(false).setClob(false).build();
		columnConfigs[2] = ColumnConfig.newBuilder().setConfiguration(null).setName("log_date").setPattern(null).setLiteral(null).setEventTimestamp(true).setUnicode(false).setClob(false).build();
		columnConfigs[3] = ColumnConfig.newBuilder().setConfiguration(null).setName("logger").setPattern("%logger").setLiteral(null).setEventTimestamp(false).setUnicode(false).setClob(false).build();
		columnConfigs[4] = ColumnConfig.newBuilder().setConfiguration(null).setName("log_level").setPattern("%level").setLiteral(null).setEventTimestamp(false).setUnicode(false).setClob(false).build();
		columnConfigs[5] = ColumnConfig.newBuilder().setConfiguration(null).setName("message").setPattern("%message").setLiteral(null).setEventTimestamp(false).setUnicode(false).setClob(false).build();
		columnConfigs[6] = ColumnConfig.newBuilder().setConfiguration(null).setName("exception").setPattern("%ex{full}").setLiteral(null).setEventTimestamp(false).setUnicode(false).setClob(false).build();

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
		//JdbcAppender append = JdbcAppender.createAppender("DB", "true", filter, connectionSource, "1", "logs",columnConfigs);
		
		JdbcAppender appender = JdbcAppender.newBuilder().setName("DB").setIgnoreExceptions(true).setFilter(filter).setConnectionSource(connectionSource).setBufferSize(1).setTableName("logs").setColumnConfigs(columnConfigs).build();

		// start the appender, and this is it...
		appender.start();
		((Logger) LogManager.getRootLogger()).addAppender(appender);
	}
}
