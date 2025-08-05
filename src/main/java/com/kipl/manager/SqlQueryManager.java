package com.kipl.manager;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.kipl.common.HibernateDao;
import com.kipl.dto.MaterialDto;

import jakarta.transaction.Transactional;

@SuppressWarnings("deprecation")
@Repository
@Component
public class SqlQueryManager {
	private static final Log LOG = LogFactory.getLog(SqlQueryManager.class);

	@Autowired private HibernateDao hibernateDao;
	//private Environment appConfig;
	
	
	public Long fetchAllUsersMastersDataCount(String filterString) {
		String condition = "";
		if (filterString!=null && !filterString.isEmpty()) {
        	condition +=" AND (name like '%"+filterString+"%' or mobileNumber like '%"+filterString+"%' or dateOfBirth like '%"+filterString+"%' \r\n"
        			+ "or emailId like '%"+filterString+"%' or gender like '%"+filterString+"%' or stateName like '%"+filterString+"%'  \r\n"
        			+ "or districtName like '%"+filterString+"%' or employeeId like '%"+filterString+"%' or address like '%"+filterString+"%'  \r\n"
        			+ "or village like '%"+filterString+"%' or block like '%"+filterString+"%' or pinCode like '%"+filterString+"%'  \r\n"
        			+ "or alternativeMobileNumber like '%"+filterString+"%' or landMark like '%"+filterString+"%' or role.name like '%"+filterString+"%'\r\n"
        			//+ " or region.name like '%"+filterString+"%' or territory.name like '%"+filterString+"%'  \r\n"
        			//+ "or zone.name like '%"+filterString+"%' or headQuarter.name like '%"+filterString+"%'"
        			+ ")";
		}
		try {
			StringBuffer hql = new StringBuffer("select count(*) from UserMasterEntity where 1=1 "+condition+" ");
			LOG.info(hql);
			Object result = hibernateDao.getSession().createQuery(hql.toString()).uniqueResult();
            return result != null ? ((Number) result).longValue() : 0L;
		} catch (Exception e) {
			LOG.info(e);
			return 0L;
		}
	}

	@Transactional
	public String updateStatement(String tableName, String ids,String status) {
		try {
			String cond="";
			if(!"NA".equalsIgnoreCase(status)) {
				if("Activate".equalsIgnoreCase(status))
					cond="status=true";
				else
					cond="status=false";
				
				String sql = "update " + tableName + " set "+cond+" where id in (" + ids + ")";
				LOG.info("updateStatement==> " + sql);
				hibernateDao.getSession().createQuery(sql).executeUpdate();
			}
			return "Success";
		} catch (Exception e) {
			LOG.info("Ex updateStatement==>" + e.getStackTrace(), e);
			return "Failed";
		}
	}

	public Long getEmployeeNotificationsCount(Long id) {
		try {
			StringBuffer sql = new StringBuffer("select count(*) from Notifications where user.id = " + id + " and status=true and active=true");
			LOG.info(sql);
			Object result = hibernateDao.getSession().createQuery(sql.toString()).uniqueResult();
            return result != null ? ((Number) result).longValue() : 0L;
		} catch (Exception e) {
			LOG.info(e);
			return 0L;
		}
	}
	
	public Long getInventoryAvailableQunatityBasedonMaterialId(Long id) {
		try {
			StringBuilder sql = new StringBuilder(
					"select SUM(AVAILABLE_QUANTITY) from INVENTORY_MASTER where MATERIAL_ID = '" + id + "'");
			Object result = hibernateDao.getSession().createNativeQuery(sql.toString()).uniqueResult();
			return result != null ? ((Number) result).longValue() : 0L;
		} catch (Exception e) {
			LOG.info("" + e.getStackTrace(), e);
			return 0L;
		}
	}

	@SuppressWarnings("unchecked")
	public List<MaterialDto> getInventoryCountBasedOnSegments() {
		try {
			String sql = "select distinct SEGMENTS AS segment, ROUND(COALESCE(SUM(TOTAL_WEIGHT), 0),2) AS totalWeight, ROUND(COALESCE(SUM(AVAILABLE_WEIGHT), 0),2) AS availableWeight from INVENTORY_MASTER group by SEGMENTS";
			LOG.info("getMandiPricesLatestPriceByCrop===>"+sql);
			NativeQuery<MaterialDto> sqlQuery = hibernateDao.getSession().createNativeQuery(sql);
			sqlQuery.addScalar("segment", StandardBasicTypes.STRING);
			sqlQuery.addScalar("totalWeight", StandardBasicTypes.DOUBLE);	
			sqlQuery.addScalar("availableWeight", StandardBasicTypes.DOUBLE);	
			return sqlQuery.setResultTransformer(Transformers.aliasToBean(MaterialDto.class)).list();
		} catch (Exception e) {
			LOG.info("Exception in checkExistingRecordEntity========>" + e.getStackTrace(), e);
			return null;
		}

	}

	
	}