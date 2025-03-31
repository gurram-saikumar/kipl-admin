package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.CompanyProjectMaster;

@Repository
public class CompanyProjectMasterManager extends GenericManager<CompanyProjectMaster, Long>{
	private static final Logger LOG = LogManager.getLogger(CompanyProjectMasterManager.class);

	public CompanyProjectMasterManager() {
		super(CompanyProjectMaster.class);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyProjectMaster> getAllActiveProjects() {
		List<CompanyProjectMaster> list = new ArrayList<CompanyProjectMaster>();
		try {
			list = find("from CompanyProjectMaster where status=true ");
		} catch (Exception e) {
			LOG.info("<=== Exception getAllActiveProjects ===>" + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}

	public List<CompanyProjectMaster> getProjectsList() {
		List<CompanyProjectMaster> list = new ArrayList<CompanyProjectMaster>();
		try {
			list = find("from CompanyProjectMaster");
		} catch (Exception e) {
			LOG.info("<=== Exception getProjectsList ===>" + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}
}
