package com.kipl;

import org.springframework.core.env.Environment;

/**
 * @author Balaraju.g
 *
 */
public class AppConfigMessages
{
	private final Environment appConfig;
	public AppConfigMessages(Environment appConfig) {
		super();
		this.appConfig = appConfig;
	}
	public String getRequiredFieldsMsg() {
        return appConfig.getProperty("REQUIRED_FIELDS");
	}
}
