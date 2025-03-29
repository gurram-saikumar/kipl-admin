package com.kipl.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBUtils {
	private static final Logger LOG = LogManager.getLogger(DBUtils.class);

	private static String JDBC_DRIVER;
	private static String DB_URL;
	private static String USER;
	private static String PASS;

	private static String SUBEEJKISAN_JDBC_DRIVER;
	private static String SUBEEJKISAN_DATABASE_URL;
	private static String SUBEEJKISAN_DATABASE_USERNAME;
	private static String SUBEEJKISAN_DATABASE_PASSWORD;

	static Connection connection = null;
	static {
		Properties properties = new Properties();
		try {
			properties.load(DBUtils.class.getClassLoader().getResourceAsStream("appconfig.properties"));
			
			JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			DB_URL = "jdbc:sqlserver://172.16.9.132;DatabaseName=NSL_CENTRAL_DB;encrypt=true;trustServerCertificate=true";
			USER = "empover";
			PASS = "Empdb#2025";

			SUBEEJKISAN_JDBC_DRIVER = properties.getProperty("SUBEEJKISAN_JDBC_DRIVER");
			SUBEEJKISAN_DATABASE_URL = properties.getProperty("SUBEEJKISAN_DATABASE_URL");
			SUBEEJKISAN_DATABASE_USERNAME = properties.getProperty("SUBEEJKISAN_DATABASE_USERNAME");
			SUBEEJKISAN_DATABASE_PASSWORD = properties.getProperty("SUBEEJKISAN_DATABASE_PASSWORD");

		} catch (Exception e) {
			LOG.info(e.getCause(), e);
		}
	}

	public static Connection getConnectionForNsl() {
		try {
			LOG.info("Phase====1===DBUtils=============Before=======getConnectionForNsl");
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			LOG.info("Phase====2===DBUtils=============After=======getConnectionForNsl====>Success");
			return connection;
		} catch (Exception e) {
			LOG.info(e.getCause(), e);
			return null;
		}
	}
	
	public static Connection getConnectionOfSubeejKisan() {
		try {
			LOG.info("Phase============DB_URL1========>" + SUBEEJKISAN_DATABASE_URL);
			LOG.info("Phase============JDBC_DRIVER1===>" + SUBEEJKISAN_JDBC_DRIVER);
			LOG.info("Phase============USER1==========>" + SUBEEJKISAN_DATABASE_USERNAME);
			LOG.info("Phase============PASS1==========>" + SUBEEJKISAN_DATABASE_PASSWORD);

			LOG.info("Phase====1===DBUtils=============Before=======getConnectionOfSubeejKisan");
			Class.forName(SUBEEJKISAN_JDBC_DRIVER);
			connection = DriverManager.getConnection(SUBEEJKISAN_DATABASE_URL, SUBEEJKISAN_DATABASE_USERNAME, SUBEEJKISAN_DATABASE_PASSWORD);
			LOG.info("Phase====2===DBUtils=============After=======getConnectionOfSubeejKisan====>Success");
			return connection;
		} catch (Exception e) {
			LOG.info(e.getCause(), e);
			return null;
		}
	}
}