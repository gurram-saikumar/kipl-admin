package com.kipl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

/**
 * @author Balaraju.g
 *
 */
public class Constants
{
	private static final String ACTION_1 = "This is a utility class and cannot be instantiated";  // Compliant
	  // Private constructor to prevent instantiation
    private Constants() {
        throw new UnsupportedOperationException(ACTION_1);
    }
    
    protected static Map<String, HttpSession> customerLoggedUsers = Collections.synchronizedMap(new HashMap<String, HttpSession>());
    public static final String USERSESSION = "USERSESSION";
    public static final String SCHOOLINFO = "SCHOOLINFO";
    public static final String DEFAULTLANGUAGECODE = "ENG";
    public static class ErrorCodes
    {
        private ErrorCodes() {
            throw new UnsupportedOperationException(ACTION_1);
        }
        public static final String SUCCESS_MSG = "Success";
        public static final int SUCCESS_CODE = 100;
        public static final String FAIL_MSG = "Fail";
        public static final int FAIL_CODE = 101;
        public static final String INVALID_INPUT_MSG = "Invalid input data";
        public static final int INVALID_INPUT_CODE = 102;
        public static final String NO_RECORD_FOUND_MSG = "No record found";
        public static final int NO_RECORD_FOUND_CODE = 103; 
        public static final int SERVER_ECHLN_ISSUE_CODE = 104;
        public static final int SERVER_CASESTS_ISSUE_CODE = 105; 
        public static final int SERVER_ISSUE_CODE = 106; 
    } 
    

    public static class Status
    {
    	private Status() {
            throw new UnsupportedOperationException(ACTION_1);
        }
    	
        public static final int OPEN = 1;
        public static final int ASSIGNED = 2;
        public static final int CLOSED = 3;
        
        public static final int ACTIVE_KEY = 1;
        public static final int INACTIVE_KEY = 0;
        public static final String ACTIVE = "Active";
        public static final String INACTIVE = "In Active";
    }

   
    public static class StatusText
    {
    	private StatusText() {
            throw new UnsupportedOperationException(ACTION_1);
        }
        public static final String OPEN = "Open";
        public static final String ASSIGNED = "Assigned";
        public static final String CLOSED = "Closed";
    }
    
    public static class MartialStatus
    {
    	private MartialStatus() {
            throw new UnsupportedOperationException(ACTION_1);
        }
        public static final String MARRIED = "Married";
        public static final String SINGLE = "Single";
    }

    public static class Gender
    {
    	private Gender() {
            throw new UnsupportedOperationException(ACTION_1);
        }
        public static final String MALE = "Male";
        public static final String FEMALE = "Female";
    }
    
    public static class AuthenticationStatus   {
    	private AuthenticationStatus() {
            throw new UnsupportedOperationException(ACTION_1);
        }
     	public static final String ASSIGNA	= "ASSIGNED";
     	public static final String REMOVEA	= "REMOVED";
    	
    }
    public static class DeviceType   
    {
    	private DeviceType() {
            throw new UnsupportedOperationException(ACTION_1);
        }
     	public static final String ANDROID	= "Android";
     	public static final String IOS	= "iOS";
     	public static final String WINDOWS	= "Windows";
    }
    
    public static class PlatformType   
    {
    	private PlatformType() {
            throw new UnsupportedOperationException(ACTION_1);
        }
     	public static final String ANDROID	= "Android";
     	public static final String IOS	= "iOS";
     	public static final String WEB	= "Web";
    }
    
    public static class UserStatus
    {
    	private UserStatus() {
            throw new UnsupportedOperationException(ACTION_1);
        }
        public static final String OPEN = "Open";
    }
    public static class OTP
       {
    	private OTP() {
            throw new UnsupportedOperationException(ACTION_1);
        }
       	public static final String EXPIRED	= "EXPIRED";
        	public static final String VERIFIED	= "VERIFIED";
       }
    
    public static class Action
    {
    	private Action() {
            throw new UnsupportedOperationException(ACTION_1);
        }
        public static final String DOWNLOAD = "DOWNLOAD";
        public static final String UPLOAD = "UPLOAD";
    }
    
    public static class Roles
    {
    	private Roles() {
            throw new UnsupportedOperationException(ACTION_1);
        }
    	public static final long SUPER_ADMIN = 1 ;
        public static final long ZM = 2 ;
        public static final long RM =3;
        public static final long TM = 4;
        public static final long MDO= 5;
        public static final long RETAILER=6;
        public static final long DISTRIBUTOR =7;
        public static final long EMPLOYEE =8;
        public static final long ADMIN =9;
        public static final long CUMM =10;

    }
    
    public static class RoleNames
    {
    	private RoleNames() {
            throw new UnsupportedOperationException(ACTION_1);
        }
    	public static final String SUPER_ADMIN = "Super Admin" ;
        public static final String ZM = "Zonal Manager" ;
        public static final String RM ="Regional Manager";
        public static final String TM = "Territory Manager";
        public static final String MDO= "MDO";
        public static final String RETAILER="Retailer";
        public static final String DISTRIBUTOR ="Distributor";
        public static final String EMPLOYEE ="Employee";
        public static final String ADMIN ="Admin";
        public static final String CUMM ="CUMM";

    }
    
    public static class Notification
    {
    	private Notification() {
            throw new UnsupportedOperationException(ACTION_1);
        }
    	public static final long SILENT_NOTIFICATION = 1 ;
        public static final long NORMAL_NOTIFICATION = 2 ;
        public static final long PARENT_MODE =1;
        public static final long CHILD_MODE = 2;
        public static final long APP_ID= 0;
        public static final long DEVICE_MODE=4;
        public static final int FREQUENCY =3;
    }
    public static String COUPON_CODE_TYPE_A ="A";
    public static String COUPON_CODE_TYPE_B ="B";
    
    public static String SEASON_NAME_KHARIF ="Kharif";
    public static String SEASON_NAME_RABI ="Rabi";
    
    public static String EKYC_STATUS_PENDING ="pending";
    public static String EKYC_STATUS_BY_PASS ="byPass";
    public static String EKYC_STATUS_APPROVE ="Approve";
    public static String EKYC_STATUS_REJECT ="reject";
    
    public static String APPLICATION_NAME_VYAPAR_MITRA ="VyaparMitra";
    public static String APPLICATION_NAME_SUBEEJ_KISAN ="SubeejKisan";
    public static String NOTIFICATION_TYPE ="productCard";

}
