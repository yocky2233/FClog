package com.zf.fclog;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class ExamineFtpFile {
	private FTPClient ftp;
	private String ftpPath;
	private String addr;
	private String username;
	private String password;
	private String fileName;
	private String downloadPath;

	ExamineFtpFile(String ftpPath,String addr,String username, String password,String fileName,String downloadPath) {
		this.ftpPath = ftpPath;
		this.addr = addr;
		this.username = username;
		this.password = password;
		this.fileName = fileName+".txt";
		this.downloadPath = downloadPath;
	}
	
	
	public void startRun() {
		boolean Login = false;
		try {
			Login = connect(addr,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("���������"+Login);
		if(Login) {
			boolean exises = examineFile();
			System.out.println("�Ƿ����ļ���"+exises);
			if(exises) {
				//������־
				downloadFile(fileName,downloadPath,ftpPath);
				//������־
				
			}
		}
	}

	/**
	 * @param addr ��ַ
	 * @param port �˿ں�
	 * @param username �û���
	 * @param password ����
	 * @return
	 * @throws Exception
	 */
	private boolean connect(String addr,String username, String password) throws Exception {
		boolean result = false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(addr, 21);
		ftp.login(username, password);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return result;
		}
//		ftp.changeWorkingDirectory(path);
        //��ӡ�ļ�
		result = true;
		System.out.println("���ӳɹ�");
		return result;
	}
	
	private boolean examineFile() {
		boolean start = false;
		FTPFile[] ftpfiles = null;
		try {
			ftpfiles = ftp.listFiles(ftpPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("�ļ�����"+fileName);
		for (FTPFile file : ftpfiles) {
			if(fileName.equals(file.getName().trim())) {
				start = true;
				System.out.println("�ļ�����");
			}
			System.out.println(file.getName().trim());
		}
		return start;
	}

	/***
	 * �����ļ�
	 * 
	 * @param remoteFileName �������ļ�����
	 * @param localDires ���ص������ĸ�·����
	 * @param remoteDownLoadPath ftp�ļ�����·��
	 * */
	public boolean downloadFile(String remoteFileName, String localDires,
			String remoteDownLoadPath) {
		String strFilePath = localDires + remoteFileName;
		BufferedOutputStream outStream = null;
		boolean success = false;
		try {
			ftp.changeWorkingDirectory(remoteDownLoadPath);
			outStream = new BufferedOutputStream(new FileOutputStream(
					strFilePath));
			System.out.println("��ʼ����");
			success = ftp.retrieveFile(remoteFileName, outStream);
			if (success == true) {
				System.out.println("���سɹ�");
				return success;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����ʧ��");
		} finally {
			if (null != outStream) {
				try {
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (success == false) {
			System.out.println("����ʧ��");
		}
		return success;
	}

	public static void main(String[] args) throws Exception {
		ExamineFtpFile t = new ExamineFtpFile("/IUNILog/","18.8.5.99","ftp-aurora", "aurora","123","f://");
		t.startRun();
//		t.connect("18.8.5.99","ftp-aurora", "aurora");
//		t.downloadFile("123.txt", "f://", "/IUNILog/");
		System.out.println("�������");
	}

}
