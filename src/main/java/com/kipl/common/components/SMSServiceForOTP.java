package com.kipl.common.components;

import java.net.URLEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.kipl.utils.URLConnect;

@Service
@Scope("prototype")

@Component
public class SMSServiceForOTP extends Thread {

	private static final Logger LOG = LogManager.getLogger(SMSServiceForOTP.class);

	private final Environment appConfig;

	private String mobileNo;
	@SuppressWarnings("unused")
	private String message;
	private String encodedSMS;
	private String sid;
	private String mtype;
	private String smsURL;

	// Constructor injection for the Environment dependency
    @Autowired
    public SMSServiceForOTP(Environment appConfig) {
        this.appConfig = appConfig;
    }
	
	public void setValues(String mobileNo, String message, String... sid) {
		try {
			LOG.info("==mobileNo==>{}",mobileNo);
			this.mobileNo = mobileNo;
			this.message = message;
			this.encodedSMS = URLEncoder.encode(message, "UTF-8");
			this.smsURL = appConfig.getProperty("SMS_URL_FOR_OTP");
			if (sid.length > 0) {
				if (sid[0] != null && !sid[0].equals("")) {
					this.sid = sid[0];
				} else {
					this.sid = appConfig.getProperty("SID_VALUE_FOR_ATP");
				}
			} else {
				this.sid = appConfig.getProperty("SID_VALUE_FOR_ATP");
			}
			if (sid.length > 1) {
				if (sid[1] != null && !sid[1].equals("")) {
					this.mtype = sid[1];
				} else {
					this.mtype = appConfig.getProperty("MESSAGE_TYPE_VALUE_DEFAULT");
				}
			} else {
				this.mtype = appConfig.getProperty("MESSAGE_TYPE_VALUE_DEFAULT");
			}
		} catch (Exception e) {
		}
	}
	/*@Override
	public void run() {
		LOG.info("SMS_URL==> " + appConfig.getProperty("SMS_URL_FOR_OTP") + "==mobileNo==>" + mobileNo);
		String response = URLConnect.send(appConfig.getProperty("SMS_URL_FOR_OTP")
				+ appConfig.getProperty("SMS_MOBILE_NO_PARAM") + mobileNo + appConfig.getProperty("SMS_MESSAGE_PARAM")
				+ encodedSMS + appConfig.getProperty("SMS_SID_PARAM") + sid + appConfig.getProperty("SMS_MTYPE_PARAM")
				+ mtype);
		LOG.info("SMS Response For Mobile No. " + mobileNo + " is: " + response);
	}*/
	@Override
	public void run() {
	    LOG.info("SMS_URL==> {}==mobileNo==> {}",smsURL, mobileNo);
	    String url = String.format("%s%s%s%s%s%s%s%s%s",
	            appConfig.getProperty("SMS_URL_FOR_OTP"),
	            appConfig.getProperty("SMS_MOBILE_NO_PARAM"),
	            mobileNo,
	            appConfig.getProperty("SMS_MESSAGE_PARAM"),
	            encodedSMS,
	            appConfig.getProperty("SMS_SID_PARAM"),
	            sid,
	            appConfig.getProperty("SMS_MTYPE_PARAM"),
	            mtype
	    );
	    String response = URLConnect.send(url);
	    LOG.info("SMS Response For Mobile No. {} is: {}", mobileNo, response);
	}


}