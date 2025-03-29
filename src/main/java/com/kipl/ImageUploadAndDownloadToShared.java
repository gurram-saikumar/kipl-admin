package com.kipl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jcifs.smb1.smb1.NtlmPasswordAuthentication;
import jcifs.smb1.smb1.SmbFile;
import jcifs.smb1.smb1.SmbFileOutputStream;


public class ImageUploadAndDownloadToShared {

	private static final Log LOG = LogFactory.getLog(ImageUploadAndDownloadToShared.class);
	private ImageUploadAndDownloadToShared() {
        throw new UnsupportedOperationException();
    }
	
	private static final String USER = "csd\\empover_user1";
	private static final String PASS = "Wsxzaq@4321";
	 @SuppressWarnings("resource")
	public static void copyFilesFromServerToShared(String sharedFolder, File file) {

		 try {
	       	 String path="smb://169.38.110.119//"+sharedFolder;
	       	 String fullPath="smb://169.38.110.119//"+sharedFolder+file.getName();
	       	 
	       	 NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",USER, PASS);
	       	 SmbFile smbFile = new SmbFile(path,auth);
	       	 
	       	if(!smbFile.exists())
            {
	       		smbFile.mkdirs();
            }
	       	
	    	SmbFile smbFile1 = new SmbFile(fullPath,auth);
	       	
	       	 LOG.info("\n\n====file.getPath()==="+file.getPath());
	       	 
	       	InputStream in = new BufferedInputStream(new FileInputStream(file));
	       	 
	       	 SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile1);
      	 
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
	
	public static void deleteFolder(File file){
		
		if(file.listFiles() != null ) {
	      for (File subFile : file.listFiles()) {
	         if(subFile.isDirectory()) {
	            deleteFolder(subFile);
	         } else {
	            subFile.delete();
	         }
	      }
		}
	      file.delete();
	   }
	 
}
