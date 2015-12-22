package com.zf.fclog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFileChooser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class MainLayout {
	static String Account;
	static String password;
	static String hour;
	static String min;
	static String FtpPath;
	static String LogPath;
	static String Ftp;
	static String FtpUsername;
	static String FtpPassword;
	static Text text;
	static Text text2;
	static Text text3;
	static Text text32;
	static Text text4;
	static Text text5;
	static Text textFtp;
	static Text textFtp2;
	static Text textFtp3;
	static TimerTask timerTask;
	
	static {
		File savePath = new File("./Log"); 
		if (!savePath.exists()) { 
			savePath.mkdir();
		}
		File savePath2 = new File("./info"); 
		if (!savePath2.exists()) { 
			savePath2.mkdir();
		}
		boolean start = readPath();
		if(start) {
			Account = "tim.zheng";
			password = "888888";
			hour = "03";
			min = "00";
			FtpPath = "/IUNILog/Log";
			LogPath = "./Log";
			Ftp = "18.8.5.99";
			FtpUsername = "ftp-aurora";
			FtpPassword = "aurora";
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 第一部分：初始化窗口
		final Display display = new Display(); // 创建Display类的实例
		final Shell shell = new Shell(display, SWT.MIN); // 创建该Display的shell类实例,SWT.MIN无法最大化
		shell.setBounds(500, 500, 550, 480); // 设置窗口大小
		shell.setText("日志上报提单"); // 设置窗口标题
		shell.setImage(new Image(display, "./bug.jpg")); // 给窗口设置图片
		//在当前窗口中创建第一个分组
		Group group = new Group(shell,SWT.VERTICAL);
		group.setBounds(10, 10, 250, 100);
		group.setText("设置禅道账号");
		Label label = new Label(group,SWT.NONE);
		label.setText("禅道账号:");
		label.setBounds(6, 20, 60, 20);
		text = new Text(group,SWT.BORDER);
		text.setText(Account);
		text.setBounds(80, 20, 130, 20);
		Label label2 = new Label(group,SWT.NONE);
		label2.setText("禅道密码:");
		label2.setBounds(6, 50, 60, 20);
		text2 = new Text(group,SWT.PASSWORD);
		text2.setText(password);
		text2.setBounds(80, 50, 130, 20);
		//创建第二个分组
		Group group2 = new Group(shell,SWT.VERTICAL);
//		group2.setBounds(280, 10, 250, 100);
		group2.setBounds(10, 110, 250, 100);
		group2.setText("设置其它参数");
		Label label3 = new Label(group2,SWT.NONE);
		label3.setText("运行节点:");
		label3.setBounds(6, 20, 60, 20);
		text3 = new Text(group2,SWT.BORDER);
		text3.setText(hour);
		text3.setBounds(80, 20, 25, 20);
		Label label32 = new Label(group2,SWT.NONE);
		label32.setText("点");
		label32.setBounds(106, 20, 15, 20);
		text32 = new Text(group2,SWT.BORDER);
		text32.setText(min);
		text32.setBounds(121, 20, 25, 20);
		Label label33 = new Label(group2,SWT.NONE);
		label33.setText("分");
		label33.setBounds(147, 20, 15, 20);
		
		Label label4 = new Label(group2,SWT.NONE);
		label4.setText("FTP路径:");
		label4.setBounds(6, 50, 60, 20);
		text4 = new Text(group2,SWT.BORDER);
		text4.setText(FtpPath);
		text4.setBounds(80, 50, 150, 20);
		
		Label label5 = new Label(shell,SWT.NONE);
		label5.setText("本地保存路径:");
		label5.setBounds(10, 220, 80, 20);
		text5 = new Text(shell,SWT.BORDER);
		text5.setText(LogPath);
		text5.setBounds(90, 220, 320, 20);
		Button button = new Button(shell,SWT.NONE);
		button.setText("选择目录");
		button.setBounds(415, 220, 80, 20);
		
		//创建第三个分组
		Group group3 = new Group(shell,SWT.VERTICAL);
		group3.setBounds(280, 10, 250, 200);
		group3.setText("FTP参数设置");
		Label labelFtp = new Label(group3,SWT.NONE);
		labelFtp.setText("FTP地址:");
		labelFtp.setBounds(6, 20, 60, 20);
		textFtp = new Text(group3,SWT.BORDER);
		textFtp.setText(Ftp);
		textFtp.setBounds(80, 20, 130, 20);
		Label ftpName = new Label(group3,SWT.NONE);
		ftpName.setText("FTP用户名:");
		ftpName.setBounds(6, 50, 60, 20);
		textFtp2 = new Text(group3,SWT.BORDER);
		textFtp2.setText(FtpUsername);
		textFtp2.setBounds(80, 50, 130, 20);
		Label ftpPassword = new Label(group3,SWT.NONE);
		ftpPassword.setText("FTP密码:");
		ftpPassword.setBounds(6, 80, 60, 20);
		textFtp3 = new Text(group3,SWT.PASSWORD);
		textFtp3.setText(FtpPassword);
		textFtp3.setBounds(80, 80, 130, 20);
		
		
		//创建第四个分组
		Group group4 = new Group(shell,SWT.VERTICAL);
		group4.setBounds(10, 290, 520, 50);
		group4.setText("提bug情况");
		Label bugShow = new Label(group4,SWT.NONE);
		bugShow.setAlignment(SWT.CENTER); //文字显示在中间
//		bugShow.setBackground(display.getSystemColor(SWT.COLOR_GRAY)); 
		bugShow.setForeground(display.getSystemColor(SWT.COLOR_DARK_RED)); 
		bugShow.setText("2015-10-21 共提交16个bug");
		bugShow.setFont(new Font(display,"宋体",16,SWT.NORMAL)); //设置字体字号
		bugShow.setBounds(10, 15, 500, 30);
		
		Button start = new Button(shell,SWT.NONE);
		start.setText("运行");
		start.setBounds(150, 250, 80, 35);
		Button stop = new Button(shell,SWT.NONE);
		stop.setText("停止");
		stop.setBounds(320, 250, 80, 35);
		
		//创建第五个分组
		Group group5 = new Group(shell,SWT.VERTICAL);
		group5.setBounds(10, 350, 520, 80);
		group5.setText("状态栏");
		final Label label6 = new Label(group5,SWT.NONE);
		label6.setAlignment(SWT.CENTER); 
		label6.setBackground(display.getSystemColor(SWT.COLOR_GRAY)); 
		label6.setForeground(display.getSystemColor(SWT.COLOR_DARK_BLUE)); 
		label6.setText("待命中....");
		label6.setFont(new Font(display,"宋体",20,SWT.NORMAL)); //设置字体字号
		label6.setBounds(10, 20, 500, 50);
		
		// 监听按钮
		button.addSelectionListener(new SelectionAdapter() { //选择目录
			public void widgetSelected(SelectionEvent event) {
				System.out.println("选择目录");
				String path = folderDig(shell);
				if(path!=null) {
					text5.setText(path);
				}
			}
		});
		start.addSelectionListener(new SelectionAdapter() { // 运行
			public void widgetSelected(SelectionEvent event) {
				if((label6.getText()).equals("待命中....")) {
					System.out.println("开始");
					//获取昨天的时间
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					String name = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
					final String fileName = "FC_LOG_"+name;
					System.out.println("昨天时间："+fileName);
					
					label6.setText("运行中....");
					label6.setBackground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
					saveSetting();
					// 一天的毫秒数
					long daySpan = 24 * 60 * 60 * 1000;
					// 规定的每天运行时间
					final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd '"+text3.getText().trim()+":"+text32.getText().trim()+":00'");
					System.out.println("yyyy-MM-dd '"+text3.getText().trim()+":"+text32.getText().trim()+":00'");
					// 首次运行时间
					Date startTime = null;
					try {
						startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(sdf.format(new Date()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					// 如果今天的已经过了 首次运行时间就改为明天
					if (System.currentTimeMillis() > startTime.getTime()) { 
						startTime = new Date(startTime.getTime() + daySpan);
					}
					final ExamineFtpFile eff = new ExamineFtpFile(text4.getText().trim()+"/",textFtp.getText().trim(),textFtp2.getText().trim(),
							textFtp3.getText().trim(),fileName,text5.getText().trim()+"/");
					final String zhanghao = text.getText().trim();
					final String mima = text2.getText().trim();
					Timer timer = new Timer();
					timerTask = new TimerTask() {
						@Override
						public void run() {
							// 要执行的代码
							System.out.println("运行");
							eff.startRun(zhanghao,mima);
						}
					};
					// 以每24小时执行一次
					timer.scheduleAtFixedRate(timerTask, startTime, daySpan);
				}
			}
		});
		stop.addSelectionListener(new SelectionAdapter() {  //停止
			public void widgetSelected(SelectionEvent event) {
				System.out.println("停止");
				timerTask.cancel(); //停止TimerTask运行
				label6.setBackground(display.getSystemColor(SWT.COLOR_DARK_RED));
				label6.setText("停止");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				label6.setBackground(display.getSystemColor(SWT.COLOR_GRAY)); 
				label6.setText("待命中....");
			}
		});

		shell.layout();
		shell.open();//打开窗口
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	//打开文件选择界面
	protected static String folderDig(Shell parent) {
		// 新建文件夹（目录）对话框
		DirectoryDialog folderdlg = new DirectoryDialog(parent);
		// 设置文件对话框的标题
		folderdlg.setText("文件选择");
		// 设置初始路径
		folderdlg.setFilterPath("SystemDrive");
		// 设置对话框提示文本信息
		folderdlg.setMessage("请选择相应的文件夹");
		// 打开文件对话框，返回选中文件夹目录
		String selecteddir = folderdlg.open();
		System.out.println("您选中的文件夹目录为：" + selecteddir);
		return selecteddir;
	}
	
	// 获取系统时间
	private static String getTime(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(time);
		String Time = dateFormat.format(new Date());
		return Time;
	}
	
	//保存设置
	private static void saveSetting() {
		try {
			File re = new File("./info/settingInfo.txt");
			if (!re.exists()) { // 目录不存在的话就创建目录
				re.createNewFile();
			}
			FileOutputStream is = new FileOutputStream(re);
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(is));
			br.write(text.getText().trim() + "\n"
			        + text2.getText().trim()+ "\n"
					+ text3.getText().trim() + "\n"
					+ text32.getText().trim() + "\n"
					+ text4.getText().trim() + "\n"
					+ text5.getText().trim() + "\n"
					+ textFtp.getText().trim() + "\n"
					+ textFtp2.getText().trim() + "\n"
					+ textFtp3.getText().trim() + "\n");
			br.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	// 读取保存数据
	private static boolean readPath() {
		boolean file = false;
		try {
			File re = new File("./info/settingInfo.txt");
			if (re.exists()) {
				FileInputStream is = new FileInputStream(re);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String a = null;
				int i = 1;
				while ((a = br.readLine()) != null) {
					switch (i) {
					case 1:
						Account = a.toString();
						break;
					case 2:
						password = a.toString();
						break;
					case 3:
						hour = a.toString();
						break;
					case 4:
						min = a.toString();
						break;
					case 5:
						FtpPath = a.toString();
						break;
					case 6:
						LogPath = a.toString();
						break;
					case 7:
						Ftp = a.toString();
						break;
					case 8:
						FtpUsername = a.toString();
						break;
					case 9:
						FtpPassword = a.toString();
						break;
					}
					i++;
				}
			} else {
				file = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
}
