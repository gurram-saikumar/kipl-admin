package com.kipl.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.common.HibernateDao;
import com.kipl.models.MaterialRequestMaster;
import com.kipl.models.UserMaster;

@Repository
public class MaterialRequestManager extends GenericManager<MaterialRequestMaster, Long>{

	private static final Logger LOG = LogManager.getLogger(MaterialRequestManager.class);
	
	@Autowired
	@Qualifier("hibernateDao")
	protected HibernateDao hibernateDao;

	public MaterialRequestManager() {
		super(MaterialRequestMaster.class);
	}

	@SuppressWarnings("unchecked")
	public List<MaterialRequestMaster> getMaterialRequestList(UserMaster user) {

		List<MaterialRequestMaster> list = new ArrayList<MaterialRequestMaster>();
		try {
			list = find("from MaterialRequestMaster where status=true and requesterId.id="+user.getId()+" order by id desc");
		} catch (Exception e) {
			LOG.info("<=== Exception getMaterialRequestList ===>" + e.getStackTrace(), e); 
			list = null;
		}
		return list;
	}

	public Long getLatestRequestId() {
		try {
			StringBuffer sql = new StringBuffer("select requestId from MaterialRequestMaster order by id desc");
			Number result = (Number) hibernateDao.getSession().createQuery(sql.toString()).uniqueResult();
			return result != null ? result.longValue() : 0L;
		} catch (Exception e) {
			LOG.error("Error fetching rewarded points for mobile number: ", e);
			return 0L;
		}
	}
	
	   public Long  getMaxRequestId()
	    {
	    	try {
				LOG.info("MAX QUERY---->FROM MaterialRequestMaster ORDER BY Id DESC");
				String sql=" select max(id) from MaterialRequestMaster";
				return (Long) getMaxValue(sql);
			} catch (Exception e) {
				LOG.info("==Exception=="+e.getStackTrace(),e);
				return null;
			}
	    }

		public Long getMaterialRequestCount(Long userId, Long roleId) {
			try {
				String hql = "";
				if(roleId==1 || roleId==2)
					hql = "select count(*) from MaterialRequestMaster";
				else 
					hql = "select count(*) from MaterialRequestMaster where  requesterId.id="+userId+"";
				LOG.info("getMaterialRequestCount==>" + hql);
				Number result = (Number) hibernateDao.getSession().createQuery(hql).uniqueResult();
				return result != null ? result.longValue() : 0L;
			} catch (Exception e) {
				LOG.info("==Exception==" + e.getStackTrace(), e);
				return 0L;
			}
		}

	public Long getMaterialPendingCount(Long userId, Long roleId) {
		try {
			String hql = "";
			if(roleId==1 || roleId==2)
				hql = "select count(*) from MaterialRequestMaster where  requestStatus in ('Submitted')";
			else 
				hql = "select count(*) from MaterialRequestMaster where  requesterId.id="+userId+"  and requestStatus in ('Submitted')";
			LOG.info("getMaterialPendingCount==>" + hql);
			Number result = (Number) hibernateDao.getSession().createQuery(hql).uniqueResult();
			return result != null ? result.longValue() : 0L;
		} catch (Exception e) {
			LOG.info("==Exception==" + e.getStackTrace(), e);
			return 0L;
		}
	}

	public Long getMaterialIssuedCount(Long userId,Long roleId) {
		try {
			String hql = "";
			if(roleId==1 || roleId==2)
				hql = "select count(*) from MaterialRequestMaster where  requestStatus in ('Partial Completed')";
			else 
				 hql = "select count(*) from MaterialRequestMaster where  requesterId.id="+userId+" and requestStatus in ('Partial Completed')";
			LOG.info("getMaterialIssuedCount==>" + hql);
			Number result = (Number) hibernateDao.getSession().createQuery(hql).uniqueResult();
			return result != null ? result.longValue() : 0L;
		} catch (Exception e) {
			LOG.info("==Exception==" + e.getStackTrace(), e);
			return 0L;
		}
	}

	public Long getMaterialRequestCompletedCount(Long userId,Long roleId) {
		try {
			String hql = "";
			if(roleId==1 || roleId==2)
				hql = "select count(*) from MaterialRequestMaster where  requestStatus='Completed'";
			else 
				 hql = "select count(*) from MaterialRequestMaster where  requesterId.id="+userId+" and requestStatus='Completed'";
			LOG.info("getMaterialRequestCompletedCount==>" + hql);
			Number result = (Number) hibernateDao.getSession().createQuery(hql).uniqueResult();
			return result != null ? result.longValue() : 0L;
		} catch (Exception e) {
			LOG.info("==Exception==" + e.getStackTrace(), e);
			return 0L;
		}
	}
}
