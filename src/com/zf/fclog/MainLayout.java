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
	static Text text;
	static Text text2;
	static Text text3;
	static Text text32;
	static Text text4;
	static Text text5;
	
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
			FtpPath = "/IUNILog/Log/";
			LogPath = "./Log";
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 第一部分：初始化窗口
		final Display display = new Display(); // 创建Display类的实例
		final Shell shell = new Shell(display, SWT.MIN); // 创建该Display的shell类实例,SWT.MIN无法最大化
		shell.setBounds(500, 500, 550, 400); // 设置窗口大小
		shell.setText("日志上报提单"); // 设置窗口标题
		shell.setImage(new Image(display, "./th_Tumblr_Black_Pen.png")); // 给窗口设置图片
		//在当前窗口中创建分组
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
		group2.setBounds(280, 10, 250, 100);
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
		label5.setBounds(10, 130, 80, 20);
		text5 = new Text(shell,SWT.BORDER);
		text5.setText(LogPath);
		text5.setBounds(90, 130, 320, 20);
		Button button = new Button(shell,SWT.NONE);
		button.setText("选择目录");
		button.setBounds(415, 130, 80, 20);
		
		//创建第三个分组
		Group group3 = new Group(shell,SWT.VERTICAL);
		group3.setBounds(10, 160, 520, 50);
		group3.setText("提bug情况");
		Label bugShow = new Label(group3,SWT.NONE);
		bugShow.setAlignment(SWT.CENTER); //文字显示在中间
//		bugShow.setBackground(display.getSystemColor(SWT.COLOR_GRAY)); 
		bugShow.setForeground(display.getSystemColor(SWT.COLOR_DARK_RED)); 
		bugShow.setText("2015-10-21 共提交16个bug");
		bugShow.setFont(new Font(display,"宋体",16,SWT.NORMAL)); //设置字体字号
		bugShow.setBounds(10, 15, 500, 30);
		
		Button start = new Button(shell,SWT.NONE);
		start.setText("运行");
		start.setBounds(150, 230, 80, 35);
		Button stop = new Button(shell,SWT.NONE);
		stop.setText("停止");
		stop.setBounds(320, 230, 80, 35);
		
		//创建第四个分组
		Group group4 = new Group(shell,SWT.VERTICAL);
		group4.setBounds(10, 280, 520, 80);
		group4.setText("状态栏");
		final Label label6 = new Label(group4,SWT.NONE);
		label6.setAlignment(SWT.CENTER); 
		label6.setBackground(display.getSystemColor(SWT.COLOR_GRAY)); 
		label6.setForeground(display.getSystemColor(SWT.COLOR_DARK_BLUE)); 
		label6.setText("待命中....");
		label6.setFont(new Font(display,"宋体",20,SWT.NORMAL)); //设置字体字号
		label6.setBounds(10, 20, 500, 50);
		
		// 监听按钮
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				System.out.println("选择目录");
				String path = folderDig(shell);
				if(path!=null) {
					text5.setText(path);
				}
			}
		});
		start.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				System.out.println("开始");
				label6.setText("运行中....");
				label6.setBackground(display.getSystemColor(SWT.COLOR_DARK_GREEN)); 
				saveSetting();
				
			}
		});
		stop.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				System.out.println("停止");
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
					+ text5.getText().trim() + "\n");
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
