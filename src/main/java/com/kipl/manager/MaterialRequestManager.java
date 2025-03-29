package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.MaterialMaster;
import com.kipl.models.MaterialRequestMaster;
import com.kipl.models.UserMaster;

@Repository
public class MaterialRequestManager extends GenericManager<MaterialRequestMaster, Long>{

	private static final Logger LOG = LogManager.getLogger(MaterialRequestManager.class);

	public MaterialRequestManager() {
		super(MaterialRequestMaster.class);
	}

	@SuppressWarnings("unchecked")
	public List<MaterialRequestMaster> getMaterialRequestList(UserMaster user) {

		List<MaterialRequestMaster> list = new ArrayList<MaterialRequestMaster>();
		try {
			list = find("from MaterialRequestMaster where status=true and requesterId='"+user+"' ");
		} catch (Exception e) {
			LOG.info("<=== Exception getMaterialRequestList ===>" + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}
}
