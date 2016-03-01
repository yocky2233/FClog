package com.zf.fclog;

import java.awt.Label;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ExamineFtpFile {
	private FTPClient ftp;
	private String ftpPath;
	private String addr;
	private String username;
	private String password;
	private String fileName;
	private String downloadPath;
	private Shell shell;
	private org.eclipse.swt.widgets.Label bugShow;

	ExamineFtpFile(String ftpPath,String addr,String username, String password,String downloadPath,Shell shell,org.eclipse.swt.widgets.Label bugShow2) {
		this.ftpPath = ftpPath;
		this.addr = addr;
		this.username = username;
		this.password = password;
//		this.fileName = fileName+".xls";
		this.downloadPath = downloadPath;
		this.shell = shell;
		this.bugShow = bugShow2;
	}
	
	//过滤出对应内容后提单
	public void startRun(String CDaccount,String CDpassword,String Name) {
//		System.out.println("账号："+CDaccount+"密码："+CDpassword);
		fileName = Name+".xls";
		boolean Login = false;
		try {
			Login = connect(addr,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("连接情况："+Login);
		if(Login) {
			boolean exises = examineFile();
//			System.out.println("是否有文件："+exises);
			if(exises) {
				//下载日志
				downloadFile(fileName,downloadPath,ftpPath);
				//过滤日志
				Filter f = new Filter();
				f.readFile(fileName,CDaccount,CDpassword,downloadPath,shell,bugShow);
			}else {
				JOptionPane.showMessageDialog(null, "服务器未找到"+fileName+"对应文件！", "提示",JOptionPane.WARNING_MESSAGE); 
//				System.out.println("服务器没有对应文件");
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
		System.out.println("地址："+addr+" "+"账号："+username+" "+"密码："+password);
		boolean result = false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(addr, 21);
		ftp.login(username, password);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
//			System.out.println("失败");
			return result;
		}
//		ftp.changeWorkingDirectory(path);
        //打印文件
		result = true;
//		System.out.println("连接成功");
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
//		System.out.println("文件名："+fileName);
		for (FTPFile file : ftpfiles) {
			if(fileName.equals(file.getName().trim())) {
				start = true;
//				System.out.println("文件存在");
			}
//			System.out.println(file.getName().trim());
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
//			System.out.println("开始下载");
			success = ftp.retrieveFile(remoteFileName, outStream);
			if (success == true) {
//				System.out.println("下载成功");
				return success;
			}
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("下载失败");
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
//			System.out.println("下载失败");
		}
		return success;
	}

//	public static void main(String[] args) throws Exception {
//		ExamineFtpFile t = new ExamineFtpFile("/IUNILog/","18.8.5.99","ftp-aurora", "aurora","123","f://");
//		t.startRun("aaa","123");
//		t.connect("18.8.5.99","ftp-aurora", "aurora");
//		t.downloadFile("123.txt", "f://", "/IUNILog/");
//		System.out.println("下载完毕");
//	}

}
