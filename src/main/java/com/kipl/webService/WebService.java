package com.kipl.webService;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.query.NativeQuery;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kipl.common.FileUtils;
import com.kipl.common.HibernateDao;
import com.kipl.dto.BOMDto;
import com.kipl.dto.DropDownDTO;
import com.kipl.dto.MaterialDto;
import com.kipl.dto.MaterialRequestDto;
import com.kipl.dto.MaterialRequestListDto;
import com.kipl.dto.ResponseDTO;
import com.kipl.dto.SubMenuControllerDTO;
import com.kipl.dto.UserInfoDto;
import com.kipl.manager.BillOfMaterialMasterManager;
import com.kipl.manager.ColourMasterManager;
import com.kipl.manager.CompanyProjectMasterManager;
import com.kipl.manager.GradeMasterManager;
import com.kipl.manager.InventoryMasterManager;
import com.kipl.manager.InventoryHistoryManager;
import com.kipl.manager.IssueMaterialRequestMasterManager;
import com.kipl.manager.MaterialMasterManager;
import com.kipl.manager.MaterialRequestHistoryManager;
import com.kipl.manager.MaterialRequestManager;
import com.kipl.manager.MaterialStatusMasterManager;
import com.kipl.manager.MenuMasterManager;
import com.kipl.manager.RMMasterManager;
import com.kipl.manager.ReMappingHistoryManager;
import com.kipl.manager.RoleMasterManager;
import com.kipl.manager.SegmentMasterManager;
import com.kipl.manager.SqlQueryManager;
import com.kipl.manager.SubMenuMasterManager;
import com.kipl.manager.UserMasterManager;
import com.kipl.manager.UserMenuMappingManager;
import com.kipl.models.BillOfMaterialMaster;
import com.kipl.models.ColourMaster;
import com.kipl.models.CompanyProjectMaster;
import com.kipl.models.GradeMaster;
import com.kipl.models.InventoryHistory;
import com.kipl.models.InventoryMaster;
import com.kipl.models.IssueMaterialRequestMaster;
import com.kipl.models.MaterialMaster;
import com.kipl.models.MaterialRequestHistory;
import com.kipl.models.MaterialRequestMaster;
import com.kipl.models.MaterialStatusMaster;
import com.kipl.models.MenuMaster;
import com.kipl.models.RMMaster;
import com.kipl.models.ReMappingHistory;
import com.kipl.models.RoleMaster;
import com.kipl.models.SegmentMaster;
import com.kipl.models.SubMenuMaster;
import com.kipl.models.UserMaster;
import com.kipl.models.UserMenuMappingEntity;
import com.kipl.utils.DateUtils;
import com.kipl.utils.ExcelUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class WebService {

	@Autowired
	private Environment appConfig;
	@Autowired
	private UserMasterManager userInfoManager;

	@Autowired
	@Qualifier("hibernateDao")
	protected HibernateDao hibernateDao;

	@Autowired
	private SqlQueryManager sqlQueryManager;

	@Autowired
	private RoleMasterManager roleMasterManager;

	@Autowired
	private UserMenuMappingManager userMenuMappingManager;

	@Autowired
	private MenuMasterManager menuMasterManager;

	@Autowired
	private SubMenuMasterManager subMenuMasterManager;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired 
	private ReMappingHistoryManager reMappingHistoryManager;
	
	@Autowired private MaterialMasterManager materialMasterManager;
	@Autowired private CompanyProjectMasterManager companyProjectMasterManager;
	@Autowired private UserMasterManager userMasterManager;
	@Autowired private MaterialRequestManager materialRequestManager;
	@Autowired private MaterialRequestHistoryManager materialRequestHistoryManager;
	@Autowired private InventoryMasterManager inventoryMasterManager;
	@Autowired private IssueMaterialRequestMasterManager issueMaterialRequestMasterManager;
	@Autowired private InventoryHistoryManager inventoryHistoryManager;
	@Autowired private BillOfMaterialMasterManager billOfMaterialMasterManager;
	
	@Autowired private RMMasterManager rmMasterManager;
	@Autowired private GradeMasterManager gradeMasterManager;
	@Autowired private SegmentMasterManager segmentMasterManager;
	@Autowired private ColourMasterManager colourMasterManager;
	@Autowired private MaterialStatusMasterManager materialStatusMasterManager;



	private static final Logger LOG = LogManager.getLogger(WebService.class);

	private Random random = new Random();

	private final String roleIdParam = "roleId";

	public ResponseDTO getAllUserInfo(HttpServletRequest request, String jsonData) {
		ResponseDTO response = new ResponseDTO();
		try {
			LOG.info("getAllUserInfo==========>Service========JSONData==>" + jsonData);

			JSONObject jsonRequest = new JSONObject(jsonData);
			int page = jsonRequest.has("page") ? jsonRequest.getInt("page") : 1;
			int itemsPerPage = jsonRequest.has("itemsPerPage") ? jsonRequest.getInt("itemsPerPage") : 10;
			String reqType = jsonRequest.has("reqType") ? jsonRequest.getString("reqType") : "";
			String fileterString = jsonRequest.has("filterValue") ? jsonRequest.getString("filterValue") : "";
			String excelFilePath = "";
			Long userCount = sqlQueryManager.fetchAllUsersMastersDataCount(fileterString);
			List<UserMaster> userList = userInfoManager.getAllUsers(page, reqType, itemsPerPage, fileterString);
			LOG.info("userList===============================================" + userList.size());
			if (userList != null && userList.size() > 0) {
				if ("GRID".equalsIgnoreCase(reqType)) {
					JSONObject json = new JSONObject();
					json.put("userList", userList);
					json.put("userCount", userCount != null ? userCount : 0l);
					response.setResponse(JSONValue.parse(json.toString()));
					response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
					response.setMessage(appConfig.getProperty("SUCCESS"));
				} else {
					LOG.info("==EXCEL==");
					excelFilePath = prepareExcelReportForEmployeeData();
					LOG.info("excelFilePath===>");
					response.setMessage(appConfig.getProperty("SUCCESS"));
					response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
					response.setResponse(excelFilePath);
					return response;
				}

			} else {
				LOG.info("==ELSE_=");
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
				response.setMessage("No data available");
				response.setResponse(null);
				return response;
			}

		} catch (Exception e) {
			LOG.info("=========> " + e, e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;
	}

	@SuppressWarnings("unused")
	public ResponseDTO addUserMaster(String jsonData, HttpServletRequest request, MultipartFile profileImage) {
		ResponseDTO resp = new ResponseDTO();
		try {
			UserMaster userInfo = null;

			request.getHeader("deviceType");
			String mobileNumber = request.getHeader("mobileNumber");
			String userId = request.getHeader("userId");
			String profileImagePath = null;
			String profileImageName = null;
			LOG.info("jsondata=====>" + jsonData);
			UserInfoDto jsonRequest = new ObjectMapper().readValue(jsonData, UserInfoDto.class);

			boolean mobileNumberRegistered = false;
			boolean emailIdRegistered = false;
			boolean updatemobileNumberRegistered = false;
			boolean updateemailIdRegistered = false;

			if (jsonRequest.getName().isEmpty() || jsonRequest.getEmailId().isEmpty()
					|| jsonRequest.getMobileNumber().isEmpty() || jsonRequest.getRoleId() == 0) {
				resp.setResponse(appConfig.getProperty("REQUIRED_FIELDS"));
				resp.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
				resp.setMessage(appConfig.getProperty("REQUIRED_FIELDS"));
				return resp;
			}

			if (profileImage != null) {
				if (profileImage.getOriginalFilename() != null
						&& !profileImage.getOriginalFilename().trim().equals("")) {
					if (profileImage.getSize() > 0) {
						String tempFolderPath = appConfig.getProperty("TEMP_PROFILE_UPLOAD_FOLDER_PATH");
						String fileName = "";
						if (FileUtils.createDirs(tempFolderPath)) {
							LOG.info("=====>profileImageName===>" + profileImage.getOriginalFilename());
							fileName = jsonRequest.getMobileNumber() + "_" + profileImage.getOriginalFilename();
							profileImageName = "/Images/Mobile/profileImageUpload/" + fileName;
							profileImagePath = tempFolderPath + fileName;
							File file = new File(profileImagePath);
							FileOutputStream fos = new FileOutputStream(file);
							fos.write(profileImage.getBytes());
							fos.close();
							LOG.info("=====>profileImagePath===>" + profileImagePath);
						}
					}
				}
			}

			if (jsonRequest.getId() != null) {
				userInfo = userInfoManager.get(jsonRequest.getId());
			}
//			String randomPassword = generateRandomPassword(8);
			String randomPassword = appConfig.getProperty("USER_DEFAULT_PASSCODE");

			if (userInfo == null) {
				UserMaster existingUserInfo = userInfoManager.getPasswordByUserId(jsonRequest.getMobileNumber());

				if (existingUserInfo != null) {
					resp.setResponse("Mobile Number Is Already Registered.");
					resp.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
					resp.setMessage("Mobile Number Is Already Registered.");

					mobileNumberRegistered = true;
				}
				List<UserMaster> existingUsersByEmail = userInfoManager.getUserByEmailId(jsonRequest.getEmailId());
				if (existingUsersByEmail != null && !existingUsersByEmail.isEmpty()) {
					for (UserMaster existingUserInfoByEmail : existingUsersByEmail) {
						if (!existingUserInfoByEmail.getId().equals(jsonRequest.getId())) {
							resp.setResponse("EmailId Is Already Registered.");
							resp.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
							resp.setMessage("EmailId Is Already Registered.");
							emailIdRegistered = true;
							return resp;
						}
					}
				}
				if (!mobileNumberRegistered && !emailIdRegistered) {
					userInfo = new UserMaster();
					String empId = generateEmpId(jsonRequest.getName());
					userInfo.setLoginId(empId);
					userInfo.setEmployeeId(jsonRequest.getEmployeeId());
					userInfo.setName(jsonRequest.getName());
					userInfo.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
					userInfo.setEmailId(jsonRequest.getEmailId());
					userInfo.setGender(jsonRequest.getGender());
					userInfo.setLocation(jsonRequest.getLocation());
					userInfo.setMobileNumber(jsonRequest.getMobileNumber());
					userInfo.setStatus(jsonRequest.isStatus());
					userInfo.setDateOfBirth(jsonRequest.getDateOfBirth());
					userInfo.setVillage(jsonRequest.getVillage());
					userInfo.setBlock(jsonRequest.getBlock());
					userInfo.setAddress(jsonRequest.getAddress());
					userInfo.setAlternativeMobileNumber(jsonRequest.getAlternativeMobileNumber());
					userInfo.setCity(jsonRequest.getCity());
					userInfo.setLandMark(jsonRequest.getLandMark());
					userInfo.setPinCode(jsonRequest.getPinCode());
					userInfo.setProfilePicUrl(profileImageName);

					RoleMaster role = roleMasterManager.get(jsonRequest.getRoleId());

					if (role != null) {
						userInfo.setRole(role);
						userInfo.setRoleName(role.getName());
					}

//						if (jsonRequest.getStateId() != null || jsonRequest.getStateId() != 0 ) {
//							state = stateManager.get(jsonRequest.getStateId());
//							if (state != null) {
//								userInfo.setState(state);
//								userInfo.setStateName(state.getName());
//							}
//						}
//
//						if (jsonRequest.getDistrictId() != null || jsonRequest.getDistrictId() != 0|| jsonRequest.getDistrictId().equals("")) {
//							district = districtManager.get(jsonRequest.getDistrictId());
//							if (district != null) {
//								userInfo.setDistrictId(district);
//								userInfo.setDistrictName(district.getName());
//							}
//						}
//
//						if (district != null) {
//							country = countryManager.get(district.getState().getZone().getCountry().getId());
//							if (country != null) {
//								userInfo.setCountryId(country);
//								userInfo.setCountryName(country.getName());
//							}
//
//						}

					userInfoManager.mergeSaveOrUpdate(userInfo);

					JSONObject json = new JSONObject();
					json.put("name", userInfo.getName());
					json.put("mobileNumber", userInfo.getMobileNumber());
					json.put("id", userInfo.getId());
					resp.setResponse(JSONValue.parse(json.toString()));
					resp.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
					resp.setMessage("Employee Added Succesfully");
					// logUserEvent("portal", "Employee", mobileNumber,resp.getMessage(),userId,
					// jsonData, resp);

					if (userInfo.getRole().getName().equalsIgnoreCase("Super Admin")) {

						String password = appConfig.getProperty("USER_DEFAULT_PASSCODE");
						String base64EncodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

						userInfo.setPassword(base64EncodedPassword);
						userInfoManager.mergeSaveOrUpdate(userInfo);

					} else {

						String base64EncodedPassword = Base64.getEncoder().encodeToString(randomPassword.getBytes());
						userInfo.setPassword(base64EncodedPassword);
						userInfoManager.mergeSaveOrUpdate(userInfo);

					}

				}
			} else {
				UserMaster existingUserInfo = userInfoManager.getUserBymblNo(jsonRequest.getMobileNumber(),
						jsonRequest.getId());
				UserMaster existingUserwithEmailId = userInfoManager.getUserByemailId(jsonRequest.getEmailId(),
						jsonRequest.getId());

				if (existingUserInfo != null) {
					updatemobileNumberRegistered = true;
				}

				if (existingUserwithEmailId != null) {
					updateemailIdRegistered = true;

				}
				if (!updatemobileNumberRegistered) {

					if (!updateemailIdRegistered) {

						userInfo.setEmployeeId(jsonRequest.getEmployeeId());
						userInfo.setName(jsonRequest.getName());
						userInfo.setModifiedOn(DateUtils.getCurrentSystemTimestamp());
						userInfo.setEmailId(jsonRequest.getEmailId());
						userInfo.setGender(jsonRequest.getGender());
						userInfo.setLocation(jsonRequest.getLocation());
						userInfo.setMobileNumber(jsonRequest.getMobileNumber());
						userInfo.setProfilePicUrl(profileImageName);

						RoleMaster role = roleMasterManager.get(jsonRequest.getRoleId());

						if (role != null) {
							userInfo.setRole(role);
							userInfo.setRoleName(role.getName());
						}

						userInfo.setStatus(jsonRequest.isStatus());
						userInfo.setDateOfBirth(jsonRequest.getDateOfBirth());
						userInfo.setVillage(jsonRequest.getVillage());
						userInfo.setBlock(jsonRequest.getBlock());
						userInfo.setAddress(jsonRequest.getAddress());
						userInfo.setAlternativeMobileNumber(jsonRequest.getAlternativeMobileNumber());
						userInfo.setCity(jsonRequest.getCity());
						userInfo.setLandMark(jsonRequest.getLandMark());
						userInfo.setPinCode(jsonRequest.getPinCode());

//							if (jsonRequest.getStateId() != null || jsonRequest.getStateId() != 0|| jsonRequest.getStateId().equals("")) {
//								state = stateManager.get(jsonRequest.getStateId());
//								if (state != null) {
//									userInfo.setState(state);
//									userInfo.setStateName(state.getName());
//								}
//							}
//
//							if (jsonRequest.getDistrictId() != null || jsonRequest.getDistrictId() != 0
//									|| jsonRequest.getDistrictId().equals("")) {
//								district = districtManager.get(jsonRequest.getDistrictId());
//								if (district != null) {
//									userInfo.setDistrictId(district);
//									userInfo.setDistrictName(district.getName());
//								}
//							}
//
//							if (district != null) {
//								country = countryManager.get(district.getState().getZone().getCountry().getId());
//								if (country != null) {
//									userInfo.setCountryId(country);
//									userInfo.setCountryName(country.getName());
//								}
//
//							}

						userInfoManager.mergeSaveOrUpdate(userInfo);

						JSONObject json = new JSONObject();
						json.put("name", userInfo.getName());
						json.put("mobileNumber", userInfo.getMobileNumber());
						json.put("id", userInfo.getId());
						resp.setResponse(JSONValue.parse(json.toString()));
						resp.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
						resp.setMessage("Employee Updated Succesfully");
						// logUserEvent("portal", "Employee", mobileNumber,resp.getMessage(),userId,
						// jsonData, resp);
					} else {
						resp.setResponse("EmailId Is Already Registered.");
						resp.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
						resp.setMessage("EmailId Is Already Registered.");
					}
				} else {
					resp.setResponse("Mobile Number Is Already Registered.");
					resp.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
					resp.setMessage("Mobile Number Is Already Registered.");
				}

			}

		} catch (Exception e) {
			LOG.info("Exception e==> " + e.getStackTrace(), e);
			resp.setResponse(appConfig.getProperty("OOPS_MESSAGE"));
			resp.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			resp.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return resp;
	}

	public String generateRandomPassword(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			password.append(characters.charAt(index));
		}

		return password.toString();
	}

	// Method to generate the empId
	public String generateEmpId(String firstName) {
		String empId = firstName.substring(0, Math.min(firstName.length(), 3)).toUpperCase();

		int randomThreeDigitNumber = random.nextInt(900) + 100;

		String randomNumber = String.format("%03d", randomThreeDigitNumber);

		empId += randomNumber;

		String randomString = generateRandomString(4);
		empId += randomString.toUpperCase();

		return empId;
	}

	public String generateRandomString(int length) {
		String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		StringBuilder randomStringBuilder = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(allowedCharacters.length());
			char randomChar = allowedCharacters.charAt(randomIndex);
			randomStringBuilder.append(randomChar);
		}

		return randomStringBuilder.toString();
	}

	@SuppressWarnings("unused")
	public ResponseDTO validateLogin(HttpServletRequest request, String jsonData) {
		ResponseDTO response = new ResponseDTO();
		try {
			JSONObject json = new JSONObject(jsonData);
			String userID = json.getString("userId");
			String passWord = json.getString("password");
			String encodedPassword = "";
			String decodedPassword = "";

			if (userID.isEmpty() || passWord.isEmpty()) {
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
				response.setMessage(appConfig.getProperty("REQUIRED_FIELDS"));
				return response;
			}

//			byte[] decodedPassWord = Base64.getDecoder().decode(passWord);
//			String decodedPasswordString = new String(decodedPassWord, StandardCharsets.UTF_8);
			UserMaster userPresence = userInfoManager.getPasswordByUserId(userID);

			if (userPresence != null) {

				encodedPassword = userPresence.getPassword();
				byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
				decodedPassword = new String(decodedBytes, StandardCharsets.UTF_8);

				LOG.info("Original password from portal : " + passWord);
				//LOG.info("Password from portal after decoding : " + decodedPasswordString);
				LOG.info("Password from databases after decoding : " + decodedPassword);
				LOG.info("Original password from databases : " + encodedPassword);

				if (userID != null && !userID.trim().isEmpty()) {
					if (decodedPassword.equals(passWord)) {
						UserMaster jsonRequest = userInfoManager.getUserByUserIdAndPassword(userID, encodedPassword);
						List<SubMenuControllerDTO> userMenuControls = new ArrayList<>();

						UserInfoDto userInfo = new UserInfoDto();
						userInfo.setEmployeeId(jsonRequest.getEmployeeId() != null ? jsonRequest.getEmployeeId() : "");
						userInfo.setName(jsonRequest.getName() != null ? jsonRequest.getName() : "");
						// userInfo.setCountryId(jsonRequest.getCountryId() != null ?
						// jsonRequest.getCountryId().getId() : 0l);
						userInfo.setCountryName(
								jsonRequest.getCountryName() != null ? jsonRequest.getCountryName() : "");
						userInfo.setCreatedOn(jsonRequest.getCreatedOn());
						userInfo.setDeviceId(jsonRequest.getDeviceId() != null ? jsonRequest.getDeviceId() : "");
						userInfo.setDeviceToken(
								jsonRequest.getDeviceToken() != null ? jsonRequest.getDeviceToken() : "");
						userInfo.setDeviceType(jsonRequest.getDeviceType() != null ? jsonRequest.getDeviceType() : "");
						// userInfo.setDistrictId(jsonRequest.getDistrictId() != null ?
						// jsonRequest.getDistrictId().getId() : 0l);
						userInfo.setDistrictName(
								jsonRequest.getDistrictName() != null ? jsonRequest.getDistrictName() : "");
						userInfo.setEmailId(jsonRequest.getEmailId() != null ? jsonRequest.getEmailId() : "");
						userInfo.setFcmToken(jsonRequest.getFcmToken() != null ? jsonRequest.getFcmToken() : "");
						userInfo.setGender(jsonRequest.getGender() != null ? jsonRequest.getGender() : "");
						userInfo.setLocation(jsonRequest.getLocation() != null ? jsonRequest.getLocation() : "");
						userInfo.setMobileNumber(
								jsonRequest.getMobileNumber() != null ? jsonRequest.getMobileNumber() : "");
						userInfo.setRoleId(jsonRequest.getRole() != null ? jsonRequest.getRole().getId() : 01);
						userInfo.setRoleName(jsonRequest.getRole() != null ? jsonRequest.getRole().getName() : "");
						// userInfo.setStateId(jsonRequest.getState() != null ?
						// jsonRequest.getState().getId() : 0l);
						userInfo.setStateName(jsonRequest.getStateName() != null ? jsonRequest.getStateName() : "");
						userInfo.setStatus(jsonRequest.getStatus());
						userInfo.setDateOfBirth(jsonRequest.getDateOfBirth());
						userInfo.setId(jsonRequest.getId());
						userInfo.setModifiedOn(jsonRequest.getModifiedOn());
						userInfo.setAddressLine(jsonRequest.getAddress() != null ? jsonRequest.getAddress() : "");
						userInfo.setCity(jsonRequest.getCity() != null ? jsonRequest.getCity() : "");
						userInfo.setLandMark(jsonRequest.getLandMark() != null ? jsonRequest.getLandMark() : "");
						userInfo.setAlternativeMobileNumber(jsonRequest.getAlternativeMobileNumber() != null
								? jsonRequest.getAlternativeMobileNumber()
								: "");

						List<UserMenuMappingEntity> menuList = userMenuMappingManager
								.getUserMenuByRoleIdAndStateId(userInfo.getRoleId(), "Web");
						if (menuList != null) {
							for (UserMenuMappingEntity userMenu : menuList) {
								SubMenuControllerDTO dto = new SubMenuControllerDTO();
								MenuMaster manu = menuMasterManager.get(userMenu.getMenuId().getId());
								if (manu != null) {
									dto.setMenuControllerId(manu.getId());
									dto.setUserId(manu.getId());
									dto.setStatus(manu.getStatus());
									dto.setName(manu.getName());
									dto.setTitle(manu.getTitle());
									dto.setType(manu.getType());
									dto.setIcon(manu.getIcon());
									dto.setDescription(manu.getDescription());
									dto.setFontColour(manu.getFontColour());
									dto.setBackGroundColour(manu.getBackGroundColour());
									dto.setDisplayOrder(userMenu.getDisplayOrder());
									userMenuControls.add(dto);
								}
							}
						}
						userMenuControls = userMenuControls.stream()
								.sorted(Comparator.comparingInt(SubMenuControllerDTO::getDisplayOrder))
								.collect(Collectors.toList());
						userInfo.setUserMenuControl(userMenuControls);
						response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
						response.setMessage(appConfig.getProperty("SUCCESS"));
						response.setResponse(userInfo);
						// logUserEvent("portal", "login", jsonRequest.getMobileNumber(),"user logged in
						// successfully", userId, jsonData, userInfo);
					} else {
						response.setStatusCode(Integer.parseInt(appConfig.getProperty("NOT_EXISTS_CODE")));
						response.setMessage("Please enter the Valid Password.");
					}
				} else {
					response.setStatusCode(Integer.parseInt(appConfig.getProperty("NOT_EXISTS_CODE")));
					response.setMessage("Please Enter Valid User Id Or Password");
				}

			} else {
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("NOT_EXISTS_CODE")));
				response.setMessage("User Does Not Exist");
			}
		} catch (Exception e) {
			LOG.info("Exception Login==> "+e.getStackTrace(),e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			LOG.info(e);
		}
		return response;
	}

	public boolean getUserBasedOnUserIdNMobileNumber(String userId, String mobileNumber) {
		try {
			UserMaster user = userInfoManager.getUserBasedOnUserIdNMobileNumber(Long.parseLong(userId), mobileNumber);
			LOG.info("user::" + user);
			if (user != null) {
				if (user.getStatus())
					return true;
				else
					return false;
			}

			else
				return false;

		} catch (Exception e)

		{
			return false;
		}
	}

	public String prepareExcelReportForEmployeeData() {
		List<UserMaster> userList = userInfoManager.getAllUsersForExcelReport();
		LOG.info("prepareExcelReportForEmployeeData===> " + userList.size());
		String excelDownloadPath = "";
		try {
			String fileName = null;
			File file = null;
			String filePath = null;
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Employees Master Data");
			List<Object> headers = new ArrayList<>(Arrays.asList("Employee Id", "Employee Name", "Mobile No.",
					"EMail Id", "Date of birth", "Gender", "Designations", "Alternate Mobile No", "Zone Name",
					"State Name", "Territory Name", "District Name", "Head Quarter Name", "Address", "City", "Block",
					"Village", "Land Mark", "Pin Code"));
			CellStyle cs = ExcelUtils.createStyle(workbook, true, 11);
			ExcelUtils.createRowsWithOneCellStyle(workbook, sheet, 0, headers, true, cs);
			setColumnWidths(sheet, headers.size());
			if (userList.size() > 0) {
				int startHeaderLine = 1;
				CellStyle cs1 = ExcelUtils.createStyle(workbook, false, 11);
				for (UserMaster user : userList) {
					List<Object> rowData = new ArrayList<>();
					rowData.add((user.getEmployeeId() != null && !user.getEmployeeId().isEmpty()
							&& user.getEmployeeId().matches("\\d+")) ? user.getEmployeeId() : "0");
					rowData.add(user.getName() != null ? user.getName() : "");
					rowData.add((user.getMobileNumber() != null && !user.getMobileNumber().isEmpty()
							&& user.getMobileNumber().matches("\\d+")) ? user.getMobileNumber() : "0");
					rowData.add(user.getEmailId() != null ? user.getEmailId() : "");
					rowData.add(user.getDateOfBirth() != null ? user.getDateOfBirth() : "");
					rowData.add(user.getGender() != null ? user.getGender() : "");
					rowData.add(
							user.getRole() != null && user.getRole().getName() != null ? user.getRole().getName() : "");
					rowData.add(
							(user.getAlternativeMobileNumber() != null && !user.getAlternativeMobileNumber().isEmpty()
									&& user.getAlternativeMobileNumber().matches("\\d+"))
											? user.getAlternativeMobileNumber()
											: "0");
					// rowData.add(user.getZoneName() != null ? user.getStateName() : "");
					rowData.add(user.getStateName() != null ? user.getStateName() : "");
					// rowData.add(user.getTerritoryName() != null ? user.getTerritoryName() : "");
					rowData.add(user.getDistrictName() != null ? user.getDistrictName() : "");
					// rowData.add(user.getHeadquarterName() != null ? user.getHeadquarterName() :
					// "");
					rowData.add(user.getAddress() != null ? user.getAddress() : "");
					rowData.add(user.getCity() != null ? user.getCity() : "");
					rowData.add(user.getBlock() != null ? user.getBlock() : "");
					rowData.add(user.getVillage() != null ? user.getVillage() : "");
					rowData.add(user.getLandMark() != null ? user.getLandMark() : "");
					rowData.add((user.getPinCode() != null && !user.getPinCode().isEmpty()
							&& user.getPinCode().matches("\\d+")) ? user.getPinCode() : "0");
					ExcelUtils.createRowsWithOneCellStyle(workbook, sheet, startHeaderLine, rowData, false, cs1);
					int numericCellindexes[] = { 0, 2, 7, 18 };
					ExcelUtils.setCellStyleToNumeric(workbook, sheet, startHeaderLine, rowData, numericCellindexes,
							false, cs1);
					startHeaderLine++;
				}
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
			fileName = "EmployeeExcelReport_" + LocalDateTime.now().format(formatter) + ".xlsx";
			filePath = appConfig.getProperty("EXCEL_UPLOAD_PATH_FOR_EXPORT") + fileName;
			excelDownloadPath = appConfig.getProperty("EXCEL_DOWNLOAD_PATH_FOR_EXPORT");
			if (FileUtils.createDirs(appConfig.getProperty("EXCEL_UPLOAD_PATH_FOR_EXPORT"))) {
				file = new File(filePath);
				FileOutputStream fos = new FileOutputStream(file);
				workbook.write(fos);
				fos.close();
				excelDownloadPath = excelDownloadPath + fileName;
			}
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
		}
		return excelDownloadPath;
	}

	private void setColumnWidths(Sheet sheet, int headerSize) {
		for (int i = 0; i < headerSize; i++) {
			sheet.setColumnWidth(i, 5000);
		}
	}

	public ResponseDTO getUserBasedOnIdAndMobileNumber(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		try {
			String UserId = request.getHeader("userId");
			String mobileNumber = request.getHeader("mobileNumber");

			List<UserMaster> users = userInfoManager.getUserDataBasedOnIdAndMobileNumber(Long.parseLong(UserId),
					mobileNumber);

			List<JSONObject> userList = new ArrayList<>();
			if (userList != null) {
				for (UserMaster jsonRequest : users) {
					JSONObject userInfo = new JSONObject();

					userInfo.put("employeeId", jsonRequest.getEmployeeId() != null ? jsonRequest.getEmployeeId() : "");
					userInfo.put("name", jsonRequest.getName() != null ? jsonRequest.getName() : "");
					// userInfo.put("countryId",jsonRequest.getCountryId() != null ?
					// jsonRequest.getCountryId().getId() : 0l);
					// userInfo.put("countryName",jsonRequest.getCountryId() != null ?
					// jsonRequest.getCountryId().getName() : "");
					// userInfo.put("districtId",jsonRequest.getDistrictId() != null ?
					// jsonRequest.getDistrictId().getId() : 0l);
					// userInfo.put("districtName",jsonRequest.getDistrictId() != null ?
					// jsonRequest.getDistrictId().getName() : "");
					userInfo.put("emailId", jsonRequest.getEmailId() != null ? jsonRequest.getEmailId() : "");
					userInfo.put("gender", jsonRequest.getGender() != null ? jsonRequest.getGender() : "");
					userInfo.put("id", jsonRequest.getId());
					userInfo.put("location", jsonRequest.getLocation() != null ? jsonRequest.getLocation() : "");
					userInfo.put("mobileNumber",
							jsonRequest.getMobileNumber() != null ? jsonRequest.getMobileNumber() : "");
					userInfo.put("roleId", jsonRequest.getRole() != null ? jsonRequest.getRole().getId() : 01);
					userInfo.put("roleName", jsonRequest.getRole() != null ? jsonRequest.getRole().getName() : "");
					// userInfo.put("stateId", jsonRequest.getState() != null ?
					// jsonRequest.getState().getId() : 0l);
					// userInfo.put("stateName", jsonRequest.getState() != null ?
					// jsonRequest.getState().getName() : "");
					userInfo.put("status", jsonRequest.getStatus());
					userInfo.put("dateOfBirth", jsonRequest.getDateOfBirth());
					userInfo.put("address", jsonRequest.getAddress() != null ? jsonRequest.getAddress() : "");
					userInfo.put("city", jsonRequest.getCity() != null ? jsonRequest.getCity() : "");
					userInfo.put("landMark", jsonRequest.getLandMark() != null ? jsonRequest.getLandMark() : "");
					userInfo.put("alternativeMobileNumber",
							jsonRequest.getAlternativeMobileNumber() != null ? jsonRequest.getAlternativeMobileNumber()
									: "");
					userInfo.put("profileImage", jsonRequest.getProfilePicUrl() != null
							? appConfig.getProperty("APPLICATION_URL") + jsonRequest.getProfilePicUrl()
							: appConfig.getProperty("APPLICATION_URL") + appConfig.getProperty("DEFAULT_PROFILE_PIC"));
					userInfo.put("loginId", jsonRequest.getLoginId() != null ? jsonRequest.getLoginId() : "");
					userInfo.put("pinCode", jsonRequest.getPinCode() != null ? jsonRequest.getPinCode() : "");
					userInfo.put("village", jsonRequest.getVillage() != null ? jsonRequest.getVillage() : "");
					userInfo.put("block", jsonRequest.getBlock() != null ? jsonRequest.getBlock() : "");
					// userInfo.put("headquarterId", jsonRequest.getHeadQuarter() != null ?
					// jsonRequest.getHeadQuarter().getId(): 0l);
					// userInfo.put("territoryId", jsonRequest.getTerritory() != null ?
					// jsonRequest.getTerritory().getId() :0l);
					// userInfo.put("regionId", jsonRequest.getRegion() != null ?
					// jsonRequest.getRegion().getId(): 0l);
					// userInfo.put("zoneId", jsonRequest.getZone() != null ?
					// jsonRequest.getZone().getId():0l);
					// userInfo.put("countryId", jsonRequest.getCountryId() != null ?
					// jsonRequest.getCountryId().getId():0l);
					// userInfo.put("zoneName", jsonRequest.getZone() != null ?
					// jsonRequest.getZone().getName():"");
					// userInfo.put("territoryName", jsonRequest.getTerritory() != null ?
					// jsonRequest.getTerritory().getName():"");
					// userInfo.put("headquarterName", jsonRequest.getHeadQuarter() != null ?
					// jsonRequest.getHeadQuarter().getName():"");
					// userInfo.put("regionName", jsonRequest.getRegion() != null ?
					// jsonRequest.getRegion().getName():"");
					userList.add(userInfo);
				}
			}

//			if (userList != null) {
//				for (UserMasterEntity userData : userList) {
//					userData.setProfilePicUrl(userData.getProfilePicUrl() != null ? appConfig.getProperty("APPLICATION_URL") + userData.getProfilePicUrl() :appConfig.getProperty("APPLICATION_URL") +appConfig.getProperty("DEFAULT_PROFILE_PIC"));
//					userData.setPassword("");
//						if(userData.getRole().getId()==2) {
//							userData.setZoneName(userData.getZone()!=null?userData.getZone().getName():"");
//						}
//						if(userData.getRole().getId()==3) {
//							userData.setZoneName(userData.getZone()!=null?userData.getZone().getName():"");
//							userData.setRegionName(userData.getRegion()!=null?userData.getRegion().getName():"");
//						}
//						if(userData.getRole().getId()==4) {
//							userData.setZoneName(userData.getZone()!=null?userData.getZone().getName():"");
//							userData.setRegionName(userData.getRegion()!=null?userData.getRegion().getName():"");
//							userData.setTerritoryName(userData.getTerritory()!=null?userData.getTerritory().getName():"");
//						}
//						if(userData.getRole().getId()==5) {
//							userData.setZoneName(userData.getZone()!=null?userData.getZone().getName():"");
//							userData.setRegionName(userData.getRegion()!=null?userData.getRegion().getName():"");
//							userData.setTerritoryName(userData.getTerritory()!=null?userData.getTerritory().getName():"");
//							userData.setHeadquarterName(userData.getHeadQuarter()!=null?userData.getHeadQuarter().getName():"");
//						}
//					}
//				
//		
//		
//			}

			JSONObject json = new JSONObject();
			json.put("usersList", userList);
			response.setResponse(JSONValue.parse(json.toString()));
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
			response.setMessage(appConfig.getProperty("SUCCESS"));

		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Transactional
	public ResponseDTO addMenuController(HttpServletRequest request, ResponseDTO response, String jsonData) {
		try {

			JSONObject jsonRequest = new JSONObject(jsonData);

			Long roleId = jsonRequest.getLong("roleId");
//					Long stateId = jsonRequest.getLong("stateId");
			String type = jsonRequest.getString("type");
			//String deviceType = request.getHeader("deviceType");
			//String mobileNumber = request.getHeader("mobileNumber");
			//String userId = request.getHeader("userId");

			JSONArray userMenus = jsonRequest.getJSONArray("userMenus");

			UserMenuMappingEntity userMapping = userMenuMappingManager.getUserbyMobileNumber(roleId);

			if (userMapping != null) {
				LOG.info("Role State  Mapped");
				String deleteUserMenuMappingQuery = "DELETE FROM TBL_USER_MENU_MAPPING WHERE ROLE_ID = " + roleId
						+ " AND TYPE='" + type + "'";

				NativeQuery<UserMenuMappingEntity> sqlQuery = hibernateDao.getSession()
						.createNativeQuery(deleteUserMenuMappingQuery);
				sqlQuery.executeUpdate();
			}

			for (int i = 0; i < userMenus.length(); i++) {
				JSONObject menuObject = userMenus.getJSONObject(i);
				JSONArray subItems = menuObject.getJSONArray("subItems");
				LOG.info("menuObject ==> " + menuObject.toString());
				if (subItems.length() > 0) {
					LOG.info("subItems ==>" + subItems.toString());
					for (int j = 0; j < subItems.length(); j++) {
						JSONObject subItemsObject = subItems.getJSONObject(j);
						Long subMenuId = subItemsObject.optLong("subMenuId");
						LOG.info("subMenuId ==> " + subMenuId);
						boolean isView = subItemsObject.getBoolean("isView");
						boolean isDelete = subItemsObject.getBoolean("isDelete");
						boolean isEdit = subItemsObject.getBoolean("isEdit");

						Long menuId = menuObject.getLong("menuId");
						String name = subItemsObject.getString("name");

						RoleMaster role = roleMasterManager.get(roleId);
//								StateMasterEntity state = stateManager.get(stateId);
						MenuMaster menu = menuMasterManager.get(menuId);
						SubMenuMaster subMenu = subMenuMasterManager.get(subMenuId);

						UserMenuMappingEntity userMap = new UserMenuMappingEntity();

						userMap.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
						userMap.setMenuId(menu);
						userMap.setSubMenuId(subMenu);
						userMap.setRoleId(role);
//								userMap.setStateId(state);
						userMap.setEdit(isEdit);
						userMap.setView(isView);
						userMap.setDelete(isDelete);
						userMap.setName(name);
						userMap.setStatus(true);
						userMap.setType(type);

						userMenuMappingManager.mergeSaveOrUpdate(userMap);
					}
				} else {
					LOG.info("subItems Empty ==>" + subItems.toString());
					Long menuId = menuObject.getLong("menuId");
					Long subMenuId = menuObject.optLong("subMenuId");

					// boolean checked = menuObject.getBoolean("checked");
					boolean isView = menuObject.getBoolean("isView");
					boolean isDelete = menuObject.getBoolean("isDelete");
					boolean isEdit = menuObject.getBoolean("isEdit");

					String name = menuObject.getString("name");

					RoleMaster role = roleMasterManager.get(roleId);
//							StateMasterEntity state = stateManager.get(stateId);
					MenuMaster menu = menuMasterManager.get(menuId);
					SubMenuMaster subMenu = subMenuMasterManager.get(subMenuId);

					UserMenuMappingEntity userMap = new UserMenuMappingEntity();

					userMap.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
					userMap.setMenuId(menu);
					userMap.setSubMenuId(subMenu);
					userMap.setRoleId(role);
//							userMap.setStateId(state);
					userMap.setEdit(isEdit);
					userMap.setView(isView);
					userMap.setDelete(isDelete);
					userMap.setName(name);
					userMap.setStatus(true);
					userMap.setType(type);

					userMenuMappingManager.mergeSaveOrUpdate(userMap);

				}

				response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
				response.setMessage(appConfig.getProperty("SUCCESS"));
				// logUserEvent(deviceType, "Menu", mobileNumber,response.getMessage(),
				// userId,jsonData,response);
			}

		} catch (Exception e) {

			LOG.info("addMenuController==> " + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;

	}

	public ResponseDTO getMenuControles(ResponseDTO response, String jsonData) {
		try {
			JSONObject json = new JSONObject(jsonData);
			String type = json.getString("type");
			Long roleId = commonService.checkJsonObjectAndGetId(json, roleIdParam);
			ArrayList<JSONObject> menuListList = new ArrayList<>();
			List<MenuMaster> menuMasterList = menuMasterManager.getMenuListbasedOnType(type);

			JSONObject jsonOutput = new JSONObject();
			jsonOutput.put("type", type);
			jsonOutput.put("roleId", roleId);

			if (menuMasterList != null) {
				for (MenuMaster mList : menuMasterList) {
					UserMenuMappingEntity menuData = userMenuMappingManager
							.getUserMenuByRoleIdAndStateIdAndMainMenuId(roleId, mList.getId());

					JSONObject menuDto = new JSONObject();
					if (menuData != null) {
						menuDto.put("isEdit", menuData.isEdit());
						menuDto.put("isView", menuData.isView());
						menuDto.put("isDelete", menuData.isDelete());
						menuDto.put("name", menuData.getMenuId().getName());
						menuDto.put("menuId", menuData.getMenuId().getId());

					} else {
						menuDto.put("isEdit", false);
						menuDto.put("isView", false);
						menuDto.put("isDelete", false);
						menuDto.put("name", mList.getName());
						menuDto.put("menuId", mList.getId());
					}

					List<SubMenuMaster> subMenuMasterList = subMenuMasterManager
							.getSubMenuListBasedOnManuId(mList.getId());

					if (subMenuMasterList != null) {
						ArrayList<JSONObject> subMenuList = new ArrayList<>();

						for (SubMenuMaster smList : subMenuMasterList) {
							UserMenuMappingEntity subMenuData = userMenuMappingManager
									.getUserMenuByRoleIdAndStateIdAndSubMenuId(roleId, smList.getId());
							JSONObject subMenuDTO = new JSONObject();
							if (subMenuData != null) {
								SubMenuMaster subMenuMaster = subMenuData.getSubMenuId();
								if (subMenuMaster != null) {
									subMenuDTO.put("isEdit", subMenuData.isEdit());
									subMenuDTO.put("isView", subMenuData.isView());
									subMenuDTO.put("isDelete", subMenuData.isDelete());
									subMenuDTO.put("name", subMenuMaster.getTitle());
									subMenuDTO.put("menuId", subMenuData.getMenuId().getId());
									subMenuDTO.put("subMenuId", subMenuMaster.getId());
								} else {
									subMenuDTO.put("isEdit", false);
									subMenuDTO.put("isView", false);
									subMenuDTO.put("isDelete", false);
									subMenuDTO.put("name", smList.getTitle());
									subMenuDTO.put("menuId", smList.getMenuId().getId());
									subMenuDTO.put("subMenuId", smList.getId());
								}
							} else {
								subMenuDTO.put("isEdit", false);
								subMenuDTO.put("isView", false);
								subMenuDTO.put("isDelete", false);
								subMenuDTO.put("name", smList.getTitle());
								subMenuDTO.put("menuId", smList.getMenuId().getId());
								subMenuDTO.put("subMenuId", smList.getId());
							}
							subMenuList.add(subMenuDTO);
						}

						menuDto.put("subItems", subMenuList);

					}

					menuListList.add(menuDto);
					jsonOutput.put("userMenus", menuListList);
				}
			}

			response.setResponse(JSONValue.parse(jsonOutput.toString()));
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
			response.setMessage(appConfig.getProperty("SUCCESS"));
		} catch (Exception e) {
			LOG.info("getMenuControles==> " + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;
	}

	public ResponseDTO getMenuControlesForPortal_Old(ResponseDTO response, String jsonData) {
		try {
			JSONObject json = new JSONObject(jsonData);
			String type = json.getString("type");
			Long roleId = json.getLong("roleId");

			JSONArray menuList = new JSONArray();
			List<MenuMaster> menuMasterList = menuMasterManager.getMenuListbasedOnType(type);

			if (menuMasterList != null) {
				for (MenuMaster menuMaster : menuMasterList) {
					UserMenuMappingEntity menuData = userMenuMappingManager.getUserMenuByRoleIdAndMainMenuId(roleId,menuMaster.getId());
					JSONObject menuDto = new JSONObject();
					JSONArray pagesList = new JSONArray();

					if (menuData != null) {
						JSONObject page = new JSONObject();
						page.put("icon",appConfig.getProperty("APPLICATION_URL") + menuMaster.getIcon() != null? appConfig.getProperty("APPLICATION_URL") + menuMaster.getIcon(): "");
						page.put("name", menuMaster.getTitle() != null ? menuMaster.getTitle() : "");
						page.put("path", menuMaster.getPath());
						page.put("element", menuMaster.getElement());

						menuDto.put("layout", menuMaster.getName());
						pagesList.put(page);
						menuDto.put("pages", pagesList);

						List<SubMenuMaster> subMenuMasterList = subMenuMasterManager.getSubMenuListBasedOnManuId(menuMaster.getId());

						if (subMenuMasterList != null && !subMenuMasterList.isEmpty()) {
							JSONArray subMenuList = new JSONArray();

							for (SubMenuMaster subMenuMaster : subMenuMasterList) {
								UserMenuMappingEntity subMenuData = userMenuMappingManager.getUserMenuByRoleIdAndSubMenuId(roleId, subMenuMaster.getId());
								JSONObject subMenuDTO = new JSONObject();

								if (subMenuData != null) {
									subMenuDTO.put("icon",appConfig.getProperty("APPLICATION_URL") + subMenuMaster.getIcon() != null? appConfig.getProperty("APPLICATION_URL") + subMenuMaster.getIcon(): "");
									subMenuDTO.put("name",subMenuMaster.getTitle() != null ? subMenuMaster.getTitle() : "");
									subMenuDTO.put("path", subMenuMaster.getPath());
									subMenuDTO.put("element", subMenuMaster.getElement());
									subMenuList.put(subMenuDTO);
								}
							}

							if (subMenuList.length() > 0) {
								page.put("subItems", subMenuList);
							}
						}
					}
					if (menuDto.length() > 0) {
						menuList.put(menuDto);
					}
				}
			}

			response.setResponse(JSONValue.parse(menuList.toString()));
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
			response.setMessage(appConfig.getProperty("SUCCESS"));

		} catch (Exception e) {
			LOG.info("getMenuControles==> " + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;
	}
	
	public ResponseDTO getMenuControlesForPortal(ResponseDTO response, String jsonData) {
		try {
			JSONObject json = new JSONObject(jsonData);
			String type = json.getString("type");
			Long roleId = json.getLong("roleId");

			JSONArray menuList = new JSONArray();
			List<MenuMaster> menuMasterList = menuMasterManager.getMenuListbasedOnType(type);

			if (menuMasterList != null) {
				for (MenuMaster menuMaster : menuMasterList) {
					UserMenuMappingEntity menuData = userMenuMappingManager.getUserMenuByRoleIdAndMainMenuId(roleId,menuMaster.getId());
					JSONObject menuDto = new JSONObject();
				
					if (menuData != null) {
						menuDto.put("name", menuMaster.getName());
						menuDto.put("path", menuMaster.getPath());
						menuDto.put("icon", menuMaster.getIcon());
						List<SubMenuMaster> subMenuMasterList = subMenuMasterManager
								.getSubMenuListBasedOnManuId(menuMaster.getId());

						if (subMenuMasterList != null && !subMenuMasterList.isEmpty()) {
							JSONArray subMenuList = new JSONArray();

							for (SubMenuMaster subMenuMaster : subMenuMasterList) {
								UserMenuMappingEntity subMenuData = userMenuMappingManager
										.getUserMenuByRoleIdAndSubMenuId(roleId, subMenuMaster.getId());
								JSONObject subMenuDTO = new JSONObject();

								if (subMenuData != null) {
									subMenuDTO.put("icon",
											appConfig.getProperty("APPLICATION_URL") + subMenuMaster.getIcon() != null
													? appConfig.getProperty("APPLICATION_URL") + subMenuMaster.getIcon()
													: "");
									subMenuDTO.put("name",
											subMenuMaster.getTitle() != null ? subMenuMaster.getTitle() : "");
									subMenuDTO.put("path", subMenuMaster.getPath());
									subMenuList.put(subMenuDTO);
								}
							}

							if (subMenuList.length() > 0) {
								menuDto.put("children", subMenuList);
							}
						}
					}
					if (menuDto.length() > 0) {
						menuList.put(menuDto);
					}
				}
			}

			response.setResponse(JSONValue.parse(menuList.toString()));
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
			response.setMessage(appConfig.getProperty("SUCCESS"));

		} catch (Exception e) {
			LOG.info("getMenuControles==> " + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;
	}
	
	public ResponseDTO forgetPassword(HttpServletRequest request, ResponseDTO resp, String jsonData) {
		try {
			String mailBody = "";
			UserMaster userInfo = null;
			JSONObject jsonRequest = new JSONObject(jsonData);
			LOG.info("Json Data================>" + jsonData);
			String mobileNumber = jsonRequest.getString("mobileNumber");

			if (mobileNumber.isEmpty()) {
				resp.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
				resp.setMessage(appConfig.getProperty("REQUIRED_FIELDS"));
				return resp;
			}

			userInfo = userInfoManager.getUserbyMobileNumber(mobileNumber);

			if (userInfo != null) {

				byte[] decodedBytes = Base64.getDecoder().decode(userInfo.getPassword());
				String newPasswordFromUser = new String(decodedBytes, StandardCharsets.UTF_8);

				String toMail = userInfo.getEmailId();
				toMail.split(",");

				String OTPMessage = String.format(appConfig.getProperty("CREATED_PASSWORD"), userInfo.getPassword());

				mailBody = OTPMessage;
				// emailService.sendEmail(strMails, null, "Your VyaparMitra Password", mailBody,
				// null, null);
				LOG.info("Email Body===============>" + mailBody);

				// resp.setResponse("Dear user, your password to log in to VyaparMithra is " +
				// newPasswordFromUser);
				resp.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
				resp.setMessage("Dear user, your password to log in to Kipl is " + userInfo.getPassword());

			} else {
				resp.setResponse("please enter the valid mobile number.");
				resp.setStatusCode(Integer.parseInt(appConfig.getProperty("NOT_EXISTS_CODE")));
				resp.setMessage("please enter the valid mobile number.");
			}

		} catch (Exception e) {
			resp.setResponse(appConfig.getProperty("OOPS_MESSAGE"));
			resp.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			resp.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			LOG.info("Error occurred: " + e);
		}

		return resp;
	}
	
	public boolean getUserBasedOnCustomerIdNMobileNumberNDeviceId(String userId, String mobileNumber, String deviceId,String deviceToken) {
		try
		{
			UserMaster employee = userInfoManager.getCustomerBasedOnCustomerIdNMobileNumberNDeviceId(userId, mobileNumber, deviceId, deviceToken);
			if(employee != null) 
			{
				if(employee.getStatus() == true) return true;
				else return false;
			}
			else return false;
		}
		catch(Exception e)
		{
			LOG.info(e.getCause(), e);
			return false;
		}
	
	}
	
	public ResponseDTO getEmployeeDashBoardDetailes(HttpServletRequest request, String jsonData) {
		ResponseDTO response = new ResponseDTO();
		try {
			JSONObject jsonRequest = new JSONObject(jsonData);
			Long userId = jsonRequest.getLong("userId");
			jsonRequest.getString("mobileNumber");
			
			UserMaster employee = null;
			List<JSONObject> respList = new ArrayList<>();
			List<JSONObject> userMenuControls = new ArrayList<>();
			//SeasonMaster season = null;
			employee = userInfoManager.get(userId);

			if (employee != null) {

				JSONObject dto = new JSONObject();

				long notificationCount = sqlQueryManager.getEmployeeNotificationsCount(employee.getId());
				dto.put("name", employee.getName() != null ? employee.getName() : "");
				dto.put("mobileNumber", employee.getMobileNumber() != null ? employee.getMobileNumber() : "");
				
				String roleBased = employee.getRole() != null ? employee.getRole().getName() : "";
				
//				if (employee.getRole().getName().equalsIgnoreCase("CUMM") || employee.getRole().getName().equalsIgnoreCase("Zonal Manager")) {
//				    if (employee.getZone() != null) {
//				        roleBased += "-" + employee.getZone().getName();
//				    }
//				} else if (employee.getRole().getName().equalsIgnoreCase("Regional Manager")) {
//				    if (employee.getRegion() != null) {
//				        roleBased += "-" + employee.getRegion().getName();
//				    }
//				} else if (employee.getRole().getName().equalsIgnoreCase("Territory Manager")) {
//				    if (employee.getTerritory() != null) {
//				        roleBased += "-" + employee.getTerritory().getName();
//				    }
//				} else if (employee.getRole().getName().equalsIgnoreCase("MDO")) {
//				    if (employee.getHeadQuarter() != null) {
//				        roleBased += "-" + employee.getHeadQuarter().getName();
//				    }
//				}
				dto.put("roleBased", roleBased);

				dto.put("employeeId", employee.getEmployeeId() != null ? employee.getEmployeeId() : "");
				dto.put("name", employee.getName() != null ? employee.getName() : "");
				//dto.put("countryId",employee.getCountryId() != null ? employee.getCountryId().getId() : 0l);
				//dto.put("countryName",employee.getCountryId() != null ? employee.getCountryId().getName() : "");
				//dto.put("districtId",employee.getDistrictId() != null ? employee.getDistrictId().getId() : 0l);
				//dto.put("districtName",employee.getDistrictId() != null ? employee.getDistrictId().getName() : "");
				dto.put("emailId", employee.getEmailId() != null ? employee.getEmailId() : "");
				dto.put("gender", employee.getGender() != null ? employee.getGender() : "");
				dto.put("id", employee.getId());
				dto.put("location", employee.getLocation() != null ? employee.getLocation() : "");
				dto.put("mobileNumber",employee.getMobileNumber() != null ? employee.getMobileNumber() : "");
				dto.put("roleId", employee.getRole() != null ? employee.getRole().getId() : 01);
				dto.put("roleName", employee.getRole() != null ? employee.getRole().getName() : "");
				//dto.put("stateId", employee.getState() != null ? employee.getState().getId() : 0l);
				//dto.put("stateName", employee.getState() != null ? employee.getState().getName() : "");
				dto.put("status", employee.getStatus());
				dto.put("dateOfBirth", employee.getDateOfBirth());
				dto.put("address", employee.getAddress() != null ? employee.getAddress() : "");
				dto.put("city", employee.getCity() != null ? employee.getCity() : "");
				dto.put("landMark", employee.getLandMark() != null ? employee.getLandMark() : "");
				dto.put("alternativeMobileNumber",employee.getAlternativeMobileNumber() != null ? employee.getAlternativeMobileNumber(): "");
				dto.put("profileImage", employee.getProfilePicUrl() != null ? appConfig.getProperty("APPLICATION_URL") + employee.getProfilePicUrl() :appConfig.getProperty("APPLICATION_URL") +appConfig.getProperty("DEFAULT_PROFILE_PIC"));
				dto.put("loginId", employee.getLoginId() != null ? employee.getLoginId():"");
				dto.put("pinCode", employee.getPinCode() != null ? employee.getPinCode():"");
				dto.put("village", employee.getVillage() != null ? employee.getVillage():"");
				dto.put("block", employee.getBlock() != null ? employee.getBlock():"");
				//dto.put("headquarterId", employee.getHeadQuarter() != null ? employee.getHeadQuarter().getId(): 0l);
				//dto.put("territoryId", employee.getTerritory() != null ? employee.getTerritory().getId() :0l);
				//dto.put("regionId", employee.getRegion() != null ? employee.getRegion().getId(): 0l);
				//dto.put("zoneId", employee.getZone() != null ? employee.getZone().getId():0l);
				//dto.put("countryId", employee.getCountryId() != null ? employee.getCountryId().getId():0l);
				//dto.put("zoneName", employee.getZone() != null ? employee.getZone().getName():"");
				//dto.put("territoryName", employee.getTerritory() != null ? employee.getTerritory().getName():"");
				//dto.put("headquarterName", employee.getHeadQuarter() != null ? employee.getHeadQuarter().getName():"");
				//dto.put("regionName", employee.getRegion() != null ? employee.getRegion().getName():"");
					
				dto.put("notificationCount", notificationCount);
				respList.add(dto);

				//long totalRetailerCount = sqlQueryManager.getTotalRetailerCount("0", employee,"","");
				//long totalParticipatedRetailersCount = sqlQueryManager.getTotalParticipatedRetailersCount(season, "0",employee);

//				long activeRetailersCount = sqlQueryManager.getActiveRetailersCount(employee);
//				long totalRetailersCount = sqlQueryManager.totalRetailersCount(employee);
				
				List<UserMenuMappingEntity> menuList = userMenuMappingManager.getUserMenuByRoleIdAndStateId(employee.getRole().getId(),"Mobile");

				if (menuList != null) {
					LOG.info("enter into menuList ==>" + menuList.size());
					Set<Long> menuId = new HashSet<>();
					for (UserMenuMappingEntity userMenu : menuList) {
						JSONObject subMenuDto = new JSONObject();
						if (menuId.add(userMenu.getMenuId().getId())) {//For avoiding duplicate sub menu
							MenuMaster menu = menuMasterManager.get(userMenu.getMenuId().getId());
							if (menu != null) {
								subMenuDto.put("menuControllerId", menu.getId());
								subMenuDto.put("userId", menu.getId());
								subMenuDto.put("status", menu.getStatus());
								subMenuDto.put("name", menu.getName());
								subMenuDto.put("title", menu.getTitle());
								subMenuDto.put("type", menu.getType());
								subMenuDto.put("description", menu.getDescription());
								subMenuDto.put("fontColour", menu.getFontColour());
								subMenuDto.put("backGroundColour", menu.getBackGroundColour());
								subMenuDto.put("icon", appConfig.getProperty("APPLICATION_URL") + menu.getIcon());
								subMenuDto.put("displayOrder",userMenu.getDisplayOrder());
								userMenuControls.add(subMenuDto);
							}
						}
					}
				}
				userMenuControls.sort(Comparator.comparingInt(json -> json.getInt("displayOrder")));
				/*if (menuList != null) {
					LOG.info("enter into menuList ==>");
					List<UserMenuMappingEntity> reorderedList = new ArrayList<>();
					if (employee.getRole().getId() == 4) {

						reorderedList.add(menuList.get(2));
						reorderedList.add(menuList.get(3));
						reorderedList.add(menuList.get(0));
						reorderedList.add(menuList.get(1));
					} else {
						reorderedList.add(menuList.get(2));
						reorderedList.add(menuList.get(0));
						reorderedList.add(menuList.get(1));
					}
					for (UserMenuMappingEntity userMenu : reorderedList) {
						JSONObject subMenuDto = new JSONObject();
						MenuMaster menu = menuMasterManager.get(userMenu.getMenuId().getId());
						if (menu != null) {
							
							subMenuDto.put("menuControllerId", menu.getId());
							subMenuDto.put("userId", menu.getId());
							subMenuDto.put("status", menu.getStatus());
							subMenuDto.put("name", menu.getName());
							subMenuDto.put("title", menu.getTitle());
							subMenuDto.put("type", menu.getType());
							subMenuDto.put("description", menu.getDescription());
							subMenuDto.put("fontColour", menu.getFontColour());
							subMenuDto.put("backGroundColour", menu.getBackGroundColour());
							subMenuDto.put("icon", appConfig.getProperty("APPLICATION_URL") + menu.getIcon());
							userMenuControls.add(subMenuDto);
						}
					}
				}*/
		
				JSONObject json = new JSONObject();
				//json.put("totalRetailers", totalRetailerCount);
				//json.put("activeRetailers", totalParticipatedRetailersCount);
				json.put("userList", respList);
				json.put("userMenuControl", userMenuControls);
			
				response.setResponse(JSONValue.parse(json.toString()));
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
				response.setMessage(appConfig.getProperty("SUCCESS"));

			} else {
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("NOT_EXISTS_CODE")));
				response.setMessage("No User Exists");
			}

		} catch (Exception e) {
			LOG.info("==> " + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;
	}
	
	public ResponseDTO getExistingEmployeeDetails(HttpServletRequest request, String jsonData) {
	    ResponseDTO response = new ResponseDTO();
	    try {
	        JSONObject jsonRequest = new JSONObject(jsonData);
	        String name = jsonRequest.optString("name");
	        String mobileNumber = jsonRequest.optString("mobileNumber");
	        Long employeeUserId = jsonRequest.optLong("employeeUserId", 0);
	        Long zoneId = jsonRequest.optLong("zoneId", 0);
	        Long stateId = jsonRequest.optLong("stateId", 0);
	        Long regionId = jsonRequest.optLong("regionId", 0);
	        Long territoryId = jsonRequest.optLong("territoryId", 0);
	        Long districtId = jsonRequest.optLong("districtId", 0);
	        Long headquarterId = jsonRequest.optLong("headquarterId", 0);
	        Long roleId = jsonRequest.optLong("roleId", 0);

	        JSONObject resp = new JSONObject();
	        UserMaster employee = null;

	        if (!name.isEmpty() && !mobileNumber.isEmpty()) {
	            employee = userInfoManager.getToBeUpdatedUserDetails(name, mobileNumber);
	        } else if (employeeUserId != 0) {
	            employee = userInfoManager.get(employeeUserId);
	        }

	        UserMaster userExistence = userInfoManager.getExistingEmployeeDetails(roleId, headquarterId, territoryId, regionId, zoneId, stateId, districtId);

	        if (userExistence != null) {
	            if (employee != null) {
	                resp.put("name", employee.getName());
	                resp.put("mobileNumber", employee.getMobileNumber());

	                if (!isSameEmployee(userExistence, employee)) {
	                    populateResponse(response, userExistence, resp);
	                } else {
						response.setStatusCode(502);
	                    response.setMessage("There is an Employee already present with given Details");
	                    response.setResponse(JSONValue.parse(resp.toString()));
	                }
	            } else if (!isSameEmployee(userExistence, name, mobileNumber)) {
	                resp.put("name", name);
	                resp.put("mobileNumber", mobileNumber);
	                populateResponse(response, userExistence, resp);
	            } else {
					response.setStatusCode(502);
	                response.setMessage("There is an Employee already present with given Details");
	                response.setResponse(JSONValue.parse(resp.toString()));
	            }
	        } else {
	            response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
	            response.setMessage("No Record Exists");
	            response.setResponse(JSONValue.parse(resp.toString()));
	        }
	    } catch (Exception e) {
	        LOG.error("Error while fetching employee details: ", e);
	        response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
	        response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
	    }
	    return response;
	}
	
	private boolean isSameEmployee(UserMaster userExistence, UserMaster employee) {
	    return userExistence.getName().equalsIgnoreCase(employee.getName())
	            && userExistence.getMobileNumber().equalsIgnoreCase(employee.getMobileNumber());
	}

	private boolean isSameEmployee(UserMaster userExistence, String name, String mobileNumber) {
	    return userExistence.getName().equalsIgnoreCase(name)&& userExistence.getMobileNumber().equalsIgnoreCase(mobileNumber);
	}

	private void populateResponse(ResponseDTO response, UserMaster userExistence, JSONObject resp) {
	    String roleName = userExistence.getRole().getName();
	   // String locationName = getLocationName(userExistence);

	    response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
	    response.setMessage("An employee named " + userExistence.getName() + " already exists as " + roleName + ". Do you want to continue?");
	    response.setResponse(JSONValue.parse(resp.toString()));
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Transactional
	public ResponseDTO reMappingEmployee(HttpServletRequest request, String jsonData) {
		ResponseDTO response = new ResponseDTO();
		try {
			String updateReMappingQuery = "";
			String updateEkycQuery = "";
			RoleMaster role = null;
			UserMaster newUser = null;
			UserMaster updatedBy = null;
			UserMaster userExistence = null;
			ReMappingHistory mapHistory = null;
			ReMappingHistory oldHistoryExists = null;
			String newRoleType = "";
			String newUserUpdationInUserInfo = "";
			JSONObject jsonRequest = new JSONObject(jsonData);
			String name = jsonRequest.optString("name");
			String mobileNumber = jsonRequest.optString("mobileNumber");
			Long zoneId = jsonRequest.getLong("zoneId");
			Long employeeUserId = jsonRequest.optLong("employeeUserId");
			Long stateId = jsonRequest.getLong("stateId");
			Long regionId = jsonRequest.getLong("regionId");
			Long territoryId = jsonRequest.getLong("territoryId");
			Long districtId = jsonRequest.getLong("districtId");
			Long headquarterId = jsonRequest.getLong("headquarterId");
			Long roleId = jsonRequest.getLong("roleId");
			String updaterUserId = request.getHeader("userId");

			if ((name != null && !name.isEmpty()) && (mobileNumber != null && !mobileNumber.isEmpty())) {
				newUser = userInfoManager.getToBeUpdatedUserDetails(name, mobileNumber);
			} else if (employeeUserId != null && employeeUserId != 0) {
				newUser = userInfoManager.get(employeeUserId);
			}
			
			userExistence = userInfoManager.getExistingEmployeeDetails(roleId, headquarterId, territoryId, regionId,zoneId, stateId, districtId);
			updatedBy = userInfoManager.get(Long.parseLong(updaterUserId));
			role = roleMasterManager.get(roleId);

			if (userExistence != null) {
				if (userExistence.getId() == newUser.getId()) {
					response.setStatusCode(502);
					response.setMessage("There is a user already present with given details");
					return response;
				}
			}
			
			if (newUser != null) {

				if (newUser.getRole().getId() == 2) {
						newRoleType = " UPDATE TBL_EMPLOYEE_RETAILER_MAPPING SET ZONAL_MANAGER_ID=NULL,ZONAL_MANAGER=NULL WHERE ZONAL_MANAGER_ID = "+newUser.getId()+"";
					} else if (newUser.getRole().getId() == 3) {
						newRoleType = " UPDATE TBL_EMPLOYEE_RETAILER_MAPPING SET REGIONAL_MANAGER_ID=NULL,REGIONAL_MANAGER=NULL WHERE REGIONAL_MANAGER_ID = " + newUser.getId() + "";
					} else if (newUser.getRole().getId() == 4) {
						newRoleType = " UPDATE TBL_EMPLOYEE_RETAILER_MAPPING SET TERRITORY_MANAGER_ID=NULL,TERRITORY_MANAGER=NULL WHERE TERRITORY_MANAGER_ID = " + newUser.getId() + "";
					} else if (newUser.getRole().getId() == 5) {
						newRoleType = " UPDATE TBL_EMPLOYEE_RETAILER_MAPPING SET MDO_ID=NULL,MDO=NULL WHERE MDO_ID = " + newUser.getId() + "";
					} else if (newUser.getRole().getId() == 10) {
						newRoleType = " UPDATE TBL_EMPLOYEE_RETAILER_MAPPING SET CUMM_ID=NULL,CUMM_NAME=NULL WHERE CUMM_ID = " + newUser.getId() + "";
					}					
					
					NativeQuery<ReMappingHistory> reUpdate = hibernateDao.getSession().createNativeQuery(newRoleType);
					reUpdate.executeUpdate();
					
					if (roleId == 2) {
						
						updateReMappingQuery = "UPDATE TBL_EMPLOYEE_RETAILER_MAPPING SET ZONAL_MANAGER_ID  = "+newUser.getId()+",ZONAL_MANAGER = '"+newUser.getName()+"' WHERE ZONE_ID = "+zoneId+"";
						
						newUserUpdationInUserInfo="UPDATE TBL_USER_INFO SET ROLE_ID = "+role.getId()+", ROLE_NAME='"+role.getName()+"', ZONE_ID="+zoneId+", REGION_ID = NULL, STATE_ID=NULL, DISTRICT_ID=NULL, TERRITORY_ID=NULL, HEAD_QUARTER_ID=NULL WHERE ID = "+newUser.getId()+"";
					
					} else if (roleId == 3) {
						
						updateReMappingQuery = "UPDATE TBL_EMPLOYEE_RETAILER_MAPPING SET REGIONAL_MANAGER_ID = "+newUser.getId()+",REGIONAL_MANAGER='"+newUser.getName()+"' WHERE ZONE_ID = "+zoneId+" AND STATE_ID = "+stateId+" AND REGION_ID = "+regionId+"";
					
						newUserUpdationInUserInfo="UPDATE TBL_USER_INFO SET ROLE_ID = "+role.getId()+", ROLE_NAME='"+role.getName()+"', ZONE_ID="+zoneId+", REGION_ID = "+regionId+", STATE_ID="+stateId+", DISTRICT_ID=NULL, TERRITORY_ID=NULL, HEAD_QUARTER_ID=NULL WHERE ID = "+newUser.getId()+"";

					} else if (roleId == 4) {
						
						updateReMappingQuery = "UPDATE TBL_EMPLOYEE_RETAILER_MAPPING SET TERRITORY_MANAGER_ID = "+newUser.getId()+",TERRITORY_MANAGER='"+newUser.getName()+"' WHERE ZONE_ID = "+zoneId+" AND STATE_ID = "+stateId+" AND REGION_ID = "+regionId+" AND TERRITORY_ID = "+territoryId+"";

						if (userExistence != null) {
							updateEkycQuery = "UPDATE TBL_EKYC_ENTITY SET TERRITORY_MANAGER_ID = "+newUser.getId()+", TERRITORY_MANAGER_MOBILE_NUMBER = '"+newUser.getMobileNumber()+"' WHERE TERRITORY_MANAGER_ID = "+userExistence.getId()+"";
							NativeQuery<ReMappingHistory> ekycQuery = hibernateDao.getSession().createNativeQuery(updateEkycQuery);
							ekycQuery.executeUpdate();
						}
						
						newUserUpdationInUserInfo="UPDATE TBL_USER_INFO SET ROLE_ID = "+role.getId()+", ROLE_NAME='"+role.getName()+"', ZONE_ID="+zoneId+", REGION_ID = "+regionId+", STATE_ID="+stateId+", DISTRICT_ID=NULL, TERRITORY_ID="+territoryId+", HEAD_QUARTER_ID=NULL WHERE ID = "+newUser.getId()+"";
										
					} else if (roleId == 5) {
						
						updateReMappingQuery = "UPDATE TBL_EMPLOYEE_RETAILER_MAPPING SET MDO_ID = "+newUser.getId()+",MDO='"+newUser.getName()+"' WHERE ZONE_ID = "+zoneId+" AND STATE_ID = "+stateId+" AND REGION_ID = "+regionId+" AND TERRITORY_ID = "+territoryId+"  AND DISTRICT_ID= "+districtId+" AND HEADQUARTER_ID = "+headquarterId+"";
					
						newUserUpdationInUserInfo="UPDATE TBL_USER_INFO SET ROLE_ID = "+role.getId()+", ROLE_NAME='"+role.getName()+"', ZONE_ID="+zoneId+", REGION_ID = "+regionId+", STATE_ID="+stateId+", DISTRICT_ID="+districtId+", TERRITORY_ID="+territoryId+", HEAD_QUARTER_ID="+headquarterId+" WHERE ID = "+newUser.getId()+"";

					
					} else if (roleId == 10) {
						
						updateReMappingQuery = "UPDATE TBL_EMPLOYEE_RETAILER_MAPPING SET CUMM_ID = "+newUser.getId()+",CUMM_NAME='"+newUser.getName()+"' WHERE ZONE_ID = "+zoneId+"";
					
						newUserUpdationInUserInfo="UPDATE TBL_USER_INFO SET ROLE_ID = "+role.getId()+", ROLE_NAME='"+role.getName()+"', ZONE_ID="+zoneId+", REGION_ID = NULL, STATE_ID=NULL, DISTRICT_ID=NULL, TERRITORY_ID=NULL, HEAD_QUARTER_ID=NULL WHERE ID = "+newUser.getId()+"";

					}
					
					NativeQuery<ReMappingHistory> sqlQuery = hibernateDao.getSession().createNativeQuery(updateReMappingQuery);
					sqlQuery.executeUpdate();					
					
					NativeQuery<ReMappingHistory> userUpdationQuery = hibernateDao.getSession().createNativeQuery(newUserUpdationInUserInfo);
					userUpdationQuery.executeUpdate();
					
					if (userExistence != null) {
						saveEmployeeHistory(userExistence, oldHistoryExists, mapHistory, newUser, updatedBy, roleId,headquarterId, territoryId, regionId, zoneId, stateId, districtId);
					} else {
						saveEmployeeHistory(null, oldHistoryExists, mapHistory, newUser, updatedBy, roleId,headquarterId, territoryId, regionId, zoneId, stateId, districtId);
					}
						
						
				} else {	
					response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
					response.setMessage("Please check if the user's name or mobile number may be empty.");
				}
	
		} catch (Exception e) {
			LOG.info("==>" + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response;	
	}
	
	public void saveEmployeeHistory(UserMaster oldUser, ReMappingHistory oldHistoryExists, ReMappingHistory mapHistory, UserMaster newUser, UserMaster updatedBy, Long roleId, Long headquarterId, Long territoryId, Long regionId, Long zoneId, Long stateId, Long districtId) {
		try {
			oldHistoryExists = reMappingHistoryManager.getEmployeeExistence(newUser.getId());
//			RoleMasterEntity roleName = roleInfoManager.get(roleId);
//			ReMappingHistory duplicateRecord = reMappingHistoryManager.getEmployeeExistenceBasedOnIds(roleName.getName(),zoneId,stateId,regionId,territoryId,districtId,headquarterId);
			if (oldHistoryExists != null) {
				oldHistoryExists.setModifiedOn(DateUtils.getCurrentSystemTimestamp());
				oldHistoryExists.setStatus(false);
				reMappingHistoryManager.mergeSaveOrUpdate(oldHistoryExists);
			} 
		
			mapHistory = new ReMappingHistory();
			mapHistory.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
			mapHistory.setEmployee(newUser);
			mapHistory.setName(newUser != null ? newUser.getName() : "");
			mapHistory.setMobileNumber(newUser != null ? newUser.getMobileNumber() : "");
			mapHistory.setExistingRoleName(newUser.getRole() != null ? newUser.getRole().getName() : "");
			//mapHistory.setExistingZone(newUser.getZone() != null ? newUser.getZone().getId() : null);
			//mapHistory.setExistingState(newUser.getState() != null ? newUser.getState().getId() : null);
			//mapHistory.setExistingRegion(newUser.getRegion() != null ? newUser.getRegion().getId() : null);
			//mapHistory.setExistingTerritory(newUser.getTerritory() != null ? newUser.getTerritory().getId() : null);
			//mapHistory.setExistingDistrict(newUser.getDistrictId() != null ? newUser.getDistrictId().getId() : null);
			//mapHistory.setExistingHeadquarter(newUser.getHeadQuarter() != null ? newUser.getHeadQuarter().getId() : null);
			mapHistory.setModifiedBy(updatedBy.getId());
			
			RoleMaster updatedRole = roleMasterManager.get(roleId);
			mapHistory.setUpdatedRoleName(updatedRole != null ? updatedRole.getName() : "");
			mapHistory.setUpdatedZone(zoneId != 0 ? zoneId : null);
			mapHistory.setUpdatedState(stateId != 0 ? stateId : null);
			mapHistory.setUpdatedRegion(regionId != 0 ? regionId : null);
			mapHistory.setUpdatedTerritory(territoryId != 0 ? territoryId : null);
			mapHistory.setUpdatedDistrict(districtId != 0 ? districtId : null);
			mapHistory.setUpdatedHeadquarter(headquarterId != 0 ? headquarterId : null);
			mapHistory.setStatus(true);
			reMappingHistoryManager.mergeSaveOrUpdate(mapHistory);
			
			if (oldUser != null) {

				mapHistory.setPreviousEmployeeUserId(oldUser.getId());
				reMappingHistoryManager.mergeSaveOrUpdate(mapHistory);

//				oldUser.setZone(null);
//				oldUser.setState(null);
//				oldUser.setRegion(null);
//				oldUser.setTerritory(null);
//				oldUser.setDistrictId(null);
//				oldUser.setHeadQuarter(null);
				userInfoManager.mergeSaveOrUpdate(oldUser);
			}

		} catch (Exception e) {
			LOG.info("saveEmployeeHistory============> " + e.getStackTrace(), e);
		}
	}

	public ResponseDTO saveMaterialRequest(HttpServletRequest request, ResponseDTO response, String jsonData) {
		try {
		
			MaterialRequestDto jsonRequest = new ObjectMapper().readValue(jsonData, MaterialRequestDto.class);
			
			if(jsonRequest!=null)
			{
				MaterialRequestMaster MaterialRequest = generateRequestId();
	                LOG.info("AFTER MAX VALUE");
	                Long requestId = 0L;
	                if (MaterialRequest != null)
	                {
	                	 LOG.info("AFTER MAX VALUE"+MaterialRequest.getId());
	                	 requestId = MaterialRequest.getRequestId() + 1;
	                }
	                else requestId = 1000l;
				
				//Long requestId=materialRequestManager.getLatestRequestId();
				MaterialRequestMaster requestM=new MaterialRequestMaster();
				requestM.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
				requestM.setProjectId(jsonRequest.getProjectId()!=null?(companyProjectMasterManager.get(jsonRequest.getProjectId())):null);
				requestM.setSerialNumber(jsonRequest.getSerialNumber());
				requestM.setRequesterId(jsonRequest.getRequesterId()!=null?(userMasterManager.get(jsonRequest.getRequesterId())):null);
				requestM.setRequiredDate(Timestamp.valueOf(jsonRequest.getRequestedDate()));
				requestM.setComments(jsonRequest.getRemarks());
				requestM.setRequestStatus("Submitted");
				requestM.setStatus(true);
				requestM.setRequestId(requestId);
				materialRequestManager.mergeSaveOrUpdate(requestM);
				
				for(MaterialRequestListDto dto:jsonRequest.getMaterialRequestList())
				{
					MaterialRequestHistory history=new MaterialRequestHistory();
					history.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
					history.setMaterialId(dto.getMaterialId()!=null?(materialMasterManager.get(dto.getMaterialId())):null);
					history.setProjectId(jsonRequest.getProjectId()!=null?(companyProjectMasterManager.get(jsonRequest.getProjectId())):null);
					history.setRequestId(requestM);
					history.setRequestLocation(dto.getProductRequiredLocation());
					history.setRequiredDate(Timestamp.valueOf(dto.getRequiredDate()));
					history.setRequiredQuantity(dto.getRequiredQuantity());
					history.setSerialNmuber(jsonRequest.getSerialNumber());
					history.setStatus(true);
					history.setTotalOrderValue(dto.getTotalOrderValue());
					history.setUom(dto.getUom());
					history.setRequestStatus("Submitted");
					history.setRequiredWeight(dto.getRequiredWeight());
					materialRequestHistoryManager.mergeSaveOrUpdate(history);

				}
				
			}
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
            response.setMessage("Data Inserted Successfully");
           // response.setResponse(JSONValue.parse(resp.toString()));
		} catch (JsonMappingException e) {
			LOG.info("saveMaterialRequest JsonMappingException============> " + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		} catch (JsonProcessingException e) {
			LOG.info(" saveMaterialRequestJsonProcessingException============> " + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}

		return response;
	}
	
	  private MaterialRequestMaster generateRequestId()
	    {
	       // return ticketManager.getTicketSeqNo();
	    	try {
				long maxId = materialRequestManager.getMaxRequestId();
				if(maxId>0)
					return	materialRequestManager.get(maxId);
				else
					return null;
			} catch (Exception e) {
				LOG.info("==Exception=="+e.getStackTrace(),e);
				return null;
			}
	    }

	public String getMasters() {

		JSONObject resp = new JSONObject();

		try {
			List<MaterialMaster> materials = materialMasterManager.getAllActiveMaterials();
			List<CompanyProjectMaster> projects = companyProjectMasterManager.getAllActiveProjects();
			List<RMMaster> rmSizes = rmMasterManager.getActiveRMMasterList();
			List<SegmentMaster> segments = segmentMasterManager.getActiveSegmentMasterList();
			List<GradeMaster> grades = gradeMasterManager.getActiveGradeMasterList();
			List<ColourMaster> colours = colourMasterManager.getActiveColourMasterList();
			List<MaterialStatusMaster> status = materialStatusMasterManager.getMaterialStatusList();
			
			List<DropDownDTO> materialList = new ArrayList<>();
			if(materials!=null && materials.size() >0) {
				LOG.info("materialList===>");
				for (MaterialMaster material : materials) {
					DropDownDTO dto = new DropDownDTO();
					dto.setId(material.getId());
					dto.setName(material.getRmSize()+" ("+material.getSegment()+")");
					Double availableQuantity=inventoryMasterManager.getInventoryAvailableQunatityBasedonMaterialId(material.getId().toString());
					dto.setAvailableQuantity(availableQuantity.toString());
					dto.setUom(material.getUom());
					materialList.add(dto);
				}
			}
			
			List<DropDownDTO> projectList = new ArrayList<>();
			if(projects!=null && projects.size() >0) {
				LOG.info("projectList===>");
				for (CompanyProjectMaster project : projects) {
					DropDownDTO dto = new DropDownDTO();
					dto.setId(project.getId());
					dto.setName(project.getProjectName());
					projectList.add(dto);
				}
			}
			
			List<DropDownDTO> rmList = new ArrayList<>();
			if(rmSizes!=null && rmSizes.size() >0) {
				LOG.info("rmList===>");
				for (RMMaster rm : rmSizes) {
					DropDownDTO dto = new DropDownDTO();
					dto.setId(rm.getId());
					dto.setName(rm.getRmSize());
					rmList.add(dto);
				}
			}
			
			List<DropDownDTO> gradeList = new ArrayList<>();
			if(grades!=null && grades.size() >0) {
				LOG.info("gradeList===>");
				for (GradeMaster grade : grades) {
					DropDownDTO dto = new DropDownDTO();
					dto.setId(grade.getId());
					dto.setName(grade.getGrade());
					gradeList.add(dto);
				}
			}
			
		
			
			
			List<DropDownDTO> segmentList = new ArrayList<>();
			if(segments!=null && segments.size() >0) {
				LOG.info("segmentList===>");
				for (SegmentMaster segment : segments) {
					DropDownDTO dto = new DropDownDTO();
					dto.setId(segment.getId());
					dto.setName(segment.getSegment());
					segmentList.add(dto);
				}
			}
			
			List<DropDownDTO> colourList = new ArrayList<>();
			if(colours!=null && colours.size() >0) {
				LOG.info("colourList===>");
				for (ColourMaster colour : colours) {
					DropDownDTO dto = new DropDownDTO();
					dto.setId(colour.getId());
					dto.setName(colour.getColour());
					colourList.add(dto);
				}
			}
			
			List<DropDownDTO> materialStatusList = new ArrayList<>();
			if(status!=null && status.size() >0) {
				LOG.info("gradeList===>");
				for (MaterialStatusMaster grade : status) {
					DropDownDTO dto = new DropDownDTO();
					dto.setId(grade.getId());
					dto.setName(grade.getName());
					materialStatusList.add(dto);
				}
			}
			
			List<DropDownDTO> measurementsList = new ArrayList<>();

			DropDownDTO m1 = new DropDownDTO();
			m1.setId(1L);
			m1.setName("Kilograms");
			m1.setUom("Kg");
			m1.setAvailableQuantity("");
			measurementsList.add(m1);

			DropDownDTO m2 = new DropDownDTO();
			m2.setId(2L);
			m2.setName("Liters");
			m2.setUom("L");
			m2.setAvailableQuantity("");
			measurementsList.add(m2);

			DropDownDTO m3 = new DropDownDTO();
			m3.setId(3L);
			m3.setName("Pieces");
			m3.setUom("Nos");
			m3.setAvailableQuantity("");
			measurementsList.add(m3);

			List<DropDownDTO> productRequiredLocations = new ArrayList<>();

			DropDownDTO l1 = new DropDownDTO();
			l1.setId(1L);
			l1.setName("Hyderabad");
			l1.setUom("");
			l1.setAvailableQuantity("");
			productRequiredLocations.add(l1);

			DropDownDTO l2 = new DropDownDTO();
			l2.setId(2L);
			l2.setName("Vijayawada");
			l2.setUom("");
			l2.setAvailableQuantity("");
			productRequiredLocations.add(l2);

			DropDownDTO l3 = new DropDownDTO();
			l3.setId(3L);
			l3.setName("Nalgonda");
			l3.setUom("");
			l3.setAvailableQuantity("");
			productRequiredLocations.add(l3);

			DropDownDTO l4 = new DropDownDTO();
			l4.setId(4L);
			l4.setName("Tirupati");
			l4.setUom("");
			l4.setAvailableQuantity("");
			productRequiredLocations.add(l4);

			
			JSONObject json = new JSONObject();
			json.put("materialList", materialList);
			json.put("projectList", projectList);
			json.put("rmSizeList", rmList);
			json.put("gradeList", gradeList);
			json.put("segmentList", segmentList);
			json.put("colourList", colourList);
			json.put("materialStatusList", materialStatusList);
			json.put("productRequiredLocationsList", productRequiredLocations);
			json.put("measurementsList", measurementsList);
			
			resp.put("statusCode", appConfig.getProperty("SUCCESS_CODE"));
			resp.put("message", appConfig.getProperty("SUCCESS"));
			resp.put("response", JSONValue.parse(json.toString()));
		}catch (Exception e) {
			resp.put("statusCode", appConfig.getProperty("ERROR_CODE"));
			resp.put("message", appConfig.getProperty("OOPS_MESSAGE"));
			LOG.info(e);
		}
		return resp.toString();

	}

	

	public ResponseDTO getInventoryMaster(HttpServletRequest request, ResponseDTO response) {

		try {
			List<InventoryMaster> inventoryList=inventoryMasterManager.getAllInventoryList();
			JSONObject json = new JSONObject();
			json.put("inventoryList", inventoryList);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
			response.setMessage( appConfig.getProperty("SUCCESS"));
			response.setResponse(JSONValue.parse(json.toString()));
		}catch (Exception e) {
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			response.setResponse(null);
		}
		return response;
	}
	
	public ResponseDTO getMaterialRequest(HttpServletRequest request, ResponseDTO response) {
		try {
			LOG.info("getMaterialRequest service");

			String userId=request.getHeader("userId");
			UserMaster user=userMasterManager.get(Long.parseLong(userId));
			if(user!=null)
			{
				LOG.info("User Exist");

				List<MaterialRequestDto> materiallist=new ArrayList<>();
				List<MaterialRequestMaster> requestList=materialRequestManager.getMaterialRequestList(user);
				if(requestList!=null && requestList.size()>0)
				{
					for(MaterialRequestMaster materialRequest:requestList)
					{
						MaterialRequestDto dto=new MaterialRequestDto();
						dto.setMaterialRequestId(materialRequest.getId());
						dto.setRequestId(materialRequest.getRequestId());
						dto.setProjectId(materialRequest.getProjectId().getId());
						dto.setSerialNumber(materialRequest.getSerialNumber());
						dto.setRequesterId(materialRequest.getRequesterId().getId());
						dto.setRequesterName(materialRequest.getRequesterId().getName());
						List<MaterialRequestHistory> history=materialRequestHistoryManager.getMaterialRequestHistoryBasedonId(materialRequest);
						if(history!=null)
						{				
							List<MaterialRequestListDto> reqListDto=new ArrayList<>();

							for(MaterialRequestHistory reqHist: history)
							{
								Double issuedQuantity=issueMaterialRequestMasterManager.getIssuedQuantityBasedonMaterialRequestHistoryId(reqHist.getId());
								Double issuedWeight=issueMaterialRequestMasterManager.getIssuedWeightBasedonMaterialRequestHistoryId(reqHist.getId());

								MaterialRequestListDto reqdto=new MaterialRequestListDto();
								reqdto.setMaterialRequestHistoryId(reqHist.getId());
								reqdto.setMaterialId(reqHist.getMaterialId().getId());
								reqdto.setMaterialName(reqHist.getMaterialId().getRmSize()+" ("+reqHist.getMaterialId().getSegment()+")");
								reqdto.setProductRequiredLocation(reqHist.getRequestLocation());
								reqdto.setProjectId(reqHist.getProjectId().getId());
								reqdto.setRequiredDate(reqHist.getRequiredDate().toString());
								reqdto.setRequiredQuantity(reqHist.getRequiredQuantity());
								reqdto.setTotalOrderValue(reqHist.getTotalOrderValue());
								reqdto.setProjectName(reqHist.getProjectId().getProjectName());
								reqdto.setRequiredWeight(reqHist.getRequiredWeight());
								LOG.info("Required Weight "+reqHist.getRequiredWeight());
								if(issuedQuantity<=0)
								{
									reqdto.setRequestStatus("Pending");
								} else if(reqHist.getRequiredQuantity().compareTo(issuedQuantity)==0) {
									reqdto.setRequestStatus("Completed");
								} else {
									reqdto.setRequestStatus("Patial Completed");
								}
								//reqdto.setRequestStatus(reqHist.getRequestStatus());
								reqdto.setIssuedQuantity(issuedQuantity);
								reqdto.setIssuedWeight(issuedWeight);
								reqdto.setRequestedDate(reqHist.getCreatedOn().toString());
								reqdto.setIssuedDate(userId);
								reqListDto.add(reqdto);
							}
							dto.setMaterialRequestList(reqListDto);
						}
						materiallist.add(dto);
						
					}
					
					
				} 
				JSONObject json = new JSONObject();
				json.put("materialList", materiallist);
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
				response.setMessage( appConfig.getProperty("SUCCESS"));
				response.setResponse(JSONValue.parse(json.toString()));
				
				
			} else {
				response.setStatusCode(101);
				response.setMessage("User Doesn't Exist");
			}
		} catch (Exception e) {
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			response.setResponse(null);
			LOG.info("Exception_in_getMaterialRequest=====>" + e.getStackTrace()+"_"+e.getMessage()+"_"+e.getLocalizedMessage());
		}
		return response;
	}

	public ResponseDTO issueMaterialRequest(String jsonData) {
		ResponseDTO response=new ResponseDTO();
		try {
			MaterialRequestDto jsonRequest = new ObjectMapper().readValue(jsonData, MaterialRequestDto.class);
			if(jsonRequest!=null)
			{
				MaterialRequestMaster materialRequest=materialRequestManager.get(jsonRequest.getMaterialRequestId());
				if(materialRequest!=null)
				{
					MaterialRequestHistory materialRequestHistory=materialRequestHistoryManager.get(jsonRequest.getMaterialRequestHistoryId());
					if (materialRequestHistory != null) {
						Double issuedQuantity=issueMaterialRequestMasterManager.getIssuedQuantityBasedonMaterialRequestHistoryId(materialRequestHistory.getId());
						InventoryMaster inv=inventoryMasterManager.getInventoryListBasedonMaterialId(materialRequestHistory.getMaterialId().getId().toString());
						if(issuedQuantity<=inv.getAvailableQuantity())
						{
						IssueMaterialRequestMaster issue = new IssueMaterialRequestMaster();
						issue.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
						issue.setStatus(true);
						issue.setIssuedDate(Timestamp.valueOf(jsonRequest.getIssuedDate()));
						issue.setIssuerId(userMasterManager.get(jsonRequest.getIssuerId()));
						issue.setIssuedQuantity(jsonRequest.getIssuedQuantity());
						issue.setMaterialRequestMasterId(materialRequest);
						issue.setIssuedWeight(jsonRequest.getIssuedWeight());
						issue.setMaterialRequestHistoryId(materialRequestHistory);
						issueMaterialRequestMasterManager.mergeSaveOrUpdate(issue);
						if(inv!=null)
						{
							inv.setAvailableQuantity(inv.getAvailableQuantity()-issue.getIssuedQuantity());
							inv.setAvailableWeight(inv.getAvailableWeight()-issue.getIssuedWeight());
							inventoryMasterManager.mergeSaveOrUpdate(inv);

							if((jsonRequest.getIssuedQuantity()+issuedQuantity)==(materialRequestHistory.getRequiredQuantity()))
							{
								issue.setIssueStatus("Completed");
							} else if((jsonRequest.getIssuedQuantity()+issuedQuantity)>(materialRequestHistory.getRequiredQuantity()))
							{
								response.setStatusCode(202);
								response.setMessage("Please check Issued Quantity is More than Required Quantity.");
								return response;
							} else {
								issue.setIssueStatus("Partial Completed");
							}
							materialRequest.setRequestStatus(issue.getIssueStatus());
							materialRequest.setIssuedRemarks(jsonRequest.getIssuedRemarks());
							materialRequest.setIssuedDate(DateUtils.getCurrentSystemTimestamp());
							materialRequest.setModifiedOn(DateUtils.getCurrentSystemTimestamp());
							materialRequestManager.mergeSaveOrUpdate(materialRequest);
							issueMaterialRequestMasterManager.mergeSaveOrUpdate(issue);
						}
						response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
						response.setMessage(appConfig.getProperty("SUCCESS"));
						} else {
							response.setStatusCode(202);
							response.setMessage(materialRequestHistory.getMaterialId().getRmSize() +"Available Quantity is "+inv.getAvailableQuantity());
						}
					} else {
						response.setStatusCode(201);
						response.setMessage("Invalid Material Request History Id");
					}
				} else {
					response.setStatusCode(201);
					response.setMessage("Invalid Material Request Id");
				}
				
			} else {
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
				response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
				response.setResponse(null);
			}
			
			
		}catch (Exception e) {
			LOG.info("issueMaterialRequest Exception"+e.getStackTrace(),e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			response.setResponse(null);
		}
		return response;
	}
	
	public String getIssueMaterialRequest(HttpServletRequest request) {
		JSONObject response=new JSONObject();
		try {
			String userId=request.getHeader("userid");
			UserMaster user=userMasterManager.get(Long.parseLong(userId));
			List<MaterialRequestDto> issuedList= new ArrayList<>();
			List<MaterialRequestListDto> reqListDto=new ArrayList<>();
			List<IssueMaterialRequestMaster> issueList=null;

			if(user!=null)
			{
				if(user.getRole().getId()==1 || user.getRole().getId()==1)
					issueList=issueMaterialRequestMasterManager.getIssueMaterialList();
				else
					issueList=issueMaterialRequestMasterManager.getIssueMaterialListBasedonIssuerId(user.getId());
				for(IssueMaterialRequestMaster list:issueList)
				{
					MaterialRequestDto dto=new MaterialRequestDto();
					dto.setMaterialRequestId(list.getMaterialRequestMasterId().getId());
					dto.setRequestId(list.getMaterialRequestMasterId().getRequestId());
					dto.setProjectId(list.getMaterialRequestMasterId().getProjectId().getId());
					dto.setSerialNumber(list.getMaterialRequestMasterId().getSerialNumber());
					dto.setRequesterId(list.getMaterialRequestMasterId().getRequesterId().getId());
					dto.setRequesterName(list.getMaterialRequestMasterId().getRequesterId().getName());
					
					
					MaterialRequestListDto reqdto=new MaterialRequestListDto();
					reqdto.setMaterialRequestHistoryId(list.getMaterialRequestHistoryId().getId());
					reqdto.setMaterialId(list.getMaterialRequestHistoryId().getMaterialId().getId());
					reqdto.setMaterialName(list.getMaterialRequestHistoryId().getMaterialId().getRmSize());
					reqdto.setProductRequiredLocation(list.getMaterialRequestHistoryId().getRequestLocation());
					reqdto.setProjectId(list.getMaterialRequestHistoryId().getProjectId().getId());
					reqdto.setRequiredDate(list.getMaterialRequestHistoryId().getRequiredDate().toString());
					reqdto.setRequiredQuantity(list.getMaterialRequestHistoryId().getRequiredQuantity());
					reqdto.setTotalOrderValue(list.getMaterialRequestHistoryId().getTotalOrderValue());
					reqdto.setProjectName(list.getMaterialRequestHistoryId().getProjectId().getProjectName());
					reqdto.setIssuerName(list.getIssuerId().getName());
					reqdto.setRequestStatus(list.getIssueStatus());
					reqdto.setRequiredWeight(list.getMaterialRequestHistoryId().getRequiredWeight());
					reqdto.setIssuedQuantity(list.getIssuedQuantity());
					reqdto.setIssuedWeight(list.getIssuedWeight()!=null?list.getIssuedWeight():0.00);
					reqdto.setIssuedDate(list.getIssuedDate().toString());
					reqListDto.add(reqdto);
					dto.setMaterialRequestList(reqListDto);
					issuedList.add(dto);

				}
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("issuedList", issuedList);
				response.put("statusCode",Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
				response.put("message", appConfig.getProperty("SUCCESS"));
				response.put("response",JSONValue.parse(jsonObject.toString()));
			} else {
				
			}
		}catch (Exception e) {
			LOG.info("getIssueMaterialRequest Exception==> "+e.getStackTrace(),e);
			response.put("statusCode",Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.put("message",appConfig.getProperty("OOPS_MESSAGE"));
		}
		return response.toString();
	}

	public String getProjectMasters() {

		JSONObject resp = new JSONObject();

		try {
			List<CompanyProjectMaster> projects = companyProjectMasterManager.getProjectsList();

			JSONObject json = new JSONObject();
			json.put("projectList", projects);

			
			resp.put("statusCode", appConfig.getProperty("SUCCESS_CODE"));
			resp.put("message", appConfig.getProperty("SUCCESS"));
			resp.put("response", JSONValue.parse(json.toString()));
		}catch (Exception e) {
			resp.put("statusCode", appConfig.getProperty("ERROR_CODE"));
			resp.put("message", appConfig.getProperty("OOPS_MESSAGE"));
			LOG.info(e);
		}
		return resp.toString();
	}
	
	public String getMaterialMasters()
	{
		JSONObject resp = new JSONObject();

		try {
			List<MaterialMaster> materials = materialMasterManager.getMaterialList();
			JSONObject json = new JSONObject();
			json.put("materialList", materials);
			resp.put("statusCode", appConfig.getProperty("SUCCESS_CODE"));
			resp.put("message", appConfig.getProperty("SUCCESS"));
			resp.put("response", JSONValue.parse(json.toString()));
		}catch (Exception e) {
			resp.put("statusCode", appConfig.getProperty("ERROR_CODE"));
			resp.put("message", appConfig.getProperty("OOPS_MESSAGE"));
			LOG.info(e.getStackTrace(),e);
		}
		return resp.toString();
	}

	public String getDashboardDetailes(HttpServletRequest request) {
		JSONObject resp = new JSONObject();
		String userId=request.getHeader("userId");
		try {
			UserMaster user=userMasterManager.get(Long.parseLong(userId));

			//Double materialCount=materialMasterManager.getMaterialCount();
			//Double availableMaterialWeight=materialMasterManager.getAvailableMaterialWeight();
			//Long shortAgeMeterialCount=materialMasterManager.getShortAgeMeterialCount();
			Long materialRequestCount=materialRequestManager.getMaterialRequestCount(user.getId(),user.getRole().getId());
			Long materialPendingCount=materialRequestManager.getMaterialPendingCount(user.getId(),user.getRole().getId());
			Long materialIssuedCount=materialRequestManager.getMaterialIssuedCount(user.getId(),user.getRole().getId());
			Long materialRequestCompletedCount=materialRequestManager.getMaterialRequestCompletedCount(user.getId(),user.getRole().getId());
			Double totalWeight=inventoryMasterManager.getInventoryTotalWeight();
			Double availableWeight=inventoryMasterManager.getInventoryAvailableWeight();
			List<MaterialDto> segmentList=sqlQueryManager.getInventoryCountBasedOnSegments();

			DecimalFormat df = new DecimalFormat("#.##");
			String totalWeight1 = df.format(totalWeight);
			String availableWeight1 = df.format(availableWeight);

	        List<JSONObject> segments = new ArrayList<>();

			if(segmentList!=null)
			{
				for(MaterialDto segment:segmentList)
				{
					JSONObject segmentJson = new JSONObject();
					segmentJson.put("segment", segment.getSegment());
					segmentJson.put("totalStock", segment.getTotalWeight());
					segmentJson.put("issuedStock", segment.getTotalWeight()-segment.getAvailableWeight());
					segmentJson.put("availableStock", segment.getAvailableWeight());
					segments.add(segmentJson);

				}
			}
			JSONObject json = new JSONObject();
		
			JSONObject mqjson = new JSONObject();
			mqjson.put("icon","/assets/images/material-req-icon.png");
			mqjson.put("title","Material Requisition");
			mqjson.put("value",materialRequestCount);
			
			JSONObject ivjson = new JSONObject();
			ivjson.put("icon","/assets/images/issue_voucher.png");
			ivjson.put("title","Issued Vouchers");
			ivjson.put("value",materialRequestCompletedCount);
			
			JSONObject pcjson = new JSONObject();
			pcjson.put("icon","/assets/images/pending.png");
			pcjson.put("title","Pending Requests");
			pcjson.put("value",materialPendingCount);
			
			JSONObject crjson = new JSONObject();
			crjson.put("icon","/assets/images/closed.png");
			crjson.put("title","Patial Completed Requests");
			crjson.put("value",materialIssuedCount);
			
			JSONObject rmjson = new JSONObject();
			rmjson.put("icon","/assets/images/closed.png");
			rmjson.put("title","Raw Material");
			rmjson.put("value",totalWeight);
			
			JSONObject wipjson = new JSONObject();
			wipjson.put("icon","/assets/images/closed.png");
			wipjson.put("title","Work in Progress");
			wipjson.put("value",0);
			
			JSONObject fgwithOutPaintjson = new JSONObject();
			fgwithOutPaintjson.put("icon","/assets/images/closed.png");
			fgwithOutPaintjson.put("title","FG-Without Painting");
			fgwithOutPaintjson.put("value",0);
			
			JSONObject fgwithPaintjson = new JSONObject();
			fgwithPaintjson.put("icon","/assets/images/closed.png");
			fgwithPaintjson.put("title","FG-With painting");
			fgwithPaintjson.put("value",0);
			
			JSONObject dropJson = new JSONObject();
			dropJson.put("icon","/assets/images/closed.png");
			dropJson.put("title","Drops");
			dropJson.put("value",0);
			
			JSONObject scrapAndDispatchjson = new JSONObject();
			scrapAndDispatchjson.put("icon","/assets/images/closed.png");
			scrapAndDispatchjson.put("title","Scap and Diaptch");
			scrapAndDispatchjson.put("value",0);
			
			List<JSONObject> jsonList = new ArrayList<>();
			jsonList.add(mqjson);
			jsonList.add(ivjson);
			jsonList.add(pcjson);
			jsonList.add(crjson);
			jsonList.add(rmjson);
			jsonList.add(wipjson);
			jsonList.add(fgwithOutPaintjson);
			jsonList.add(fgwithPaintjson);
			jsonList.add(dropJson);
			jsonList.add(scrapAndDispatchjson);

			
			 JSONArray downCards = new JSONArray();

		        downCards.put(createCard(
		            "Material Requisition & Issue Management",
		            "AssignmentIcon",
		            new String[][] {{"Total Request", materialRequestCount.toString()}, {"Issued Requests", materialRequestCompletedCount.toString()}},
		            "linear-gradient(90deg, #00a86b 0%, #008f5b 100%)"
		        ));

		        downCards.put(createCard(
		            "Stock Management",
		            "InventoryIcon",
		            new String[][] {{"Total Weight", totalWeight1}, {"Issued Weight", availableWeight1}},
		            "linear-gradient(90deg, #ff3e8e 0%, #e91e63 100%)"
		        ));

//		        downCards.put(createCard(
//		            "Unit-Specific Material Allocation",
//		            "AccountTreeIcon",
//		            new String[][] {{"Accepted Requests", "16500"}, {"Pending Requests", "220"}},
//		            "linear-gradient(90deg, #006d5b 0%, #00594b 100%)"
//		        ));
//
//		        downCards.put(createCard(
//		            "Reports",
//		            "AssessmentIcon",
//		            new String[][] {{"Overall Users", "20000"}, {"Productive Users", "6000"}},
//		            "linear-gradient(90deg, #9c27b0 0%, #7b1fa2 100%)"
//		        ));
//
//		        downCards.put(createCard(
//		            "Quality Assurance Integration",
//		            "VerifiedIcon",
//		            new String[][] {{"Accepted Requests", "3850"}, {"Pending Requests", "290"}},
//		            "linear-gradient(90deg, #303f9f 0%, #1a237e 100%)"
//		        ));
		        
		        
			
			json.put("topCards",jsonList);
			json.put("downCards", downCards);
			json.put("segmentData", segments);


			resp.put("statusCode", appConfig.getProperty("SUCCESS_CODE"));
			resp.put("message", appConfig.getProperty("SUCCESS"));
			resp.put("response", JSONValue.parse(json.toString()));
		}catch (Exception e) {
			resp.put("statusCode", appConfig.getProperty("ERROR_CODE"));
			resp.put("message", appConfig.getProperty("OOPS_MESSAGE"));
			LOG.info(e.getStackTrace(),e);
		}
		return resp.toString();
	}
	
	 private static JSONObject createCard(String title, String icon, String[][] stats, String gradient) {
	        JSONObject card = new JSONObject();
	        card.put("title", title);
	        card.put("icon", icon); // icon name as string since Java doesn't handle JSX

	        JSONArray statsArray = new JSONArray();
	        for (String[] stat : stats) {
	            JSONObject statObj = new JSONObject();
	            statObj.put("label", stat[0]);
	            statObj.put("value", stat[1]);
	            statsArray.put(statObj);
	        }

	        card.put("stats", statsArray);
	        card.put("gradient", gradient);

	        return card;
	    }

	public ResponseDTO saveMaterialMaster(String jsonData) {
		ResponseDTO response=new ResponseDTO();
		//JSONObject json = new JSONObject();

		try {
			MaterialDto materialDto = new ObjectMapper().readValue(jsonData, MaterialDto.class);
			if(materialDto!=null)
			{
				MaterialMaster material=materialMasterManager.getMaterialExistBasedOnFileter(materialDto.getRmSize(),materialDto.getSegment(),materialDto.getGrade(),materialDto.getColour());
				if(material!=null) {
					response.setStatusCode(201);
					response.setMessage("Material Already Exist");
					response.setResponse(JSONValue.parse(material.toString()));
				} else {
					
					MaterialMaster Material =new MaterialMaster();
					Material.setStatus(true);
					Material.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
					Material.setSegment(materialDto.getSegment());
					Material.setRmSize(materialDto.getRmSize());
					Material.setColour(materialDto.getColour());
					Material.setGrade(materialDto.getGrade());
					Material.setMpa(materialDto.getMpa());
					Material.setSection(materialDto.getSection());
					Material.setCustomer(materialDto.getCustomer());
					Material.setLength(materialDto.getLength());
					Material.setLocation(materialDto.getLocation());
					//Material.setWeight(materialDto.getWeight());
					Material.setWidth(materialDto.getWidth());
					Material.setMaterialDescrption(materialDto.getMaterialDescrption());
					Material.setMaterialCode(materialDto.getMaterialCode());
					Material.setMaterialType(materialDto.getMaterialType());
					Material.setUom(materialDto.getUom());
					Material.setThick(materialDto.getThick());
					Material.setRemarks(materialDto.getRemarks());
					Material.setPrice(materialDto.getPrice());
					materialMasterManager.mergeSaveOrUpdate(Material);
					response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
					response.setMessage(appConfig.getProperty("SUCCESS"));
					response.setResponse(Material.toString());
				}
			} else {
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
				response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
				response.setResponse("Invalid Request");
			}			
		} catch (Exception e) {
			LOG.info("saveMaterialMaster Exception==> "+e.getStackTrace(),e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			response.setResponse(null);
		}
		return response;
	}

	public ResponseDTO addItemInInventory(String jsonData) {
		ResponseDTO response=new ResponseDTO();
		try {
			InventoryMaster inventory =null;
		MaterialDto inventoryDto = new ObjectMapper().readValue(jsonData, MaterialDto.class);
		if(inventoryDto!=null)
		{
			inventory = inventoryMasterManager.getInventoryListBasedonRMSizeAndSegmentAndColourAndGrade(
					inventoryDto.getRmSize(), inventoryDto.getSegment(), inventoryDto.getColour(),
					inventoryDto.getGrade());

			if (inventory != null) {
				inventory.setAvailableQuantity(inventory.getAvailableQuantity()+inventoryDto.getQuantity());
				inventory.setTotalQuantity(inventory.getTotalQuantity()+inventoryDto.getQuantity());
				inventory.setModifiedOn(DateUtils.getCurrentSystemTimestamp());
				inventory.setTotalWeight(inventory.getTotalWeight()+inventoryDto.getWeight());
				inventory.setAvailableWeight(inventory.getAvailableWeight()+inventoryDto.getWeight());
				inventoryMasterManager.mergeSaveOrUpdate(inventory);
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
				response.setMessage("Inventory Updated ");
			} else {
				inventory=new InventoryMaster();
				inventory.setStatus(true);
				inventory.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
				inventory.setSegment(inventoryDto.getSegment());
				inventory.setRmSize(inventoryDto.getRmSize());
				inventory.setColour(inventoryDto.getColour());
				inventory.setGrade(inventoryDto.getGrade());
				inventory.setMpa(inventoryDto.getMpa());
				inventory.setSection(inventoryDto.getSection());
				inventory.setCustomer(inventoryDto.getCustomer());
				inventory.setLength(inventoryDto.getLength());
				inventory.setLocation(inventoryDto.getLocation());
				inventory.setAvailableWeight(inventoryDto.getWeight());
				inventory.setTotalWeight(inventoryDto.getWeight());
				inventory.setWidth(inventoryDto.getWidth());
				inventory.setMaterialDescrption(inventoryDto.getMaterialDescrption());
				inventory.setMaterialCode(inventoryDto.getMaterialCode());
				inventory.setMaterialType(inventoryDto.getMaterialType());
				inventory.setUom(inventoryDto.getUom());
				inventory.setThick(inventoryDto.getThick());
				inventory.setRemarks(inventoryDto.getRemarks());
				inventory.setPrice(inventoryDto.getPrice());
				inventory.setAvailableQuantity(inventoryDto.getQuantity());
				inventory.setTotalQuantity(inventoryDto.getQuantity());
				inventory.setTotalWeight(inventory.getTotalWeight()+inventoryDto.getWeight());
				inventory.setAvailableWeight(inventory.getAvailableWeight()+inventoryDto.getWeight());
				inventoryMasterManager.mergeSaveOrUpdate(inventory);
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
				response.setMessage("Inventory Added");
			}
			
			InventoryHistory inventoryHistory=new InventoryHistory();
			inventoryHistory.setStatus(true);
			inventoryHistory.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
			inventoryHistory.setSegment(inventoryDto.getSegment());
			inventoryHistory.setRmSize(inventoryDto.getRmSize());
			inventoryHistory.setColour(inventoryDto.getColour());
			inventoryHistory.setGrade(inventoryDto.getGrade());
			inventoryHistory.setMpa(inventoryDto.getMpa());
			inventoryHistory.setSection(inventoryDto.getSection());
			inventoryHistory.setCustomer(inventoryDto.getCustomer());
			inventoryHistory.setLength(inventoryDto.getLength());
			inventoryHistory.setLocation(inventoryDto.getLocation());
			inventoryHistory.setWeight(inventoryDto.getWeight());
			inventoryHistory.setWidth(inventoryDto.getWidth());
			inventoryHistory.setMaterialDescrption(inventoryDto.getMaterialDescrption());
			inventoryHistory.setMaterialCode(inventoryDto.getMaterialCode());
			inventoryHistory.setMaterialType(inventoryDto.getMaterialType());
			inventoryHistory.setUom(inventoryDto.getUom());
			inventoryHistory.setThick(inventoryDto.getThick());
			inventoryHistory.setRemarks(inventoryDto.getRemarks());
			inventoryHistory.setPrice(inventoryDto.getPrice());
			inventoryHistory.setAvailableQuantity(inventoryDto.getQuantity());
			inventoryHistory.setTotalQuantity(inventoryDto.getQuantity());
			inventoryHistory.setInventoryMasterId(inventory);
			MaterialMaster material = materialMasterManager.getMaterialExistBasedOnFileter(inventoryDto.getRmSize(),
					inventoryDto.getSegment(), inventoryDto.getGrade(), inventoryDto.getColour());
			if(material!=null)
			{
				inventoryHistory.setMaterialId(material.getId().toString());
			}

			inventoryHistoryManager.mergeSaveOrUpdate(inventoryHistory);

		}
		} catch (Exception e) {
			LOG.info("addItemInInventory Exception==> "+e.getStackTrace(),e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			response.setResponse(null);
		}
		return response;
	}

	public ResponseDTO editInventoryMaster(HttpServletRequest request, String jsonData, ResponseDTO response) {

		try {
			InventoryMaster inventory =null;
		MaterialDto inventoryDto = new ObjectMapper().readValue(jsonData, MaterialDto.class);
		if(inventoryDto!=null)
		{
			inventory = inventoryMasterManager.get(inventoryDto.getInventoryMasterId());

			if (inventory != null) {
				inventory.setAvailableQuantity(inventory.getAvailableQuantity()+inventoryDto.getQuantity());
				inventory.setTotalQuantity(inventory.getTotalQuantity()+inventoryDto.getQuantity());
				inventory.setTotalWeight(inventory.getTotalWeight()+inventoryDto.getWeight());
				inventory.setAvailableWeight(inventory.getAvailableWeight()+inventoryDto.getWeight());
				inventory.setModifiedOn(DateUtils.getCurrentSystemTimestamp());
				inventoryMasterManager.mergeSaveOrUpdate(inventory);
				

				InventoryHistory inventoryHistory=new InventoryHistory();
				inventoryHistory.setStatus(true);
				inventoryHistory.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
				inventoryHistory.setSegment(inventory.getSegment());
				inventoryHistory.setRmSize(inventory.getRmSize());
				inventoryHistory.setColour(inventory.getColour());
				inventoryHistory.setGrade(inventory.getGrade());
				inventoryHistory.setMpa(inventory.getMpa());
				inventoryHistory.setSection(inventory.getSection());
				inventoryHistory.setCustomer(inventory.getCustomer());
				inventoryHistory.setLength(inventory.getLength());
				inventoryHistory.setLocation(inventory.getLocation());
				inventoryHistory.setWeight(inventoryDto.getWeight());
				inventoryHistory.setWidth(inventory.getWidth());
				inventoryHistory.setMaterialDescrption(inventory.getMaterialDescrption());
				inventoryHistory.setMaterialCode(inventory.getMaterialCode());
				inventoryHistory.setMaterialType(inventory.getMaterialType());
				inventoryHistory.setUom(inventory.getUom());
				inventoryHistory.setThick(inventory.getThick());
				inventoryHistory.setRemarks(inventory.getRemarks());
				inventoryHistory.setPrice(inventory.getPrice());
				inventoryHistory.setAvailableQuantity(inventoryDto.getQuantity());
				inventoryHistory.setTotalQuantity(inventoryDto.getQuantity());
				inventoryHistory.setInventoryMasterId(inventory);
				MaterialMaster material = materialMasterManager.getMaterialExistBasedOnFileter(inventoryDto.getRmSize(),
						inventoryDto.getSegment(), inventoryDto.getGrade(), inventoryDto.getColour());
				if(material!=null)
				{
					inventoryHistory.setMaterialId(material.getId().toString());
				}

				inventoryHistoryManager.mergeSaveOrUpdate(inventoryHistory);
				
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
				response.setMessage( appConfig.getProperty("SUCCESS"));
				ObjectMapper obj=new  ObjectMapper();
				String jsonResponse = obj.writeValueAsString(inventory);

				response.setResponse(JSONValue.parse(jsonResponse));
			} else {
				response.setStatusCode(201);
				response.setMessage("Invalid Inventory Id");
				response.setResponse(null);
			}
			
			
		}
		} catch (Exception e) {
			LOG.info("addItemInInventory Exception==> "+e.getStackTrace(),e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			response.setResponse(null);
		}
		return response;
	
	}

	public ResponseDTO getReports(HttpServletRequest request, ResponseDTO response) {

		try {
			List<MaterialMaster> materials=materialMasterManager.getAllActiveMaterials();
			ArrayList<MaterialDto> materialList=new ArrayList<>();

			if(materials!=null && materials.size()>0)
			{
				for(MaterialMaster material:materials)
				{
					MaterialDto dto=new MaterialDto();
					dto.setSegment(material.getSegment());
					dto.setGrade(material.getGrade());
					dto.setMaterialName(material.getRmSize());
					InventoryMaster inv=inventoryMasterManager.getInventoryListBasedonMaterialId(material.getId().toString());
					if(inv!=null)
					{
						dto.setTotalQuantity(inv.getTotalQuantity());
						dto.setAvailableQuantity(inv.getAvailableQuantity());
						dto.setTotalWeight(inv.getTotalWeight());
						dto.setAvailableWeight(inv.getAvailableWeight());
						dto.setIssuedQuantity(inv.getTotalQuantity()-inv.getAvailableQuantity());
						dto.setIssuedWeight(inv.getTotalWeight()-inv.getAvailableWeight());
						materialList.add(dto);
					}
					

				}
			}
			JSONObject json = new JSONObject();
			json.put("data", materialList);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
			response.setMessage( appConfig.getProperty("SUCCESS"));
			response.setResponse(JSONValue.parse(json.toString()));
		} catch (Exception e) {
			LOG.info("getReports Exception==> "+e.getStackTrace(),e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			response.setResponse(null);
		}
		return response;
	}

	public ResponseDTO getBillOfMaterialList(HttpServletRequest request, ResponseDTO response) {
		// TODO Auto-generated method stub
		try {
			List<BillOfMaterialMaster> billOfMaterialList=billOfMaterialMasterManager.getBillOfMaterialList();
			ArrayList<BOMDto> bomList=new ArrayList<>();
			if(billOfMaterialList!=null && billOfMaterialList.size()>0)
			{
				for(BillOfMaterialMaster bom:billOfMaterialList)
				{
					BOMDto dto= new BOMDto();
					dto.setProject(bom.getProject());
					dto.setJobId(bom.getJobId());
					dto.setAbAndTemplate(bom.getAbAndTemplate());
					dto.setBuildUp(bom.getBuildUp());
					dto.setColdForm(bom.getColdForm());
					dto.setHotRolled(bom.getHotRolled());
					dto.setMisc(bom.getMisc());
					dto.setSsrSheeting(bom.getSsrSheeting());
					dto.setKrRoofSheeting(bom.getKrRoofSheeting());
					dto.setKrWallSheeting(bom.getKrWallSheeting());
					dto.setFlashings(bom.getFlashings());
					dto.setDeckSheet(bom.getDeckSheet());
					dto.setBuyOuts(bom.getBuyOuts());
					dto.setTotalWeightInMT(bom.getTotalWeightInMT());
					dto.setPuffPanelArea(bom.getPuffPanelArea());
					dto.setRedSheetSurfaceArea(bom.getRedSheetSurfaceArea());
					bomList.add(dto);
				}
			}
			JSONObject json = new JSONObject();
			json.put("data", bomList);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
			response.setMessage( appConfig.getProperty("SUCCESS"));
			response.setResponse(JSONValue.parse(json.toString()));
		} catch (Exception e) {
			LOG.info("getBillOfMaterialList Exception==> "+e.getStackTrace(),e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			response.setResponse(null);
		}
		return response;
	}
	
	public ResponseDTO saveMaterialRequestForMobile(HttpServletRequest request, ResponseDTO response, String jsonData) {
		try {
		
			MaterialRequestDto jsonRequest = new ObjectMapper().readValue(jsonData, MaterialRequestDto.class);
			
			if(jsonRequest!=null)
			{
				MaterialRequestMaster MaterialRequest = generateRequestId();
	                LOG.info("AFTER MAX VALUE");
	                Long requestId = 0L;
	                if (MaterialRequest != null)
	                {
	                	 LOG.info("AFTER MAX VALUE"+MaterialRequest.getId());
	                	 requestId = MaterialRequest.getRequestId() + 1;
	                }
	                else requestId = 1000l;
				
				//Long requestId=materialRequestManager.getLatestRequestId();
				MaterialRequestMaster requestM=new MaterialRequestMaster();
				requestM.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
				requestM.setProjectId(jsonRequest.getProjectId()!=null?(companyProjectMasterManager.get(jsonRequest.getProjectId())):null);
				requestM.setProjectName(jsonRequest.getProjectName());
				requestM.setSerialNumber(jsonRequest.getSerialNumber());
				requestM.setRequesterId(jsonRequest.getRequesterId()!=null?(userMasterManager.get(jsonRequest.getRequesterId())):null);
				requestM.setRequiredDate(Timestamp.valueOf(jsonRequest.getRequestedDate()));
				requestM.setComments(jsonRequest.getRemarks());
				requestM.setTotalOrderValue(jsonRequest.getTotalOrderValue());
				requestM.setProductRequiredLocationId(jsonRequest.getProductRequiredLocationId()!=null?jsonRequest.getProductRequiredLocationId():0);
				requestM.setProductRequiredLocationName(jsonRequest.getProductRequiredLocationName());
				requestM.setRequestStatus("Submitted");
				requestM.setStatus(true);
				requestM.setRequestId(requestId);
				requestM.setRequesterRemarks(jsonRequest.getRequesterRemarks());
				materialRequestManager.mergeSaveOrUpdate(requestM);
				
				for(MaterialRequestListDto dto:jsonRequest.getMaterialRequestList())
				{
					MaterialRequestHistory history=new MaterialRequestHistory();
					history.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
					history.setMaterialId(dto.getMaterialId()!=null?(materialMasterManager.get(dto.getMaterialId())):null);
					history.setProjectId(jsonRequest.getProjectId()!=null?(companyProjectMasterManager.get(jsonRequest.getProjectId())):null);
					history.setRequestId(requestM);
					history.setRequestLocation(jsonRequest.getProductRequiredLocationName());
					history.setRequiredDate(Timestamp.valueOf(jsonRequest.getRequiredDate()));
					history.setRequiredQuantity(dto.getRequiredQuantity());
					history.setSerialNmuber(jsonRequest.getSerialNumber());
					history.setStatus(true);
					history.setTotalOrderValue(jsonRequest.getTotalOrderValue());
					history.setUom(dto.getUom());
					history.setRequestStatus("Submitted");
					history.setRequiredWeight(dto.getRequiredWeight());
					materialRequestHistoryManager.mergeSaveOrUpdate(history);

				}
				
			}
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
            response.setMessage("Data Inserted Successfully");
           // response.setResponse(JSONValue.parse(resp.toString()));
		} catch (JsonMappingException e) {
			LOG.info("saveMaterialRequest JsonMappingException============> " + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		} catch (JsonProcessingException e) {
			LOG.info(" saveMaterialRequestJsonProcessingException============> " + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
		}

		return response;
	}

//	public ResponseDTO getMaterialRequestForMobile(HttpServletRequest request, ResponseDTO response, String jsonData) {
//		try {
//			LOG.info("getMaterialRequest service");
//			String userId = request.getHeader("userId");
//			
//			JSONObject jsonRequest = new JSONObject(jsonData);
//			Long projectId = jsonRequest.optLong("projectId",0l);
//			String moduleType  = jsonRequest.getString("moduleType");
//
//			UserMaster user = userMasterManager.get(Long.parseLong(userId));
//			if (user != null) {
//				LOG.info("User Exist");
//
//				List<MaterialRequestDto> materiallist = new ArrayList<>();
//				List<MaterialRequestMaster> requestList = materialRequestManager.getMaterialRequestListForMobile(user,projectId);
//				if (requestList != null && requestList.size() > 0) {
//					for (MaterialRequestMaster materialRequest : requestList) {
//						MaterialRequestDto dto = new MaterialRequestDto();
//						dto.setMaterialRequestId(materialRequest.getId());
//						dto.setRequestId(materialRequest.getRequestId());
//						dto.setProjectId(materialRequest.getProjectId().getId());
//						dto.setSerialNumber(materialRequest.getSerialNumber());
//						dto.setRequesterId(materialRequest.getRequesterId().getId());
//						dto.setRequesterName(materialRequest.getRequesterId().getName());
//						List<MaterialRequestHistory> history = materialRequestHistoryManager.getMaterialRequestHistoryBasedonId(materialRequest);
//						if (history != null) {
//							List<MaterialRequestListDto> reqListDto = new ArrayList<>();
//
//							for (MaterialRequestHistory reqHist : history) {
//								Double issuedQuantity = issueMaterialRequestMasterManager.getIssuedQuantityBasedonMaterialRequestHistoryId(reqHist.getId());
//								Double issuedWeight = issueMaterialRequestMasterManager.getIssuedWeightBasedonMaterialRequestHistoryId(reqHist.getId());
//
//								MaterialRequestListDto reqdto = new MaterialRequestListDto();
//								reqdto.setMaterialRequestHistoryId(reqHist.getId());
//								reqdto.setMaterialId(reqHist.getMaterialId().getId());
//								reqdto.setMaterialName(reqHist.getMaterialId().getRmSize() + " ("+ reqHist.getMaterialId().getSegment() + ")");
//								reqdto.setProductRequiredLocation(reqHist.getRequestLocation());
//								reqdto.setProjectId(reqHist.getProjectId().getId());
//								reqdto.setRequiredDate(reqHist.getRequiredDate().toString());
//								reqdto.setRequiredQuantity(reqHist.getRequiredQuantity());
//								reqdto.setTotalOrderValue(reqHist.getTotalOrderValue());
//								reqdto.setProjectName(reqHist.getProjectId().getProjectName());
//								reqdto.setRequiredWeight(reqHist.getRequiredWeight());
//								LOG.info("Required Weight " + reqHist.getRequiredWeight());
//								if (issuedQuantity <= 0) {
//									reqdto.setRequestStatus("Pending");
//								} else if (reqHist.getRequiredQuantity().compareTo(issuedQuantity) == 0) {
//									reqdto.setRequestStatus("Completed");
//								} else {
//									reqdto.setRequestStatus("Patial Completed");
//								}
//								reqdto.setIssuedQuantity(issuedQuantity);
//								reqdto.setIssuedWeight(issuedWeight);
//								reqdto.setRequestedDate(reqHist.getCreatedOn().toString());
//								reqdto.setIssuedDate(userId);
//								reqListDto.add(reqdto);
//							}
//							dto.setMaterialRequestList(reqListDto);
//						}
//						materiallist.add(dto);
//					}
//				}
//				JSONObject json = new JSONObject();
//				json.put("materialList", materiallist);
//				response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
//				response.setMessage(appConfig.getProperty("SUCCESS"));
//				response.setResponse(JSONValue.parse(json.toString()));
//			} else {
//				response.setStatusCode(101);
//				response.setMessage("User Doesn't Exist");
//			}
//		} catch (Exception e) {
//			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
//			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
//			response.setResponse(null);
//			LOG.info("Exception_in_getMaterialRequestForMobile=====>" + e.getStackTrace() + "_" + e.getMessage() + "_"+ e.getLocalizedMessage());
//		}
//		return response;
//	}
	
	public ResponseDTO getMaterialRequestForMobile_v2(HttpServletRequest request, ResponseDTO response, String jsonData) {
	    try {
	        LOG.info("getMaterialRequest service");
	        String userId = request.getHeader("userId");

	        JSONObject jsonRequest = new JSONObject(jsonData);
	        Long projectId = jsonRequest.optLong("projectId", 0L);
	        String moduleType = jsonRequest.getString("moduleType");

	        UserMaster user = userMasterManager.get(Long.parseLong(userId));
	        if (user != null) {
	            LOG.info("User Exist");

	            List<JSONObject> responseList = new ArrayList<>();
	            List<MaterialRequestMaster> requestList = materialRequestManager.getMaterialRequestListForMobile(user, projectId);

	            if (requestList != null && !requestList.isEmpty()) {
	                for (MaterialRequestMaster materialRequest : requestList) {

	                    JSONObject reqJson = new JSONObject();
	                    reqJson.put("materialRequestId", materialRequest.getId());
	                    reqJson.put("projectId", materialRequest.getProjectId().getId());
	                    reqJson.put("projectName", materialRequest.getProjectId().getProjectName());
	                    reqJson.put("serialNumber", materialRequest.getSerialNumber());
	                    reqJson.put("requesterId", materialRequest.getRequesterId().getId());
	                    reqJson.put("requesterName", materialRequest.getRequesterId().getName());
	                    reqJson.put("requestedDate", materialRequest.getCreatedOn().toString());

	                    List<MaterialRequestHistory> history = materialRequestHistoryManager.getMaterialRequestHistoryBasedonId(materialRequest);
	                    List<JSONObject> reqListDto = new ArrayList<>();
	                    Double totalOrderValue = 0.0;

	                    if (history != null) {
	                        for (MaterialRequestHistory reqHist : history) {
	                            Double issuedQuantity = issueMaterialRequestMasterManager.getIssuedQuantityBasedonMaterialRequestHistoryId(reqHist.getId());
	                            Double issuedWeight = issueMaterialRequestMasterManager.getIssuedWeightBasedonMaterialRequestHistoryId(reqHist.getId());

	                            JSONObject reqdto = new JSONObject();
	                            reqdto.put("materialRequestHistoryId", reqHist.getId());
	                            reqdto.put("materialId", reqHist.getMaterialId().getId());
	                            reqdto.put("materialName", reqHist.getMaterialId().getRmSize() + " (" + reqHist.getMaterialId().getSegment() + ")");
	                            reqdto.put("requiredQuantity", reqHist.getRequiredQuantity());
	                            reqdto.put("requiredWeight", reqHist.getRequiredWeight());
	                            reqdto.put("uom", reqHist.getMaterialId().getUom());
	                            reqListDto.add(reqdto);

	                            totalOrderValue += reqHist.getTotalOrderValue();

	                            // top-level fields (example, you may refine)
	                            reqJson.put("productRequiredLocation", reqHist.getRequestLocation());
	                            reqJson.put("requiredDate", reqHist.getRequiredDate().toString());
	                            reqJson.put("issuedQuantity", issuedQuantity);
	                            reqJson.put("issuedWeight", issuedWeight);

	                            if (issuedQuantity <= 0) {
	                                reqJson.put("status", "Pending");
	                            } else if (reqHist.getRequiredQuantity().compareTo(issuedQuantity) == 0) {
	                                reqJson.put("status", "Completed");
	                            } else {
	                                reqJson.put("status", "Partial Completed");
	                            }
	                            reqJson.put("issuedDate", materialRequest.getIssuedDate());
	                            reqJson.put("issuedRemarks", materialRequest.getIssuedRemarks());
	                            reqJson.put("requesterRemarks", materialRequest.getRequesterRemarks());
	                        }
	                    }
	                    reqJson.put("materialRequestList", reqListDto);
	                    reqJson.put("totalOrderValue", totalOrderValue);
	                    responseList.add(reqJson);
	                }
	            }
	            response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
	            response.setMessage(appConfig.getProperty("SUCCESS"));
	            response.setResponse(JSONValue.parse(responseList.toString()));
	        } else {
	            response.setStatusCode(101);
	            response.setMessage("User Doesn't Exist");
	        }
	    } catch (Exception e) {
	        response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
	        response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
	        response.setResponse(null);
	        LOG.info("Exception_in_getMaterialRequestForMobile=====>" + e.getMessage(), e);
	    }
	    return response;
	}

	public ResponseDTO issueMaterialRequestForMobile(String jsonData) {
		ResponseDTO response = new ResponseDTO();
		try {
			MaterialRequestDto jsonRequest = new ObjectMapper().readValue(jsonData, MaterialRequestDto.class);
			if (jsonRequest != null) {
				MaterialRequestMaster materialRequest = materialRequestManager.get(jsonRequest.getMaterialRequestId());
				if (materialRequest != null) {
					MaterialRequestHistory materialRequestHistory = materialRequestHistoryManager.get(jsonRequest.getMaterialRequestHistoryId());
					if (materialRequestHistory != null) {
						Double issuedQuantity = issueMaterialRequestMasterManager.getIssuedQuantityBasedonMaterialRequestHistoryId(materialRequestHistory.getId());
						InventoryMaster inv = inventoryMasterManager.getInventoryListBasedonMaterialId(materialRequestHistory.getMaterialId().getId().toString());
						if (issuedQuantity <= inv.getAvailableQuantity()) {
							IssueMaterialRequestMaster issue = new IssueMaterialRequestMaster();
							issue.setCreatedOn(DateUtils.getCurrentSystemTimestamp());
							issue.setStatus(true);
							issue.setIssuedDate(Timestamp.valueOf(jsonRequest.getIssuedDate()));
							issue.setIssuerId(userMasterManager.get(jsonRequest.getIssuerId()));
							issue.setIssuedQuantity(jsonRequest.getIssuedQuantity());
							issue.setMaterialRequestMasterId(materialRequest);
							issue.setIssuedWeight(jsonRequest.getIssuedWeight());
							issue.setMaterialRequestHistoryId(materialRequestHistory);
							issueMaterialRequestMasterManager.mergeSaveOrUpdate(issue);
							if (inv != null) {
								inv.setAvailableQuantity(inv.getAvailableQuantity() - issue.getIssuedQuantity());
								inv.setAvailableWeight(inv.getAvailableWeight() - issue.getIssuedWeight());
								inventoryMasterManager.mergeSaveOrUpdate(inv);

								if ((jsonRequest.getIssuedQuantity() + issuedQuantity) == (materialRequestHistory.getRequiredQuantity())) {
									issue.setIssueStatus("Completed");
								} else if ((jsonRequest.getIssuedQuantity() + issuedQuantity) > (materialRequestHistory
										.getRequiredQuantity())) {
									response.setStatusCode(202);
									response.setMessage("Please check Issued Quantity is More than Required Quantity.");
									return response;
								} else {
									issue.setIssueStatus("Partial Completed");
								}
								materialRequest.setRequestStatus(issue.getIssueStatus());
								materialRequest.setIssuedRemarks(jsonRequest.getIssuedRemarks());
								materialRequest.setIssuedDate(DateUtils.getCurrentSystemTimestamp());
								materialRequest.setModifiedOn(DateUtils.getCurrentSystemTimestamp());
								materialRequestManager.mergeSaveOrUpdate(materialRequest);
								issueMaterialRequestMasterManager.mergeSaveOrUpdate(issue);
							}
							response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
							response.setMessage(appConfig.getProperty("SUCCESS"));
						} else {
							response.setStatusCode(202);
							response.setMessage(materialRequestHistory.getMaterialId().getRmSize()+ "Available Quantity is " + inv.getAvailableQuantity());
						}
					} else {
						response.setStatusCode(201);
						response.setMessage("Invalid Material Request History Id");
					}
				} else {
					response.setStatusCode(201);
					response.setMessage("Invalid Material Request Id");
				}
			} else {
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
				response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
				response.setResponse(null);
			}
		} catch (Exception e) {
			LOG.info("issueMaterialRequest Exception" + e.getStackTrace(), e);
			response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
			response.setMessage(appConfig.getProperty("OOPS_MESSAGE"));
			response.setResponse(null);
		}
		return response;
	}

}