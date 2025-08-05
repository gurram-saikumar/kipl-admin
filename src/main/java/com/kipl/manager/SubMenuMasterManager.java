package com.kipl.manager;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.SubMenuMaster;



@Repository
public class SubMenuMasterManager extends GenericManager<SubMenuMaster, Long>{

	private static final Logger LOG = LogManager.getLogger(SubMenuMasterManager.class);

	public SubMenuMasterManager() {
		super(SubMenuMaster.class);
	}

	@SuppressWarnings("unchecked")
	public List<SubMenuMaster> getSubMenuListBasedOnManuId(Long menuId) {
		try {
			String hql = "from SubMenuMaster where menuId.id="+menuId+" and status=true";
			List<SubMenuMaster> li = find(hql);
			return li;
		} catch (Exception e) {
			LOG.info("EX getSubMenuListBasedOnManuId"+e.getStackTrace(),e);
			return null;
		}
	}

}
