package com.kipl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import jcifs.smb1.smb1.NtlmPasswordAuthentication;
import jcifs.smb1.smb1.SmbFile;
import jcifs.smb1.smb1.SmbFileOutputStream;


public class FileUploadDownload 
{

	private static final Log LOG = LogFactory.getLog(FileUploadDownload.class);
	/*
	 * Image Upload
	 */
	private static final String USER = "csd\\empover_user1";
	private static final String PASS = "Wsxzaq@4321";
	public void sampleMethod()
	{
		LOG.info("Kiran Upload");
	}
	
	 public static void fileUploadToPath(String sharedFolder,MultipartFile multipartFile,String excelUploadRoot)
	    {
			try 
			{
				 LOG.info(":::File Uploading Started:::");
				 LOG.info(":::sharedFolder multipartFile :::"+ multipartFile.getOriginalFilename());
		       	 String path= excelUploadRoot+sharedFolder;
		       	 LOG.info("::Shared Folder Path::"+ path);
		       	 NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",USER, PASS);
		       	 SmbFile smbFile = new SmbFile(path,auth);
		        try (SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile)) {
		        	smbfos.write(multipartFile.getBytes());
	                LOG.info("File Upload Completed");
	            }
			} catch (Exception e)
			{
				 LOG.info(":::Exception While File Uploading:::"+e.getStackTrace(),e);
			}
	    }
	    
	    public static InputStream fileDownloadToPath(String fileName,String rootPath,String excelUploadPath)
	    {
			try 
			{
				LOG.info("fileName ..."+ fileName);
		       	String url= rootPath+excelUploadPath+"//"+fileName;
		       	LOG.info("File URL--->"+ url);
				NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, USER, PASS);
				SmbFile dir = new SmbFile(url, auth);
				return dir.getInputStream();
			} catch (Exception e) 
			{
				 LOG.info("Exception.fileDownloadToPath............. : "+e.getStackTrace(),e);
				 return null;
			}
		}
	    
	    @SuppressWarnings("resource")
		public static void excelCopyFromServerToShared(String sharedFolder, File file) 
	    {
			 try {
				
		       	 String path=sharedFolder;
		       	 NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",USER, PASS);
		       	 SmbFile smbFile = new SmbFile(path,auth);
		       	 
		       	 LOG.info("\n\n====file.getPath()==="+file.getPath());
		       	LOG.info("\n\n====path==="+path);
		       	 InputStream in = new BufferedInputStream(new FileInputStream(file));
		       	 SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
		       	byte[] buffer = new byte[4096];
		          int len = 0; //Read length
		          while ((len = in.read(buffer, 0, buffer.length)) != -1) {
	          	smbfos.write(buffer, 0, len);
	          }
	          smbfos.close();
				 
			} catch (Exception e) {
				LOG.info(e.getStackTrace(), e);
			}

		}
	    
	    public static boolean createFolder(String sharedFolder){
			
			try {
		       	 String path="smb://169.38.110.119//"+sharedFolder;
		       	 NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",USER, PASS);
		       	 SmbFile smbFile = new SmbFile(path,auth);
		       	 if(smbFile.exists()){
		       		LOG.info("Folder Already Exists....:"+path); 
		       	 }else{
		       		smbFile.mkdir(); 
		       	  LOG.info("createFolder ..completed !");
		       	 }
		       	 return true;
			} catch (Exception e) {
				 LOG.info("Exception.createFolder............. : "+e.getMessage());
				 return false;
			}
		}

}
