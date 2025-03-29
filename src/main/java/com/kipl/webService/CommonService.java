package com.kipl.webService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
	private static final Logger LOG = LogManager.getLogger(CommonService.class);
	public Long checkJsonObjectAndGetId(JSONObject jsonRequest,String jsonObjKey)
	{
		 try {
					if (jsonRequest.has(jsonObjKey)) {
					    Object idObject = jsonRequest.get(jsonObjKey);
					    if (idObject instanceof Number number) {
					    	return number.longValue();
					    }else if (idObject instanceof Boolean booleanValue && !booleanValue) {
					    	return 0L; // Set to a default value or handle appropriately
					    }else if (idObject instanceof String stringValue) {
			                if(stringValue.isEmpty())
			                	return 0L;
			                else
			                	return Long.parseLong(stringValue);
			            } 
					    else {
					        throw new JSONException(jsonObjKey+" is not a valid number: " + idObject);
					    }
					} else {
					    throw new JSONException(jsonObjKey+" is missing");
					}
		 	}
		 catch (Exception e) {
		        LOG.info("Error occurred while fetching : {}", e.getMessage());
		    }
		
		return 0l;
	}

}