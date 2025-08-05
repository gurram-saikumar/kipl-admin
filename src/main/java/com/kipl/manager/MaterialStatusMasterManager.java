package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.common.HibernateDao;
import com.kipl.models.MaterialRequestMaster;
import com.kipl.models.MaterialStatusMaster;
import com.kipl.models.UserMaster;

@Repository
public class MaterialStatusMasterManager extends GenericManager<MaterialStatusMaster, Long>{

	private static final Logger LOG = LogManager.getLogger(MaterialStatusMasterManager.class);
	
	@Autowired
	@Qualifier("hibernateDao")
	protected HibernateDao hibernateDao;

	public MaterialStatusMasterManager() {
		super(MaterialStatusMaster.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<MaterialStatusMaster> getMaterialStatusList() {

		List<MaterialStatusMaster> list = new ArrayList<MaterialStatusMaster>();
		try {
			list = find("from MaterialStatusMaster where status=true  order by id desc");
		} catch (Exception e) {
			LOG.info("<=== Exception getMaterialStatusList ===>" + e.getStackTrace(), e); 
			list = null;
		}
		return list;
	}


}
