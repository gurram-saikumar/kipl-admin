package com.kipl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import jcifs.smb1.smb1.NtlmPasswordAuthentication;
import jcifs.smb1.smb1.SmbFile;
import jcifs.smb1.smb1.SmbFileOutputStream;

public class ImageUploadAndDownload {

	private static final Log LOG = LogFactory.getLog(ImageUploadAndDownload.class);
	
	private ImageUploadAndDownload() {
        throw new UnsupportedOperationException();
    }
	
	private static final String USER = "csd\\empover_user1";
	private static final String PASS = "Wsxzaq@4321";

	public static void fileUploadToPath(String sharedFolder, MultipartFile multipartFile) {

		try {
			LOG.info("sharedFolder ..." + sharedFolder);
			LOG.info("sharedFolder multipartFile ..." + multipartFile.getOriginalFilename());
			String path = "smb://10.162.87.198//" + sharedFolder;
			LOG.info(".....1..." + path);
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", USER, PASS);
			LOG.info("......2...");
			SmbFile smbFile = new SmbFile(path, auth);
			LOG.info("......3...");
			try(SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);){
			LOG.info("......4...");
			smbfos.write(multipartFile.getBytes());
			LOG.info("......5...");
			}
			LOG.info("fileUploadToPath ..completed !");

		} catch (Exception e) {
			LOG.info("Exception.fileUploadToPath............. : " + e.getCause());
		}
	}

	/*
	 * Image Download
	 */
	@SuppressWarnings("unused")
	public static InputStream fileDownloadToPath(File file, String fileName) {

		try {
			LOG.info("fileName ..." + fileName);
			String sharedFolder = "Empover";
			String url = "smb://169.38.110.119//Empover//CDI//diseaseImages//imageFiles//" + fileName;
			LOG.info("url ..." + url);
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, USER, PASS);
			SmbFile dir = new SmbFile(url, auth);
			LOG.info("AAA--------->" + dir.getPath());
			return dir.getInputStream();
		} catch (Exception e) {
			LOG.info("Exception.fileDownloadToPath............. : " + e.getStackTrace(), e);
			return null;
		}
	}

	/*
	 * Excel Upload Code
	 * 
	 */
	@SuppressWarnings("resource")
	public static void copyFilesFromServerToShared(String sharedFolder, File file) {

		try {
			String path = "smb://169.38.110.119//" + sharedFolder;
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", USER, PASS);
			SmbFile smbFile = new SmbFile(path, auth);
			LOG.info("\n\n====file.getPath()===" + file.getPath());
			LOG.info("File ready 1");
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
			LOG.info("File ready 3");
			byte[] buffer = new byte[4096];
			int len = 0; // Read length
			while ((len = in.read(buffer, 0, buffer.length)) != -1) {
				smbfos.write(buffer, 0, len);
			}
			LOG.info("FileUploaded SucessFully");
			smbfos.close();

		} catch (Exception e) {
			LOG.info(e.getStackTrace(), e);
		}

	}

	public static void fileUploadToPathV2(String sharedFolder) {

		try {
			String path = "smb://10.162.87.198//" + sharedFolder;
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", USER, PASS);
			LOG.info("Connection Path" + path);
			SmbFile remoteFile = new SmbFile(path, auth);
			LOG.info("Connection Opend");
			try(InputStream in = new BufferedInputStream(new FileInputStream(sharedFolder));){
			LOG.info("File ready 2");
			try(OutputStream smbfos = new SmbFileOutputStream(remoteFile);){
			LOG.info("File ready to upload");
			int bufferSize = 5096 * 10;
			LOG.info("File ready 1");
			byte[] b = new byte[bufferSize];
			int noOfBytes = 0;
			LOG.info("File ready 2");
			while ((noOfBytes = in.read(b)) != -1) {
				smbfos.write(b, 0, noOfBytes);
			}
			}
			}
			LOG.info("FileUploaded SucessFully");
		} catch (Exception e) {
			LOG.info("Exception.fileUploadToPath............. : " + e);
		}
	}

}
