package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.InventoryMaster;

@Repository
public class InventoryMasterManager extends GenericManager<InventoryMaster, Long>{
	private static final Logger LOG = LogManager.getLogger(InventoryMasterManager.class);

	public InventoryMasterManager() {
		super(InventoryMaster.class);
	}

	@SuppressWarnings("unchecked")
	public List<InventoryMaster> getAllInventoryList() {
		List<InventoryMaster> list = new ArrayList<InventoryMaster>();
		try {
			list = find("from InventoryMaster where 1=1");
		} catch (Exception e) {
			LOG.info("<=== Exception getAllInventoryList ===>" + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	public Double getInventoryAvailableQunatityBasedonMaterialId(String materialId) {
		  String hql = "select sum(availableQuantity) from InventoryMaster where materialId='"+materialId+"' ";
	        LOG.info("getSurveyCountForProductPerformance==> " + hql);
	        List<Double> ids = find(hql);
	        return ids != null && !ids.isEmpty() ? ids.get(0) : null;
	}
	
	public InventoryMaster getInventoryListBasedonMaterialId(String materialId) {
		  String hql = " from InventoryMaster where materialId='"+materialId+"' ";
	        LOG.info("getSurveyCountForProductPerformance==> " + hql);
	        List<InventoryMaster> ids = find(hql);
	        return ids != null && !ids.isEmpty() ? ids.get(0) : null;
	}

}
