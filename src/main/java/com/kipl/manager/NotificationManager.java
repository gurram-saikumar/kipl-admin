package com.kipl.manager;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kipl.common.GenericManager;
import com.kipl.models.Notifications;
import com.kipl.models.UserMaster;




@Repository
public class NotificationManager extends GenericManager<Notifications, Long> {

	private static final Logger LOG = LogManager.getLogger(NotificationManager.class);

	public NotificationManager() {
		super(Notifications.class);
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<Notifications> getNotifications(UserMaster user) {
	    List<Notifications> list = new ArrayList<Notifications>();
		try {
			StringBuilder string = new StringBuilder();
			String sql = "";
			LOG.info("user=====>" + user);
			if (user != null) {
				LOG.info("enter into user=====>");
				string.append(" and mobileNumber = '" + user.getMobileNumber() + "' and user.id = " + user.getId()+ " order by id desc");
			} 
			if (user != null ) {
				sql = "FROM Notifications WHERE status = true";
				if (string.length() > 0) {
					sql += " and 1=1 " + string.toString();
				}
			}
			LOG.info("getNotifications===>" + sql);
			list = find(sql);
		} catch (Exception e) {
	        LOG.info("Exception getNotifications======>" + e.getStackTrace(), e);
	        list = null;
	    }
	    return list;
	}
	
}