package com.kipl.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.CompanyProjectMaster;

@Repository
public class JobMasterManager extends GenericManager<CompanyProjectMaster, Long>{
	private static final Logger LOG = LogManager.getLogger(JobMasterManager.class);

	public JobMasterManager() {
		super(CompanyProjectMaster.class);
	}
}
