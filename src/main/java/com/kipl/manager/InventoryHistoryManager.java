package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.InventoryHistory;
import com.kipl.models.InventoryMaster;

@Repository
public class InventoryHistoryManager extends GenericManager<InventoryHistory, Long>{
	private static final Logger LOG = LogManager.getLogger(InventoryHistoryManager.class);

	public InventoryHistoryManager() {
		super(InventoryHistory.class);
	}

	@SuppressWarnings("unchecked")
	public List<InventoryHistory> getAllInventoryHistoryList() {
		List<InventoryHistory> list = new ArrayList<InventoryHistory>();
		try {
			list = find("from InventoryHistory where 1=1");
		} catch (Exception e) {
			LOG.info("<=== Exception getAllInventoryHistoryList ===>" + e.getStackTrace(), e);
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
	
	public InventoryMaster getInventoryListBasedonMaterialIdAnd(String materialId) {
		  String hql = " from InventoryMaster where materialId='"+materialId+"' ";
	        LOG.info("getSurveyCountForProductPerformance==> " + hql);
	        List<InventoryMaster> ids = find(hql);
	        return ids != null && !ids.isEmpty() ? ids.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public InventoryMaster getInventoryListBasedonRMSizeAndSegmentAndColourAndGrade(String rmSize,String segment,String colour,String grade) {
	String hql = " from InventoryMaster where rmSize='" + rmSize + "' and  segment='" + segment + "' and colour='"
			+ colour + "' and grade='" + grade + "'";
	LOG.info("getMaterialExistBasedOnRMSize==> " + hql);
	List<InventoryMaster> ids = find(hql);
	return ids != null && !ids.isEmpty() ? ids.get(0) : null;
	}

}
