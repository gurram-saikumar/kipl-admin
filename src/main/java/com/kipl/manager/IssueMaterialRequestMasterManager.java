package com.kipl.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.IssueMaterialRequestMaster;

@Repository
public class IssueMaterialRequestMasterManager extends GenericManager<IssueMaterialRequestMaster, Long>{
	
	private static final Logger LOG = LogManager.getLogger(IssueMaterialRequestMasterManager.class);

	public IssueMaterialRequestMasterManager() {
		super(IssueMaterialRequestMaster.class);
	}

}
