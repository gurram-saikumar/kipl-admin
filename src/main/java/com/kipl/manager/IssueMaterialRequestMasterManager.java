package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.IssueMaterialRequestMaster;

@Repository
public class IssueMaterialRequestMasterManager extends GenericManager<IssueMaterialRequestMaster, Long>{
	
	private static final Logger LOG = LogManager.getLogger(IssueMaterialRequestMasterManager.class);

	public IssueMaterialRequestMasterManager() {
		super(IssueMaterialRequestMaster.class);
	}

	@SuppressWarnings("unchecked")
	public List<IssueMaterialRequestMaster> getIssueMaterialListBasedonIssuerId(Long issuerId) {
		List<IssueMaterialRequestMaster> list = new ArrayList<IssueMaterialRequestMaster>();
		try {
			list = find("from IssueMaterialRequestMaster where status=true and issuerId.id="+issuerId+" order by id desc ");
		} catch (Exception e) {
			LOG.info("<=== Exception getIssueMaterialListBasedonIssuerId ===>" + e.getStackTrace(), e);
			list = null;
		}
		return list;
	}

	public Double getIssuedQuantityBasedonMaterialRequestHistoryId(Long id) {
	    LOG.info("PURCHASE   STARTUP------>2------------>"
	            + "SELECT COALESCE(SUM(issuedQuantity), 0) FROM IssueMaterialRequestMaster WHERE materialRequestHistoryId.id = " + id);

	    String sql = "SELECT COALESCE(SUM(issuedQuantity), 0) FROM IssueMaterialRequestMaster WHERE materialRequestHistoryId.id = " + id;
	    Double acres = (Double) getSum(sql);
	    
	    return acres;
	}

}
