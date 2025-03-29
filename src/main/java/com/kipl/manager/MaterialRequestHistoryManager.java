package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.MaterialRequestHistory;
import com.kipl.models.MaterialRequestMaster;

@Repository
public class MaterialRequestHistoryManager extends GenericManager<MaterialRequestHistory, Long>{

	private static final Logger LOG = LogManager.getLogger(MaterialRequestHistoryManager.class);

	public MaterialRequestHistoryManager() {
		super(MaterialRequestHistory.class);
	}

	@SuppressWarnings("unchecked")
	public List<MaterialRequestHistory> getMaterialRequestHistoryBasedonId(MaterialRequestMaster materialRequest) {
		List<MaterialRequestHistory> list = new ArrayList<MaterialRequestHistory>();
		try {
			list = find("from MaterialRequestHistory where status=true and requestId='"+materialRequest+"' ");
		} catch (Exception e) {
			LOG.info("<=== Exception getMaterialRequestHistoryBasedonId ===>" + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}
}
