package com.kipl.webController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kipl.dto.ResponseDTO;
import com.kipl.webService.WebService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/rest/kipl/")
public class WebController {

	@Autowired
	private WebService webService;
	
	private static final Logger LOG = LogManager.getLogger(WebController.class);
	@Autowired
	private Environment appConfig;

//	@Autowired
//	private UserMasterManager userInfoManager;
	/**
	 * This method will be to Validating UserId and Password. Before Login Password
	 * will be Decoded From Base64 Format and compared with the decoded password
	 * from database. In background, UserId and Password will be Verified. If the
	 * User Exists then the User Details Of that Particular User Will be Getting.
	 * 
	 * @param encodedData Where Encoded Data Consists Of userId,(Password will be Containing Base64 Format).
	 * @return ResponseDTO.
	 */
	@PostMapping("login")
	public ResponseDTO login(HttpServletRequest request, @RequestBody String jsonData) {
		ResponseDTO response = new ResponseDTO();
		try {
			response = webService.validateLogin(request, jsonData);
		} catch (Exception e) {
			LOG.info("login Exception==> " + e.getStackTrace());
			response.setResponse(appConfig.getProperty("OOPS_MESSAGE"));
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;
	}

	@PostMapping("register")
	public ResponseDTO addUserInfo(HttpServletRequest request, @RequestBody String jsonData,@RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
		ResponseDTO response = new ResponseDTO();
		try {
			return webService.addUserMaster(jsonData, request,profileImage);
		} catch (Exception e) {
			LOG.info("==> " + e.getStackTrace());
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;
	}
	
	@GetMapping("getUserBasedOnIdAndMobileNumber")
	public ResponseDTO getUserBasedOnIdAndMobileNumber(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		try {
			LOG.info("getUserBasedOnIdAndMobileNumber==========>Controller");
			response =  webService.getUserBasedOnIdAndMobileNumber(request);
		} catch (Exception e) {
			LOG.info("==> " + e.getStackTrace(),e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;
	}

	@PostMapping("getUsers")
	public ResponseDTO getAllUserInfo(HttpServletRequest request, @RequestBody String jsonData) {
		ResponseDTO response = new ResponseDTO();
		try {
			LOG.info("getAllUserInfo==========>Controller");
			response =  webService.getAllUserInfo(request,jsonData);
		} catch (Exception e) {
			LOG.info("==> " + e.getStackTrace(),e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;
	}

	@PostMapping("addMenuController")
	public ResponseDTO addMenuController(HttpServletRequest request, @RequestBody String jsonData)
			throws JSONException {
		LOG.info("addMenuController");
		ResponseDTO response = new ResponseDTO();
		try {
			response = webService.addMenuController(request,response, jsonData);
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
		}
		return response;
	}

	@PostMapping("getMenuControles")
	public ResponseDTO getMenuControles(@RequestBody String jsonData) throws JSONException {
		LOG.info("getMenuControles");
		ResponseDTO response = new ResponseDTO();
		try {
			response = webService.getMenuControles(response, jsonData);
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
		}
		return response;
	}

	@PostMapping("getMenuControlesForPortal")
	public ResponseDTO getMenuControlesForPortal(@RequestBody String jsonData) throws JSONException {
		LOG.info("getMenuControlesForPortal");
		ResponseDTO response = new ResponseDTO();
		try {
			response = webService.getMenuControlesForPortal(response, jsonData);
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
		}
		return response;
	}

	@PostMapping("forgetPassword")
	public ResponseDTO forgetPassword(HttpServletRequest request, @RequestBody String jsonData) {
		LOG.info("/jsonData ==>" + jsonData.toString());
		ResponseDTO resp = new ResponseDTO();
		try {
			resp = webService.forgetPassword(request, resp, jsonData);
		} catch (Exception e) {
			LOG.info(e);
		}
		return resp;
	}

	@PostMapping("saveMaterialRequest")
	public ResponseDTO saveMaterialRequest(HttpServletRequest request, @RequestBody String jsonData)
			throws JSONException {
		LOG.info("saveMaterialRequest");
		ResponseDTO response = new ResponseDTO();
		try {
			response = webService.saveMaterialRequest(request,response, jsonData);
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
		}
		return response;
	}
	
	@GetMapping("getMaterialRequest")
	public ResponseDTO getMaterialRequest(HttpServletRequest request)
			throws JSONException {
		LOG.info("getMaterialRequest");
		ResponseDTO response = new ResponseDTO();
		try {
			response = webService.getMaterialRequest(request,response);
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
		}
		return response;
	}

	
	@PostMapping("getEmployeeDashBoardDetailes")
	public ResponseDTO getDashBoardDetailes(HttpServletRequest request,@RequestBody String jsonData) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			LOG.info("getEmployeeDashBoardDetailes=====jsonData====>"+jsonData);
			if(request.getHeader("deviceType")!=null && !("").equalsIgnoreCase(request.getHeader("deviceType")) && !("web").equalsIgnoreCase(request.getHeader("deviceType")))
			{
				if (webService.getUserBasedOnCustomerIdNMobileNumberNDeviceId(request.getHeader("userId"),request.getHeader("mobileNumber"), request.getHeader("deviceId"),request.getHeader("deviceToken"))) {
					if (jsonData != null) {
						LOG.info("Phase====1");

						responseDTO = webService.getEmployeeDashBoardDetailes(request, jsonData);

					} else {
						responseDTO.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
						responseDTO.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
					}
				} else {
					responseDTO.setStatusCode(Integer.parseInt(appConfig.getProperty("LOGOUT_CODE")));
					responseDTO.setMessage(appConfig.getProperty("LOGGED_IN_OTHER_DEVICE"));
				}
			} else {
				if (webService.getUserBasedOnUserIdNMobileNumber(request.getHeader("userId"),request.getHeader("mobileNumber"))) {
					
					if (jsonData != null) {
						LOG.info("Phase====2");
						responseDTO = webService.getEmployeeDashBoardDetailes(request, jsonData);

					} else {
						responseDTO.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
						responseDTO.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
					}
				} else {
					responseDTO.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
					responseDTO.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
				}
			}
			
		} catch (Exception e) {
			LOG.info("Dashboard Count ==> "+e.getStackTrace(), e);
			responseDTO.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			responseDTO.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return responseDTO;
		
	}
	
	

	@PostMapping("updateEmployee")
	public ResponseDTO updateEmployee(HttpServletRequest request,
			@RequestParam(value = "jsonData", required = false) String jsonData,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

		ResponseDTO resp = new ResponseDTO();
		try {
			LOG.info("==== updateEmployee ===jsonData===>" + jsonData);
			return webService.addUserMaster(jsonData, request, profileImage);
		} catch (Exception e) {
			resp.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			resp.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			resp.setResponse(null);
		}
		return resp;
	}
	
	
	
	
	
	@PostMapping("getExistingEmployeeDetails")
	public ResponseDTO getExistingEmployeeDetails(HttpServletRequest request, @RequestBody String jsonData) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			LOG.info("<==getExistingEmployeeDetails===jsonData====>" + jsonData);
			responseDTO = webService.getExistingEmployeeDetails(request, jsonData);
		} catch (Exception e) {
			LOG.info("getExistingEmployeeDetails====>Exception===>" + e.getStackTrace(), e);
			responseDTO.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			responseDTO.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return responseDTO;
	}
	
	@PostMapping("reEmployeeMapping")
	@Transactional
	public ResponseDTO reMappingEmployee(HttpServletRequest request, @RequestBody String jsonData) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			LOG.info("<==reMappingEmployee===jsonData====>" + jsonData);
			responseDTO = webService.reMappingEmployee(request, jsonData);
		} catch (Exception e) {
			LOG.info("reMappingEmployee====>Exception===>" + e.getStackTrace(), e);
			responseDTO.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			responseDTO.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return responseDTO;
	}
	
	@GetMapping("getMasters")
	public String getMasters(HttpServletRequest request) {
		JSONObject resp = new JSONObject();
		try {
			LOG.info("<==getMasters====>" );
			return webService.getMasters();
		} catch (Exception e) {
			LOG.info("getMasters====>Exception===>" + e.getStackTrace(), e);
			resp.put("statusCode", appConfig.getProperty("ERROR_CODE"));
			resp.put("message", appConfig.getProperty("OOPS_MESSAGE"));
		}
		return resp.toString();
	}
	
	

	

	

	





}