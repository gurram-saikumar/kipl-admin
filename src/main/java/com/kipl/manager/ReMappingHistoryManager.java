package com.kipl.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.ReMappingHistory;


@Repository
public class ReMappingHistoryManager extends GenericManager<ReMappingHistory, Long> {
	private static final Logger LOG = LogManager.getLogger(ReMappingHistoryManager.class);

	public ReMappingHistoryManager() {
		super(ReMappingHistory.class);
	}

	public ReMappingHistory getEmployeeExistence(Long employeeId) {
		String sql = " FROM ReMappingHistory where status = true and employee.id = " + employeeId + "";
		LOG.info("getEmployeeExistence===>Query====>" + sql);
		return (ReMappingHistory) findObject(sql);
	}

	public ReMappingHistory getEmployeeExistenceBasedOnIds(String updatedRoleName, Long zoneId, Long stateId, Long regionId, Long territoryId, Long districtId, Long headquarterId) {
		String sql = "from ReMappingHistory where status = true and updatedRoleName = '"+updatedRoleName+"' and updatedZone = "+zoneId+" and updatedState = "+stateId+" and updatedRegion="+regionId+" and updatedTerritory = "+territoryId+" and updatedDistrict = "+districtId+" and updatedHeadquarter = "+headquarterId+"";
		return (ReMappingHistory) findObject(sql);
	}
}
