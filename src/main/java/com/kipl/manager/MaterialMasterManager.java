package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.MaterialMaster;

@Repository
public class MaterialMasterManager extends GenericManager<MaterialMaster, Long>{

	private static final Logger LOG = LogManager.getLogger(MaterialMasterManager.class);

	public MaterialMasterManager() {
		super(MaterialMaster.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<MaterialMaster> getAllActiveMaterials() {
		List<MaterialMaster> list = new ArrayList<MaterialMaster>();
		try {
			list = find("from MaterialMaster where status=true ");
		} catch (Exception e) {
			LOG.info("<=== Exception getAllActiveMaterials ===>" + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}
}
