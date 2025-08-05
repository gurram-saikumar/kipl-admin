package com.kipl.manager;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.ColourMaster;
@Repository
public class ColourMasterManager extends GenericManager<ColourMaster, Long>{

	private static final Logger LOG = LogManager.getLogger(ColourMasterManager.class);

	public ColourMasterManager() {
		super(ColourMaster.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ColourMaster> getActiveColourMasterList() {
		
		try {
			String hql = "from ColourMaster where status=true ";
			LOG.info("getActiveColourMasterList==>"+hql);
			List<ColourMaster> li = find(hql);
			return li;
		} catch (Exception e) {
			LOG.info("EX getActiveColourMasterList==> "+e.getStackTrace(),e);
			return null;
		}
		
	}
}
