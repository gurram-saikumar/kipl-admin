package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.RoleMaster;


@Repository
public class RoleMasterManager extends GenericManager<RoleMaster, Long> {

	private static final Logger LOG = LogManager.getLogger(RoleMasterManager.class);

	public RoleMasterManager() {
		super(RoleMaster.class);
	}

	public RoleMaster getRoleType(String roleName) {
		return (RoleMaster) findObject(" from RoleMaster where name = '" + roleName + "'");
	}
	@SuppressWarnings("unchecked")
	public List<RoleMaster> getAllRoles(int page, int itemsPerPage, String filterString) {
		List<RoleMaster> list = new ArrayList<RoleMaster>();
		try {
			String condition = "";
			int startIndex = 0;

			if (filterString != null && !filterString.isEmpty()) {
				condition += " AND (name like '%" + filterString + "%')";
			}
			startIndex = (page - 1) * itemsPerPage;
			LOG.info("getAllRoles===================>");
			list = find("from RoleMaster where 1=1 " + condition.toString() + " order by displayOrder asc limit "+ itemsPerPage + " offset " + startIndex + "");
		} catch (Exception e) {
			LOG.info("Exception getActiveRoles=======================> " + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public RoleMaster getRoleByName(String roleName) {
		try {
			String sql = "from RoleMaster where name = '" + roleName + "'";
			List<RoleMaster> role = find(sql);
			return role != null ? (role.size() > 0 ? role.get(0) : null) : null;
		} catch (Exception e) {
			LOG.info("Exception in getCustomerBasedOnCustomerIdNMobileNumberNDeviceId========>"+ e.getStackTrace(), e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RoleMaster> getAllActiveRoles() {
		List<RoleMaster> list = new ArrayList<RoleMaster>();
		try {
			list = find("from RoleMaster where status=true order by id asc");
		} catch (Exception e) {
			LOG.info("Exception getAllActiveRole===> " + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}
}


