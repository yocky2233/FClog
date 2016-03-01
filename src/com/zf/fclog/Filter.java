package com.zf.fclog;

import java.awt.Label;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.eclipse.swt.widgets.Shell;

import com.zf.fclog.uploadbug.UploadBug;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Filter {

	/**
	 * 
	 * @param file 下载日志的文件名称
	 * @param account  禅道的账号
	 * @param password  禅道的密码
	 * @param downloadPath  日志文件的本地下载路径
	 */
	public void readFile(String file,String account,String password,String downloadPath,Shell shell,final org.eclipse.swt.widgets.Label bugShow) { 
		jxl.Workbook readwb = null;
		try {
			// 构建Workbook对象, 只读Workbook对象
			// 直接从本地文件创建Workbook
			InputStream instream = new FileInputStream(downloadPath+file);
			readwb = Workbook.getWorkbook(instream);
			// Sheet的下标是从0开始
			// 获取第一张Sheet表
			Sheet readsheet = readwb.getSheet(0);
			// 获取Sheet表中所包含的总列数
//			int rsColumns = readsheet.getColumns();
			//只获取机型、版本、日志内容3列
			int rsColumns = 3;
			System.out.println("总列数："+rsColumns);
			// 获取Sheet表中所包含的总行数
			int rsRows = readsheet.getRows();
			System.out.println("总行数："+rsRows);
			// 获取指定单元格的对象引用
			final int a;
			int aa = 0;
			int bug = 0;
			for (int i = 1; i < rsRows; i++) {
				String phoneteamid = ""; //机型key值
				String assignedTo = ""; //提交给谁
				String title = ""; //标题
				String type = "";
				String versions = "";
				String log = "";
				for (int j = 0; j < rsColumns; j++) {
					switch (j) {
					case 0:
						Cell JX = readsheet.getCell(j, i);  //获取机型
						type = JX.getContents().toString();
						break;
                    case 1:
                    	Cell BB = readsheet.getCell(j, i); //获取版本
                    	versions = BB.getContents().toString();
						break;
                    case 2:
                    	Cell LOG = readsheet.getCell(j, i);  //获取日志
                    	if((LOG.getContents()).contains("Build fingerprint:")||(LOG.getContents()).contains("com.android.camera")) {
                    		
                    		continue;
                    	}
                    	aa++;
                    	log = LOG.getContents().toString();
//						System.out.print("日志内容："+log);
						break;
					}
				}
				if(log!=""&&type!=""&&versions!="") {
					System.out.println("继续执行");
					File f = new File("./info/package.txt");
					File f2 = new File("./info/JiXingInfo.txt");
					File f3 = new File("./info/developer.txt");
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
					BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(f2)));
					BufferedReader br3 = new BufferedReader(new InputStreamReader(new FileInputStream(f3)));
					String pk;
					String jx;
					while((jx=br2.readLine())!=null) {
						if(jx.contains(type)) {
							phoneteamid = jx.split("is")[1];
//							System.out.println("机型号："+phoneteamid);
							break;
						}
					}
					//如果机型属于为已知型号，继续执行
					if(phoneteamid!="") {
//						System.out.println("机型为已知");
						String[] readLog = log.split("\n");
//						System.out.println(Arrays.toString(readLog));
						StringBuffer logGS = new StringBuffer();
						for(int h=0; h<readLog.length; h++) {
							if(h==0) {
								title = readLog[h].trim();
							}
							logGS.append("<p>"+readLog[h]+"<p>"+"\n");
						}
						
						while((pk=br.readLine())!=null) {
							//判断是否为系统应用
//							System.out.println("包名："+pk);
							if(log.contains(pk)) {
								//系统bug处理
//								System.out.println(pk);
								System.out.println("为系统应用");
								bug++;
								String kf;
								while((kf=br3.readLine())!=null) {
									if(kf.contains(pk)) {
										assignedTo = kf.split("contains")[1].trim();
										break;
									}
								}
								if(assignedTo=="") {
									assignedTo = "yongliangwang"; //如果无匹配的开发，就提给永亮
								}
								break;
							}
						}
						if(assignedTo=="") {
							System.out.println("为第三方应用");
							//第三方bug处理
							Random rd = new Random();
							int i1 = rd.nextInt(2);
							switch(i1) {
							case 0:
								assignedTo = "linchunhui";
								break;
							case 1:
								assignedTo = "luolaigang";
								break;
							}
						}
//						System.out.println(title);
//						System.out.println(type);
//						System.out.println(versions);
//						System.out.println("开发是："+assignedTo);
						//执行提单
						UploadBug t = new UploadBug(account,password,"0","trunk",assignedTo,title,logGS.toString(),"3",type,phoneteamid,versions);
						Thread.sleep(1000);
//						UploadBug t = new UploadBug("tim.zheng","888888","0","trunk","tim.zheng",title,logGS.toString(),"3",type,phoneteamid,versions);
						try {
							t.run(
									);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			a = aa;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			final String time = dateFormat.format(new Date());
			//异步更新UI方法
			shell.getDisplay().asyncExec(new Runnable(){
                public void run() {
                	bugShow.setText(time+"共提交"+a+"个bug");
                }
           });
			System.out.println("有效日志数："+a);
			System.out.println("系统bug数："+bug);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	/*
	 *读取过滤下载的文件，进行提bug 
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Filter rf = new Filter();
//		rf.readFile("FC_LOG_2015-12-29.xls","kk","123","./Log/");
//	}

}
