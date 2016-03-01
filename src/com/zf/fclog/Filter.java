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
	 * @param file ������־���ļ�����
	 * @param account  �������˺�
	 * @param password  ����������
	 * @param downloadPath  ��־�ļ��ı�������·��
	 */
	public void readFile(String file,String account,String password,String downloadPath,Shell shell,final org.eclipse.swt.widgets.Label bugShow) { 
		jxl.Workbook readwb = null;
		try {
			// ����Workbook����, ֻ��Workbook����
			// ֱ�Ӵӱ����ļ�����Workbook
			InputStream instream = new FileInputStream(downloadPath+file);
			readwb = Workbook.getWorkbook(instream);
			// Sheet���±��Ǵ�0��ʼ
			// ��ȡ��һ��Sheet��
			Sheet readsheet = readwb.getSheet(0);
			// ��ȡSheet������������������
//			int rsColumns = readsheet.getColumns();
			//ֻ��ȡ���͡��汾����־����3��
			int rsColumns = 3;
			System.out.println("��������"+rsColumns);
			// ��ȡSheet������������������
			int rsRows = readsheet.getRows();
			System.out.println("��������"+rsRows);
			// ��ȡָ����Ԫ��Ķ�������
			final int a;
			int aa = 0;
			int bug = 0;
			for (int i = 1; i < rsRows; i++) {
				String phoneteamid = ""; //����keyֵ
				String assignedTo = ""; //�ύ��˭
				String title = ""; //����
				String type = "";
				String versions = "";
				String log = "";
				for (int j = 0; j < rsColumns; j++) {
					switch (j) {
					case 0:
						Cell JX = readsheet.getCell(j, i);  //��ȡ����
						type = JX.getContents().toString();
						break;
                    case 1:
                    	Cell BB = readsheet.getCell(j, i); //��ȡ�汾
                    	versions = BB.getContents().toString();
						break;
                    case 2:
                    	Cell LOG = readsheet.getCell(j, i);  //��ȡ��־
                    	if((LOG.getContents()).contains("Build fingerprint:")||(LOG.getContents()).contains("com.android.camera")) {
                    		
                    		continue;
                    	}
                    	aa++;
                    	log = LOG.getContents().toString();
//						System.out.print("��־���ݣ�"+log);
						break;
					}
				}
				if(log!=""&&type!=""&&versions!="") {
					System.out.println("����ִ��");
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
//							System.out.println("���ͺţ�"+phoneteamid);
							break;
						}
					}
					//�����������Ϊ��֪�ͺţ�����ִ��
					if(phoneteamid!="") {
//						System.out.println("����Ϊ��֪");
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
							//�ж��Ƿ�ΪϵͳӦ��
//							System.out.println("������"+pk);
							if(log.contains(pk)) {
								//ϵͳbug����
//								System.out.println(pk);
								System.out.println("ΪϵͳӦ��");
								bug++;
								String kf;
								while((kf=br3.readLine())!=null) {
									if(kf.contains(pk)) {
										assignedTo = kf.split("contains")[1].trim();
										break;
									}
								}
								if(assignedTo=="") {
									assignedTo = "yongliangwang"; //�����ƥ��Ŀ��������������
								}
								break;
							}
						}
						if(assignedTo=="") {
							System.out.println("Ϊ������Ӧ��");
							//������bug����
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
//						System.out.println("�����ǣ�"+assignedTo);
						//ִ���ᵥ
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
			//�첽����UI����
			shell.getDisplay().asyncExec(new Runnable(){
                public void run() {
                	bugShow.setText(time+"���ύ"+a+"��bug");
                }
           });
			System.out.println("��Ч��־����"+a);
			System.out.println("ϵͳbug����"+bug);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	/*
	 *��ȡ�������ص��ļ���������bug 
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Filter rf = new Filter();
//		rf.readFile("FC_LOG_2015-12-29.xls","kk","123","./Log/");
//	}

}
