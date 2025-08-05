package com.kipl.manager;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.SegmentMaster;

@Repository
public class SegmentMasterManager extends GenericManager<SegmentMaster, Long>{

	private static final Logger LOG = LogManager.getLogger(SegmentMasterManager.class);

	public SegmentMasterManager() {
		super(SegmentMaster.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<SegmentMaster> getActiveSegmentMasterList() {
		
		try {
			String hql = "from SegmentMaster where status=true ";
			LOG.info("getActiveSegmentMasterList==>"+hql);
			List<SegmentMaster> li = find(hql);
			return li;
		} catch (Exception e) {
			LOG.info("EX getActiveSegmentMasterList==> "+e.getStackTrace(),e);
			return null;
		}
		
	}
}
