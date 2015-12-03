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
		System.out.println("连接情况："+Login);
		if(Login) {
			boolean exises = examineFile();
			System.out.println("是否有文件："+exises);
			if(exises) {
				//下载日志
				downloadFile(fileName,downloadPath,ftpPath);
				//过滤日志
				
			}
		}
	}

	/**
	 * @param addr 地址
	 * @param port 端口号
	 * @param username 用户名
	 * @param password 密码
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
        //打印文件
		result = true;
		System.out.println("连接成功");
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
		System.out.println("文件名："+fileName);
		for (FTPFile file : ftpfiles) {
			if(fileName.equals(file.getName().trim())) {
				start = true;
				System.out.println("文件存在");
			}
			System.out.println(file.getName().trim());
		}
		return start;
	}

	/***
	 * 下载文件
	 * 
	 * @param remoteFileName 待下载文件名称
	 * @param localDires 下载到当地哪个路径下
	 * @param remoteDownLoadPath ftp文件所在路径
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
			System.out.println("开始下载");
			success = ftp.retrieveFile(remoteFileName, outStream);
			if (success == true) {
				System.out.println("下载成功");
				return success;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("下载失败");
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
			System.out.println("下载失败");
		}
		return success;
	}

	public static void main(String[] args) throws Exception {
		ExamineFtpFile t = new ExamineFtpFile("/IUNILog/","18.8.5.99","ftp-aurora", "aurora","123","f://");
		t.startRun();
//		t.connect("18.8.5.99","ftp-aurora", "aurora");
//		t.downloadFile("123.txt", "f://", "/IUNILog/");
		System.out.println("下载完毕");
	}

}
