package com.kiplcommon.components;

import java.net.URLEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.kipl.utils.URLConnect;

@Service
@Scope("prototype")
public class SMSService extends Thread
{
	private static final Logger LOG = LogManager.getLogger(SMSService.class);

    private final Environment appConfig;
    private String mobileNo;
    @SuppressWarnings("unused")
	private String message;
    private String encodedSMS;

 // Constructor injection for the Environment dependency
    @Autowired
    public SMSService(Environment appConfig) {
        this.appConfig = appConfig;
    }

    public void setValues(String mobileNo, String message)
    {
        try
        {
            this.mobileNo = mobileNo;
            this.message = message;
            this.encodedSMS = URLEncoder.encode(message, "UTF-8");
        }
        catch (Exception e)
        {
        	  LOG.error("Error encoding SMS message", e);
        }
    }

    @Override
    public void run() {
        
        String url = String.format("%s%s%s%s%s",
            appConfig.getProperty("SMS_URL"),
            appConfig.getProperty("SMS_MOBILE_NO_PARAM"),
            mobileNo,
            appConfig.getProperty("SMS_MESSAGE_PARAM"),
            encodedSMS
        );
        LOG.info("SMS_URL==> {}", url);
        String response = URLConnect.send(url);
        LOG.info("========response==========>{}", response);
    }


}

