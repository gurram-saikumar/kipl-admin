package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.UserMaster;

@Repository
public class UserMasterManager extends GenericManager<UserMaster, Long> {

	private static final Logger LOG = LogManager.getLogger(UserMasterManager.class);

	public UserMasterManager() {
		super(UserMaster.class);
	}

	@SuppressWarnings("unchecked")
	public List<UserMaster> getAllUserEntities(int page, int itemsPerPage, String filterString) {
		List<UserMaster> list = new ArrayList<UserMaster>();
		try {
			String condition = "";
			int startIndex = 0;
			if (filterString != null && !filterString.isEmpty()) {
				condition += " AND (name like '%" + filterString + "%')";
			}
			startIndex = (page - 1) * itemsPerPage;
			list = find("from UserMaster where 1=1 " + condition.toString() + " order by id desc limit "+ itemsPerPage + " offset " + startIndex + "");
		} catch (Exception e) {
			LOG.info("Exception_in_getAllUsers=======================> " + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}	
	
	@SuppressWarnings("unchecked")
	public List<UserMaster> getAllUsers(int page, String reqType, int itemsPerPage, String filterString) {
		List<UserMaster> list = new ArrayList<UserMaster>();
		try {
		    int startIndex = (page - 1) * itemsPerPage;
		    String filterCondition = "";
		    if (filterString != null && !filterString.isEmpty()) {
		   
		    	filterCondition = " AND (name like '%"+filterString+"%' or mobileNumber like '%"+filterString+"%'  \r\n"
	        			+ "or emailId like '%"+filterString+"%' or gender like '%"+filterString+"%' or stateName like '%"+filterString+"%'  \r\n"
	        			+ "or districtName like '%"+filterString+"%' or employeeId like '%"+filterString+"%' or address like '%"+filterString+"%'  \r\n"
	        			+ "or village like '%"+filterString+"%' or block like '%"+filterString+"%' or pinCode like '%"+filterString+"%'  \r\n"
	        			+ "or landMark like '%"+filterString+"%'  or alternativeMobileNumber like '%"+filterString+"%' or role.name like '%"+filterString+"%')";
		    }
	        String sql = "FROM UserMaster WHERE 1=1 " + filterCondition + " ORDER BY id DESC limit "+itemsPerPage + " OFFSET " + startIndex ;
			list = find(sql);
		} catch (Exception e) {
			LOG.info("Exception getAllUsersForExcelReport=======================> " + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public UserMaster findUserEntityByMobileNumber(String mobileNumber,  String password, Long id) {
		try {
			StringBuilder sb = new StringBuilder();
			if (mobileNumber != null && !mobileNumber.equals(""))
				sb.append(" and mobileNumber = '" + mobileNumber + "' ");
			if (password != null && !password.equals(""))
				sb.append(" and password = '" + password + "'");
			if ( id != null && id > 0)
				sb.append(" and id = " + id + "");

			String sql="from UserMaster where 1=1 "+sb.toString()+"";
			List<UserMaster> userDetails = find(sql);
			return userDetails != null ? (userDetails.size() > 0 ? userDetails.get(0) : null) : null;
		} catch (Exception e) {
			LOG.info("Exception in findUserEntityByMobileNumber========>"+ e.getStackTrace(), e);
			return null;
		}
	}
	
	public UserMaster getPasswordByUserId(String userID) {
		String sql="from UserMaster where loginId = '" + userID + "'";
		LOG.info("getPasswordByUserId sql===>"+sql);
		return (UserMaster) findObject(sql);	
	}
	
	public UserMaster getUserDetailsBasedOnMobileNumberAndPasscode(String mobileNumber, String passCode) {
		String sql="from UserMaster where status=true and mobileNumber = '" + mobileNumber + "' and passcode='"+passCode+"'";
		LOG.info("getUserDetailsBasedOnMobileNumberAndPasscode sql===>"+sql);
		return (UserMaster) findObject(sql);	
	}
	
	@SuppressWarnings("unchecked")
	public UserMaster findActiveUserByMobileNumberAndExcludingId(String mobileNumber, Long id) {
		try {
			StringBuilder sb = new StringBuilder();
			if (mobileNumber != null && !mobileNumber.equals(""))
				sb.append(" and mobileNumber = '" + mobileNumber + "' ");
			if ( id != null && id > 0)
				sb.append(" and  id <> " + id + "");

			String sql = "FROM UserMaster where 1=1 "+sb.toString()+"";
			List<UserMaster> userDetails = find(sql);
			return userDetails != null ? (userDetails.size() > 0 ? userDetails.get(0) : null) : null;
		} catch (Exception e) {
			LOG.info("Exception in findActiveUserByMobileNumberAndExcludingId========>"+ e.getStackTrace(), e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public UserMaster findActiveUserByMobileNumberAndId(String mobileNumber, Long id) {
		try {
			StringBuilder sb = new StringBuilder();
			if (mobileNumber != null && !mobileNumber.equals(""))
				sb.append(" and mobileNumber = '" + mobileNumber + "' ");
			if ( id != null && id > 0)
				sb.append(" and id = " + id + "");

			String sql = "FROM UserMaster WHERE status = true "+sb.toString()+"";
			List<UserMaster> userDetails = find(sql);
			return userDetails != null ? (userDetails.size() > 0 ? userDetails.get(0) : null) : null;
		} catch (Exception e) {
			LOG.info("Exception in findActiveUserByMobileNumberAndExcludingId========>"+ e.getStackTrace(), e);
			return null;
		}
	}
	
 	
 	public UserMaster getUserByUsernameAndPassword(String userID, String password) {
		String sql="from UserMaster where mobileNumber = '" + userID + "' and password = '" + password + "'";
		LOG.info("getUserByUsernameAndPassword sql===>"+sql);
		return (UserMaster) findObject(sql);	
	}
 		
 	public UserMaster getUserByUserIdAndPassword(String userID, String password) {
		String sql="from UserMaster where loginId = '" + userID + "' and password = '" + password + "'";
		LOG.info("getUserByUsernameAndPassword sql===>"+sql);
		return (UserMaster) findObject(sql);	
	}
 	
	public UserMaster getUserBymblNo(String mobileNumber, long id) {
		return (UserMaster) findObject("FROM UserMaster WHERE status = true and mobileNumber = '" + mobileNumber + "' AND id <> " + id + "");
	}
	
 	@SuppressWarnings("unchecked")
	public List<UserMaster> getUserByMobileNumber(String mobileNumber, long id) {
		List<UserMaster> list = new ArrayList<UserMaster>();
		try {
			String sql="FROM UserMaster WHERE status = true and mobileNumber = '" + mobileNumber + "' AND id = " + id + "";
			LOG.info("getUserByMobileNumber===>"+sql);
			list = find(sql);
		} catch (Exception e) {
			LOG.info("Exception getUserByMobileNumber======>" + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}
	

	public UserMaster getUserByemailId(String emailId, long id) {
		String sql ="FROM UserMaster WHERE emailId = '" + emailId + "' AND id <> " + id + "";
		return (UserMaster) findObject(sql);
	}
	
	@SuppressWarnings("unchecked")
	public UserMaster findByMobileNumberAndEmail(String mobileNumber,String emailId) {
		try {
//			String sql = "from UserMaster where mobileNumber='"+mobileNumber +"' and emailId='"+ emailId +"'";
			String sql = "from UserMaster where mobileNumber='"+mobileNumber +"'";

			LOG.info("findByMobileNumberAndEmail==>" + sql);
			List<UserMaster> userDetails = find(sql);
			return userDetails != null ? (userDetails.size() > 0 ? userDetails.get(0) : null) : null;
		} catch (Exception e) {
			LOG.info("Exception findByMobileNumberAndEmail========>"+ e.getStackTrace(), e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public UserMaster findByUserId(String userId,String password ) {
		try {
			String sql = "from UserMaster where userId = '" + userId + "' and password = '" + password + "'";
			List<UserMaster> userInfo = find(sql);
			return userInfo != null ? (userInfo.size() > 0 ? userInfo.get(0) : null) : null;
		} catch (Exception e) {
			LOG.info(e);
			return null;
		}
	}
	
	public UserMaster getUserBasedOnUserIdNMobileNumber(long id, String mobileNumber) {
		LOG.info(" from UserMaster where id = " + id + " and mobileNumber = '" +mobileNumber+ "' and status = true ");
		return (UserMaster) findObject(" from UserMaster where id = " + id + " and mobileNumber = '" +mobileNumber+ "' and status = true ");
	}
	
	@SuppressWarnings("unchecked")
	public List<UserMaster> findActiveUsersListByMobileNumberAndId(String mobileNumber, Long id) {
		try {
			StringBuilder sb = new StringBuilder();
			if (mobileNumber != null && !mobileNumber.equals(""))
				sb.append(" and mobileNumber = '" + mobileNumber + "' ");
			if ( id != null && id > 0)
				sb.append(" and id = " + id + "");

			String sql = "FROM UserMaster WHERE status = true "+sb.toString()+"";
			List<UserMaster> userDetails = find(sql);
			return userDetails;
		} catch (Exception e) {
			LOG.info("Exception in findActiveUsersListByMobileNumberAndId========>"+ e.getStackTrace(), e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public UserMaster getCustomerBasedOnCustomerIdNMobileNumberNDeviceId(String userId, String mobileNumber,String deviceId, String deviceToken) {
		try {
			String sql = "from UserMaster where status = True and id = " + userId + " and mobileNumber = '" + mobileNumber + "' and deviceId = '" + deviceId + "'";
			List<UserMaster> userDetails = find(sql);
			return userDetails != null ? (userDetails.size() > 0 ? userDetails.get(0) : null) : null;
		} catch (Exception e) {
			LOG.info("Exception in getCustomerBasedOnCustomerIdNMobileNumberNDeviceId========>"+ e.getStackTrace(), e);
			return null;
		}
	}
	public UserMaster getUserBasedOnUserIdNMobileNumber(String userId, String mobileNumber) {
		String sql = "from UserMaster where id ="+userId+" and mobileNumber = '"+mobileNumber+"' and status = true";
		LOG.info("sql===>"+sql);
		return (UserMaster) findObject(sql);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserMaster> getAllEmployeeMasters() {
		List<UserMaster> list = new ArrayList<UserMaster>();
		try {
	        String sql = "FROM UserMaster order by id desc";
			list = find(sql);
		} catch (Exception e) {
			LOG.info("Exception getAllUsersForExcelReport=======================> " + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}
	
	public UserMaster getUserbyMobileNumber(String mobileNumber) {
		String sql=" FROM UserMaster WHERE status = true and mobileNumber ='"+mobileNumber+"'";
		LOG.info("getUserbyMobileNumber sql=======> " +sql);
		return (UserMaster) findObject(sql);
	}
	
	public UserMaster getUserbyLoginId(String loginId) {
		String sql=" FROM UserMaster WHERE status = true and loginId ='"+loginId+"'";
		LOG.info("getUserbyLoginId sql=======> " +sql);
		return (UserMaster) findObject(sql);
	}

	@SuppressWarnings("unchecked")
	public List<UserMaster> getUserByEmailId(String emailId) {
		List<UserMaster> list = new ArrayList<UserMaster>();
		try {
			String sql="from UserMaster where emailId = '" + emailId + "'";
			LOG.info("getUserByEmailId:sql===>"+sql);
			list = find(sql);
		} catch (Exception e) {
			LOG.info("Exception getUserByEmailId======>" + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<UserMaster> getAllUsersForExcelReport() {
		List<UserMaster> list = new ArrayList<UserMaster>();
		try {
			String sql = "from UserMaster where 1=1 order by id desc" ;
			list = find(sql);
		} catch (Exception e) {
			LOG.info("Exception getAllUsersForExcelReport=======================> " + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserMaster> getUserDataBasedOnIdAndMobileNumber(long id, String mobileNumber) {
		List<UserMaster> list = new ArrayList<UserMaster>();
		try {
			list = find("from UserMaster where id = "+id+" and mobileNumber = '"+mobileNumber+"'");
		} catch (Exception e) {
			LOG.info("Exception getUserDataBasedOnIdAndMobileNumber=======================> " + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}

	public UserMaster getToBeUpdatedUserDetails(String name, String mobileNumber) {
		return (UserMaster) findObject("from UserMaster where status = true and name is not null and name not in ('') and name = '" + name+ "' and mobileNumber is not null and mobileNumber not in ('') and mobileNumber = '" + mobileNumber + "'");
	}
	
	@SuppressWarnings("unchecked")
	public UserMaster getExistingEmployeeDetails(Long roleId, Long headquarterId, Long territoryId, Long regionId, Long zoneId, Long stateId, Long districtId) {
		try {
			String cond = "";
			
			if (roleId != null && roleId > 0) {
				cond += " and role.id = " + roleId + "";
			}
			
			if (zoneId != null && zoneId > 0) {
				cond += " and zone.id = " + zoneId + "";
			}
			if (regionId != null && regionId > 0) {
				cond += " and region.id = " + regionId + "";
			}
			if (territoryId != null && territoryId > 0) {
				cond += " and territory.id = " + territoryId + "";
			}
			if (headquarterId != null && headquarterId > 0) {
				cond += " and headQuarter.id = " + headquarterId + "";
			}
			
			if (stateId != null && stateId > 0) {
				cond += " and state.id = " + stateId + "";
			}
			
			if (districtId != null && districtId > 0) {
				cond += " and districtId.id = " + districtId + "";
			}
			
//			if (name != null && !name.equals("")) {
//				cond += " and name = '" + name + "'";
//			}
//			
//			if (mobileNumber != null && !mobileNumber.equals("")) {
//				cond += " and mobileNumber = '" + mobileNumber + "'";
//			}
			
			String hql = " from UserMaster where status=true "+cond.toString()+"";
			LOG.info("getExistingEmployeeDetails=====>" + hql);
			List<UserMaster> list = find(hql);
			if(list != null) {
				LOG.info("getExistingEmployeeDetails======NOT_NULL");
				if(list.size() > 0) {
					LOG.info("getExistingEmployeeDetails======NOT_NULL_AND>0"+list.size());
					return list.get(0);
				}
				else
					return null;
			}
			else
				return null;
		} catch (Exception e) {
			LOG.info("<=== Exception<===>getExistingEmployeeDetails===>" + e.getStackTrace(), e);
			return null;
		}
	}
}