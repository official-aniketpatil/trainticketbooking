package com.epam.trainticketbooking.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.exceptions.DBConnectionFailedException;

public class ConnectionManager {
	private static final String PROPERTY_FILE_URL = "D:\\RD-Training\\train-ticket-booking"
			+ "\\src\\main\\resources\\application.properties";
	private static Properties properties = new Properties();
	private static Logger logger = LogManager.getLogger(ConnectionManager.class);

	private void loadProperties() {
		try (InputStream inStream = new FileInputStream(PROPERTY_FILE_URL);) {
			properties.load(inStream);
		} catch (IOException e) {
			logger.error("unable to load application.properties");
		}
	}

	public Connection getDBConnection() {
		Connection connection = null;
		try {
			loadProperties();
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:" + properties.getProperty("db.dbms") + "://" + properties.getProperty("db.serverName") + ":"
							+ properties.getProperty("db.port") + "/" + properties.getProperty("db.name"),
					properties.getProperty("db.user"), properties.getProperty("db.password"));
			return connection;
		} catch (SQLException | ClassNotFoundException e ) {
			logger.error(e.getMessage());
			throw new DBConnectionFailedException("unable to connect database");
		}

	}
}
