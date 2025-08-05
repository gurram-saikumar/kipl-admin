package com.kipl.manager;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.RMMaster;

@Repository
public class RMMasterManager extends GenericManager<RMMaster, Long>{
	private static final Logger LOG = LogManager.getLogger(RMMasterManager.class);

	public RMMasterManager() {
		super(RMMaster.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<RMMaster> getActiveRMMasterList() {
		
		try {
			String hql = "from RMMaster where status=true ";
			LOG.info("getActiveRMMasterList==>"+hql);
			List<RMMaster> li = find(hql);
			return li;
		} catch (Exception e) {
			LOG.info("EX getActiveRMMasterList==> "+e.getStackTrace(),e);
			return null;
		}
		
	}

}
