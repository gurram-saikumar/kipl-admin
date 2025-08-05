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
import com.kipl.webService.MobileService;
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
	
	@Autowired private MobileService mobileService;

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


	
	@GetMapping("getInventoryMaster")
	public ResponseDTO getInventoryMaster(HttpServletRequest request)
			throws JSONException {
		LOG.info("getInventoryMaster");
		ResponseDTO response = new ResponseDTO();
		try {
			response = webService.getInventoryMaster(request,response);
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
		}
		return response;
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
	
	
	@GetMapping("getProjectMasters")
	public String getProjectMasters(HttpServletRequest request) {
		JSONObject resp = new JSONObject();
		try {
			LOG.info("<==getProjectMasters====>" );
			return webService.getProjectMasters();
		} catch (Exception e) {
			LOG.info("getProjectMasters====>Exception===>" + e.getStackTrace(), e);
			resp.put("statusCode", appConfig.getProperty("ERROR_CODE"));
			resp.put("message", appConfig.getProperty("OOPS_MESSAGE"));
		}
		return resp.toString();
	}
	
	@GetMapping("getMaterialMasters")
	public String getMaterialMasters(HttpServletRequest request) {
		JSONObject resp = new JSONObject();
		try {
			LOG.info("<==getMaterialMasters====>" );
			return webService.getMaterialMasters();
		} catch (Exception e) {
			LOG.info("getMaterialMasters====>Exception===>" + e.getStackTrace(), e);
			resp.put("statusCode", appConfig.getProperty("ERROR_CODE"));
			resp.put("message", appConfig.getProperty("OOPS_MESSAGE"));
		}
		return resp.toString();
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
	
	
	@PostMapping("issueMaterialRequest")
	public ResponseDTO issueMaterialRequest(HttpServletRequest request, @RequestBody String jsonData) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			LOG.info("<==issueMaterialRequest====>" );
			responseDTO= webService.issueMaterialRequest(jsonData);
		} catch (Exception e) {
			LOG.info("issueMaterialRequest====>Exception===>" + e.getStackTrace(), e);
			responseDTO.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			responseDTO.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return responseDTO;
	}
	
	
	
	@GetMapping("getIssueMaterialRequest")
	public String getIssueMaterialRequest(HttpServletRequest request) {
		JSONObject resp = new JSONObject();
		try {
			LOG.info("<==getIssueMaterialRequest====>" );
			return webService.getIssueMaterialRequest(request);
		} catch (Exception e) {
			LOG.info("getIssueMaterialRequest====>Exception===>" + e.getStackTrace(), e);
			resp.put("statusCode", appConfig.getProperty("ERROR_CODE"));
			resp.put("message", appConfig.getProperty("OOPS_MESSAGE"));
		}
		return resp.toString();
	}
	
	@GetMapping("getDashboardDetailes")
	public String getDashboardDetailes(HttpServletRequest request)
	{
		JSONObject resp = new JSONObject();
		try {
			LOG.info("<==dashboard====>" );
			return webService.getDashboardDetailes(request);
		} catch (Exception e) {
			LOG.info("dashboard====>Exception===>" + e.getStackTrace(), e);
			resp.put("statusCode", appConfig.getProperty("ERROR_CODE"));
			resp.put("message", appConfig.getProperty("OOPS_MESSAGE"));
		}
		return resp.toString();
	}
	
	@PostMapping("saveMaterialMaster")
	public ResponseDTO saveMaterialMaster(HttpServletRequest request, @RequestBody String jsonData) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			LOG.info("<==saveMaterialMaster====>" );
			responseDTO= webService.saveMaterialMaster(jsonData);
		} catch (Exception e) {
			LOG.info("saveMaterialMaster====>Exception===>" + e.getStackTrace(), e);
			responseDTO.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			responseDTO.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return responseDTO;
	}
	
	@PostMapping("addItemInInventory")
	public ResponseDTO addItemInInventory(HttpServletRequest request, @RequestBody String jsonData) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			LOG.info("<==addItemInInventory ====>" );
			responseDTO= webService.addItemInInventory(jsonData);
		} catch (Exception e) {
			LOG.info("saveMaterialMaster====>Exception===>" + e.getStackTrace(), e);
			responseDTO.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			responseDTO.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return responseDTO;
	}
	
	@PostMapping("editInventoryMaster")
	public ResponseDTO getInventoryMaster(HttpServletRequest request, @RequestBody String jsonData)
			throws JSONException {
		LOG.info("editInventoryMaster");
		ResponseDTO response = new ResponseDTO();
		try {
			response = webService.editInventoryMaster(request,jsonData,response);
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
		}
		return response;
	}
	
	@GetMapping("getReports")
	public ResponseDTO getReports(HttpServletRequest request)
			throws JSONException {
		LOG.info("getReports");
		ResponseDTO response = new ResponseDTO();
		try {
			response = webService.getReports(request,response);
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
		}
		return response;
	}
	
	@GetMapping("getBillOfMaterialList")
	public ResponseDTO getBillOfMaterialList(HttpServletRequest request)
			throws JSONException {
		LOG.info("getBillOfMaterialList");
		ResponseDTO response = new ResponseDTO();
		try {
			response = webService.getBillOfMaterialList(request,response);
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
		}
		return response;
	}
	

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
	
	@PostMapping("saveMaterialRequestForMobile")
	public ResponseDTO saveMaterialRequestForMobile(HttpServletRequest request, @RequestBody String jsonData)
			throws JSONException {
		LOG.info("saveMaterialRequestForMobile"+jsonData);
		ResponseDTO response = new ResponseDTO();
		try {
			response = webService.saveMaterialRequestForMobile(request,response, jsonData);
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
		}
		return response;
	}
	

}