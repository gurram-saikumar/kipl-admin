package com.kipl.manager;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.BillOfMaterialMaster;
import com.kipl.models.MenuMaster;

@Repository
public class BillOfMaterialMasterManager extends GenericManager<BillOfMaterialMaster, Long>{

	
	private static final Logger LOG = LogManager.getLogger(BillOfMaterialMasterManager.class);

	public BillOfMaterialMasterManager() {
		super(BillOfMaterialMaster.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<BillOfMaterialMaster> getBillOfMaterialList() {
		
		try {
			String hql = "from BillOfMaterialMaster where status=true ";
			LOG.info("getBillOfMaterialList==>"+hql);
			List<BillOfMaterialMaster> li = find(hql);
			return li;
		} catch (Exception e) {
			LOG.info("EX getBillOfMaterialList"+e.getStackTrace(),e);
			return null;
		}
		
	}
	
}

