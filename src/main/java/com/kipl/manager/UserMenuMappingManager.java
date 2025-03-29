package com.kipl.manager;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.UserMenuMappingEntity;


@Repository
public class UserMenuMappingManager extends GenericManager<UserMenuMappingEntity, Long> {

	private static final Logger LOG = LogManager.getLogger(UserMenuMappingManager.class);

	public UserMenuMappingManager() {
		super(UserMenuMappingEntity.class);
	}
	
//	public UserMenuMappingEntity getUserbyMobileNumber(Long roleId, Long stateId) {
//		String sql=" FROM UserMenuMappingEntity WHERE status = true and roleId.id ="+roleId+" and stateId.id="+stateId+"";
//		LOG.info("sql=======> " +sql);
//		return (UserMenuMappingEntity) findObject(sql);
//	}

	public UserMenuMappingEntity getUserbyMobileNumber(Long roleId) {
		String sql=" FROM UserMenuMappingEntity WHERE status = true and roleId.id ="+roleId+"";
		LOG.info("sql=======> " +sql);
		return (UserMenuMappingEntity) findObject(sql);
	}

	@SuppressWarnings("unchecked")
	public List<UserMenuMappingEntity> getUserMenuByRoleIdAndStateId(Long roleId, String type) {
		try {
			String sql=" FROM UserMenuMappingEntity WHERE status = true and roleId.id ="+roleId+" and isView=true and type='"+type+"' ";
			LOG.info("sql===>"+sql);
			List<UserMenuMappingEntity> li = find(sql);
			return li;
		} catch (Exception e) {
			LOG.info("Exception getUserMenuByRoleIdAndStateId=======> " +e.getStackTrace(), e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UserMenuMappingEntity> getSubMenusBySubMenuId(Long menuId) {
		try {
			String sql=" FROM UserMenuMappingEntity WHERE status = true and menuId.id = "+menuId+"";
			
			LOG.info("sql=============>"+sql);
			List<UserMenuMappingEntity> li = find(sql);
			return li;
		} catch (Exception e) {
			LOG.info("Exception getSubMenusBySubMenuId=======> " +e.getStackTrace(), e);
			return null;
		}
	}
	
	public UserMenuMappingEntity getUserMenuByRoleIdAndStateIdAndMainMenuId(Long roleId, Long mainMenuId) {
		try {
			String sql=" FROM UserMenuMappingEntity WHERE status = true and roleId.id ="+roleId+" and menuId.id="+mainMenuId+"";
			return (UserMenuMappingEntity) findObject(sql);
		} catch (Exception e) {
			LOG.info("Exception getUserMenuByRoleIdAndStateIdAndMainMenuId=======> " +e.getStackTrace(), e);
			return null;
		}
	}
	
	public UserMenuMappingEntity getUserMenuByRoleIdAndMainMenuId(Long roleId,Long mainMenuId) {
		try {
			String sql=" FROM UserMenuMappingEntity WHERE status = true and roleId.id ="+roleId+" and menuId.id="+mainMenuId+" and isView=true";
			return (UserMenuMappingEntity) findObject(sql);
		} catch (Exception e) {
			LOG.info("Exception getUserMenuByRoleIdAndStateIdAndMainMenuId=======> " +e.getStackTrace(), e);
			return null;
		}
	}

	
	public UserMenuMappingEntity getUserMenuByRoleIdAndStateIdAndSubMenuId(Long roleId, Long subMenuId) {
		try {
			String sql=" FROM UserMenuMappingEntity WHERE status = true and roleId.id ="+roleId+" and subMenuId.id="+subMenuId+"";
			return (UserMenuMappingEntity) findObject(sql);
		} catch (Exception e) {
			LOG.info("Exception getUserMenuByRoleIdAndStateIdAndSubMenuId=======> " +e.getStackTrace(), e);
			return null;
		}
	}
	
	public UserMenuMappingEntity getUserMenuByRoleIdAndSubMenuId(Long roleId,Long subMenuId) {
		try {
			String sql=" FROM UserMenuMappingEntity WHERE status = true and roleId.id ="+roleId+" and subMenuId.id="+subMenuId+" and isView = true";
			return (UserMenuMappingEntity) findObject(sql);
		} catch (Exception e) {
			LOG.info("Exception getUserMenuByRoleIdAndStateIdAndSubMenuId=======> " +e.getStackTrace(), e);
			return null;
		}
	}
	
	public UserMenuMappingEntity getUserMenuByRoleIdAndMainMenuIdAndLanaguageId(Long roleId,Long mainMenuId,Long languageId) {
		try {
				StringBuilder sb = new StringBuilder();
			if (languageId != null && languageId>0)
				sb.append(" and languageId.id="+languageId+"");
			
			String sql=" FROM UserMenuMappingEntity WHERE status = true and roleId.id ="+roleId+" and menuId.id="+mainMenuId+" "+sb.toString()+" ";
			LOG.info("getUserMenu====>"+sql);
			return (UserMenuMappingEntity) findObject(sql);
		} catch (Exception e) {
			LOG.info("Exception getUserMenuByRoleIdAndStateIdAndMainMenuId=======> " +e.getStackTrace(), e);
			return null;
		}
	}
	
	public UserMenuMappingEntity getUserMenuByRoleIdAndMainMenuIdAndType(Long roleId,Long mainMenuId,String type) {
		try {
				
			String sql=" FROM UserMenuMappingEntity WHERE status = true and roleId.id ="+roleId+" and menuId.id="+mainMenuId+" and type='"+type+"' ";
			LOG.info("getUserMenuByRoleIdAndMainMenuIdAndType====>"+sql);
			return (UserMenuMappingEntity) findObject(sql);
		} catch (Exception e) {
			LOG.info("Exception getUserMenuByRoleIdAndMainMenuIdAndType=======> " +e.getStackTrace(), e);
			return null;
		}
	}
}