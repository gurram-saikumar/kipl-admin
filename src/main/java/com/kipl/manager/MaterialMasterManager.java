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
public class MaterialMasterManager extends GenericManager<MaterialMaster, Long>{

	private static final Logger LOG = LogManager.getLogger(MaterialMasterManager.class);

	public MaterialMasterManager() {
		super(MaterialMaster.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<MaterialMaster> getAllActiveMaterials() {
		List<MaterialMaster> list = new ArrayList<MaterialMaster>();
		try {
			list = find("from MaterialMaster where status=true ");
		} catch (Exception e) {
			LOG.info("<=== Exception getAllActiveMaterials ===>" + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<MaterialMaster> getMaterialList() {
		List<MaterialMaster> list = new ArrayList<MaterialMaster>();
		try {
			list = find("from MaterialMaster");
		} catch (Exception e) {
			LOG.info("<=== Exception getMaterialList ===>" + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public Double getMaterialCount() {
		  String hql = "select COALESCE(SUM(availableQuantity), 0) from MaterialMaster where status=true ";
	        LOG.info("getMaterialCount==> " + hql);
	        List<Double> ids = find(hql);
	        return ids != null && !ids.isEmpty() ? ids.get(0) : 0;
	}
	
	@SuppressWarnings("unchecked")
	public Double getAvailableMaterialWeight() {
		  String hql = "select COALESCE(SUM(CAST(weight AS double), 0) from MaterialMaster where status=true ";
	        LOG.info("getAvailableMaterialWeight==> " + hql);
	        List<Double> ids = find(hql);
	        return ids != null && !ids.isEmpty() ? ids.get(0) : 0;
	}

	public Long getShortAgeMeterialCount() {
		 String hql = "select count(*) from MaterialMaster where status=true and availableQuantity<=0";
	        LOG.info("getShortAgeMeterialCount==> " + hql);
	        List<Long> ids = find(hql);
	        return ids != null && !ids.isEmpty() ? ids.get(0) : 0;
	}

	public MaterialMaster getMaterialExistBasedOnFileter(String rmSize, String segment, String grade, String colour) {
		String hql = " from MaterialMaster where rmSize='" + rmSize + "' and  segment='" + segment + "' and colour='"
				+ colour + "' and grade='" + grade + "'";
		LOG.info("getMaterialExistBasedOnRMSize==> " + hql);
		List<MaterialMaster> ids = find(hql);
		return ids != null && !ids.isEmpty() ? ids.get(0) : null;
	}
}
