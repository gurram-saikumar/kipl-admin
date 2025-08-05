package com.kipl.manager;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.GradeMaster;

@Repository
public class GradeMasterManager extends GenericManager<GradeMaster, Long>{

	private static final Logger LOG = LogManager.getLogger(GradeMasterManager.class);

	public GradeMasterManager() {
		super(GradeMaster.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<GradeMaster> getActiveGradeMasterList() {
		
		try {
			String hql = "from GradeMaster where status=true ";
			LOG.info("getActiveGradeMasterList==>"+hql);
			List<GradeMaster> li = find(hql);
			return li;
		} catch (Exception e) {
			LOG.info("EX getActiveGradeMasterList"+e.getStackTrace(),e);
			return null;
		}
		
	}
}
