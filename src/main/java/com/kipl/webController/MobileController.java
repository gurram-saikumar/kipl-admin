package com.kipl.webController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kipl.dto.ResponseDTO;
import com.kipl.webService.MobileService;
import com.kipl.webService.WebService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rest")
public class MobileController {

	@Autowired
	private MobileService mobileService;
	
	private static final Logger LOG = LogManager.getLogger(MobileController.class);
	@Autowired
	private Environment appConfig;
	@PostMapping("mobileLogin")
	public ResponseDTO mobileLogin(HttpServletRequest request, @RequestBody String jsonData) {
		ResponseDTO response = new ResponseDTO();
		try {
			response = mobileService.mobileValidateLogin(request, jsonData);
		} catch (Exception e) {
			LOG.info("login Exception==> " + e.getStackTrace());
			response.setResponse(appConfig.getProperty("OOPS_MESSAGE"));
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;
	}
	
	@PostMapping("forgetPasscode")
	public ResponseDTO forgetPasscode(HttpServletRequest request, @RequestBody String jsonData) {
		LOG.info("/jsonData ==>" + jsonData.toString());
		ResponseDTO resp = new ResponseDTO();
		try {
			resp = mobileService.forgetPasscode(request, resp, jsonData);
		} catch (Exception e) {
			LOG.info(e.getStackTrace(),e);
			resp=null;
		}
		return resp;
	}
}
