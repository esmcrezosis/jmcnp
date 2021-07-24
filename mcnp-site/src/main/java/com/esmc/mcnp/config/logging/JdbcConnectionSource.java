package com.esmc.mcnp.config.logging;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class JdbcConnectionSource implements ConnectionSource {

	private DataSource dataSource;

	public JdbcConnectionSource(String url, String userName, String password, String validationQuery) {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(url);
		config.setUsername(userName);
		config.setPassword(password);
		config.setConnectionTestQuery(validationQuery);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		this.dataSource = new HikariDataSource(config);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public State getState() {
		return null;
	}

	@Override
	public void initialize() {

	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

	@Override
	public boolean isStarted() {
		return false;
	}

	@Override
	public boolean isStopped() {
		return false;
	}
}