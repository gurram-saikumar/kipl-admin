package com.kipl.webService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kipl.common.HibernateDao;
import com.kipl.dto.ResponseDTO;
import com.kipl.dto.SubMenuControllerDTO;
import com.kipl.dto.UserInfoDto;
import com.kipl.manager.MenuMasterManager;
import com.kipl.manager.SqlQueryManager;
import com.kipl.manager.SubMenuMasterManager;
import com.kipl.manager.UserMasterManager;
import com.kipl.manager.UserMenuMappingManager;
import com.kipl.models.MenuMaster;
import com.kipl.models.SubMenuMaster;
import com.kipl.models.UserMaster;
import com.kipl.models.UserMenuMappingEntity;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MobileService {

	private static final Logger LOG = LogManager.getLogger(MobileService.class);

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
	private UserMenuMappingManager userMenuMappingManager;

	@Autowired
	private MenuMasterManager menuMasterManager;

	@Autowired
	private SubMenuMasterManager subMenuMasterManager;

	public ResponseDTO mobileValidateLogin(HttpServletRequest request, String jsonData) {

		ResponseDTO response = new ResponseDTO();
		try {
			JSONObject json = new JSONObject(jsonData);
			String mobileNumber = json.getString("mobileNumber");
			String passCode = json.getString("passCode");

			if (mobileNumber.isEmpty() || passCode.isEmpty()) {
				response.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
				response.setMessage(appConfig.getProperty("REQUIRED_FIELDS"));
				return response;
			}

			UserMaster userPresence = userInfoManager.getUserbyMobileNumber(mobileNumber);

			if (userPresence != null) {

				String passCodeExist = userPresence.getPasscode();

				if (passCode.equalsIgnoreCase(passCodeExist)) {
					UserMaster jsonRequest = userInfoManager.getUserDetailsBasedOnMobileNumberAndPasscode(mobileNumber, passCode);
					//List<SubMenuControllerDTO> userMenuControls = new ArrayList<>();

					UserInfoDto userInfo = new UserInfoDto();
					userInfo.setEmployeeId(jsonRequest.getEmployeeId() != null ? jsonRequest.getEmployeeId() : "");
					userInfo.setName(jsonRequest.getName() != null ? jsonRequest.getName() : "");
					userInfo.setCountryName(jsonRequest.getCountryName() != null ? jsonRequest.getCountryName() : "");
					userInfo.setCreatedOn(jsonRequest.getCreatedOn());
					userInfo.setDeviceId(jsonRequest.getDeviceId() != null ? jsonRequest.getDeviceId() : "");
					userInfo.setDeviceToken(jsonRequest.getDeviceToken() != null ? jsonRequest.getDeviceToken() : "");
					userInfo.setDeviceType(jsonRequest.getDeviceType() != null ? jsonRequest.getDeviceType() : "");
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
					userInfo.setStateName(jsonRequest.getStateName() != null ? jsonRequest.getStateName() : "");
					userInfo.setStatus(jsonRequest.getStatus());
					userInfo.setDateOfBirth(jsonRequest.getDateOfBirth());
					userInfo.setId(jsonRequest.getId());
					userInfo.setModifiedOn(jsonRequest.getModifiedOn());
					userInfo.setAddressLine(jsonRequest.getAddress() != null ? jsonRequest.getAddress() : "");
					userInfo.setCity(jsonRequest.getCity() != null ? jsonRequest.getCity() : "");
					userInfo.setLandMark(jsonRequest.getLandMark() != null ? jsonRequest.getLandMark() : "");
					userInfo.setAlternativeMobileNumber(
							jsonRequest.getAlternativeMobileNumber() != null ? jsonRequest.getAlternativeMobileNumber()
									: "");
					
					JSONObject jsonResponse = new JSONObject();

					JSONArray menuList = new JSONArray();

					List<MenuMaster> menuMasterList = menuMasterManager.getMenuListbasedOnType("Mobile");

					if (menuMasterList != null) {
						for (MenuMaster menuMaster : menuMasterList) {
							UserMenuMappingEntity menuData = userMenuMappingManager.getUserMenuByRoleIdAndMainMenuId(userInfo.getRoleId(),menuMaster.getId());
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
												.getUserMenuByRoleIdAndSubMenuId(userInfo.getRoleId(), subMenuMaster.getId());
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
					ObjectMapper obj=new  ObjectMapper();
					String user = obj.writeValueAsString(userInfo);
					jsonResponse.put("userMenuControl",menuList);
					jsonResponse.put("userInfo",JSONValue.parse(user.toString()));


					/*List<UserMenuMappingEntity> menuList = userMenuMappingManager
							.getUserMenuByRoleIdAndStateId(userInfo.getRoleId(), "Mobile");
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
					userInfo.setUserMenuControl(userMenuControls);*/
					response.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
					response.setMessage(appConfig.getProperty("SUCCESS"));
					response.setResponse(JSONValue.parse(jsonResponse.toString()));
				} else {
					response.setStatusCode(Integer.parseInt(appConfig.getProperty("NOT_EXISTS_CODE")));
					response.setMessage("Please enter the Valid PassCode.");
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

	public ResponseDTO forgetPasscode(HttpServletRequest request, ResponseDTO resp, String jsonData) {
		try {
			String mailBody = "";
			UserMaster userInfo = null;
			JSONObject jsonRequest = new JSONObject(jsonData);
			LOG.info("Json Data================>" + jsonData);
			String mobileNumber = jsonRequest.getString("mobileNumber");
			String oldPassCode = jsonRequest.getString("oldPassCode");
			String newPassCode = jsonRequest.getString("newPassCode");


			if (mobileNumber.isEmpty()) {
				resp.setStatusCode(Integer.parseInt(appConfig.getProperty("ERROR_CODE")));
				resp.setMessage(appConfig.getProperty("REQUIRED_FIELDS"));
				return resp;
			}

			userInfo = userInfoManager.getUserbyMobileNumber(mobileNumber);

			if (userInfo != null) {

				if(oldPassCode.equalsIgnoreCase(userInfo.getPasscode()))
				{
					userInfo.setPasscode(newPassCode);
					userInfoManager.mergeSaveOrUpdate(userInfo);
					resp.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
					resp.setMessage("Passcode updated Successfully");
				} else {
					resp.setStatusCode(Integer.parseInt(appConfig.getProperty("SUCCESS_CODE")));
					resp.setMessage("Please Enter Correct Passcode.");
				}
				

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
}
