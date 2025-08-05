package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.InventoryMaster;
import com.kipl.models.MaterialMaster;

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
		  String hql = "select ROUND(SUM(availableWeight),2) from InventoryMaster where materialId='"+materialId+"' ";
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

	public Double getInventoryTotalWeight() {
		String hql = "select  ROUND(SUM(totalWeight),2) from InventoryMaster  ";
        LOG.info("getInventoryTotalWeight==> " + hql);
        List<Double> ids = find(hql);
        return ids != null && !ids.isEmpty() ? ids.get(0) : null;
	}
	
	public Double getInventoryAvailableWeight() {
		String hql = "select ROUND(SUM(totalWeight)-SUM(availableWeight),2) from InventoryMaster  ";
        LOG.info("getInventoryAvailableWeight==> " + hql);
        List<Double> ids = find(hql);
        return ids != null && !ids.isEmpty() ? ids.get(0) : null;
	}

}
