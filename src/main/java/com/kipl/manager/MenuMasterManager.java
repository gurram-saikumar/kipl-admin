package com.kipl.manager;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.MenuMaster;



@Repository
public class MenuMasterManager extends GenericManager<MenuMaster, Long>{

	private static final Logger LOG = LogManager.getLogger(MenuMasterManager.class);

	public MenuMasterManager() {
		super(MenuMaster.class);
	}

	@SuppressWarnings("unchecked")
	public List<MenuMaster> getMenuListbasedOnType(String type) {
		
		try {
			String hql = "from MenuMaster where status=true and type='"+type+"' order by displayOrder";
			LOG.info("getMenuListbasedOnType==>"+hql);
			List<MenuMaster> li = find(hql);
			return li;
		} catch (Exception e) {
			LOG.info("EX getMenuListbasedOnType"+e.getStackTrace(),e);
			return null;
		}
		
	}

}
