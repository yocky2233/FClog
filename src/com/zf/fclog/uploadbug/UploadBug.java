package com.zf.fclog.uploadbug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.*;
import org.apache.commons.httpclient.methods.*; 

/** 
 * �����ύbug������
 */
public class UploadBug extends Thread {
	static final String LOGON_SITE = "localhost";
	static final int LOGON_PORT = 8080;
	private String name;
	private String password;
	private String module;
	private String openedBuild;
	private String assignedTo;
	private String title;
	private String steps;
	private String severity;
	private String phonedes;
	private String phoneteamid;
	private String logPath;
	private String bugSb;

	public UploadBug(String name, String password, String module, String openedBuild,
			String assignedTo, String title, String steps,String logPath, String severity,
			String phonedes, String phoneteamid) {
		this.name = name;  //�����˺�
		this.password = password; //��������
		this.module = module;  //bugģ��
		this.openedBuild = openedBuild; // Ӱ��汾
		this.assignedTo = assignedTo;  // �ύ��˭
		this.title = title;  // ����
		this.steps = steps;  // ����
//		this.logPath = logPath;  //ftp��log��zip��ַ
		this.severity = severity;  // ���صȼ�
//		this.phonedes = phonedes;  // �ֶ�������ͺ�
		this.phoneteamid = phoneteamid;  // ����
	}

	public void run() {
//		String nb = getTime("HHmmss");
//		String SaveLogTime = (new TimeContainer()).getSaveLogTime();
//		try {
//			File re = new File("./bug/"+SaveLogTime+"/"+title+nb+".txt");
//			System.out.println("�ύ��log�ļ���"+"./bug/"+SaveLogTime+"/"+title+nb+".txt");
//			if (!re.exists()) {    //Ŀ¼�����ڵĻ��ʹ���Ŀ¼
//				re.createNewFile();
//		    }
//			FileOutputStream is = new FileOutputStream(re);
//			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(is));
//			br.write(bugSb);
//			br.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		System.out.println("׼���ύbug��"+"\n"+"�˺�:"+name+"���룺"+password+"���⣺"+title+"ftp��ַ��"+logPath+"�ȼ���"+severity+"�����ͺţ�"+phonedes+"���ͣ�"+phoneteamid);
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT);

		// ��¼ҳ��
		PostMethod post = new PostMethod(
				"http://18.8.0.148:88/aurora/user-login.html");
		NameValuePair inputName = new NameValuePair("account", name);
		NameValuePair inputPassword = new NameValuePair("password", password);
		post.setRequestBody(new NameValuePair[] { inputName, inputPassword });
		try {
			int status = client.executeMethod(post);
			System.out.println(post.getResponseBodyAsString());
		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		post.releaseConnection();

		// �鿴 cookie ��Ϣ
		// CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
		// Cookie[] cookies = cookiespec.match(LOGON_SITE, LOGON_PORT, "/" ,
		// false , client.getState().getCookies());
		// if (cookies.length == 0) {
		// System.out.println( "None" );
		// } else {
		// for ( int i = 0; i < cookies.length; i++) {
		// System.out.println(cookies[i].toString());
		// }
		// }

		// // ���������ҳ�� main2.jsp
		// GetMethod get=new
		// GetMethod("http://18.8.0.148:88/aurora/bug-create-1-moduleID=0.html");
		// client.executeMethod(get);
		// System.out.println(get.getResponseBodyAsString());
		// get.releaseConnection();

		//�ϴ�log
//		PostMethod post2 = new PostMethod("http://18.8.0.148:88/aurora/bug-create-1-moduleID=0.html");
		PostMethod post2 = new UTF8PostMethod("http://18.8.0.148:88/aurora/bug-create-1-moduleID=0.html");
		NameValuePair inputProduct = new NameValuePair("product", "1");
		NameValuePair inputModule = new NameValuePair("module", module); // ģ��
		NameValuePair inputOpenedBuild = new NameValuePair("openedBuild[]",
				openedBuild); // Ӱ��汾
		NameValuePair inputAssignedTo = new NameValuePair("assignedTo",
				assignedTo); // �ύ��˭
		NameValuePair inputTitle = new NameValuePair("title", "[��־�ϱ�]"+title); // ����
		NameValuePair inputSteps = new NameValuePair("steps", "<p>[Log]</p> <p>"+steps+"</p> <p>[��ע]</p> <p>"+logPath+"</p>"); // ����
		NameValuePair inputType = new NameValuePair("type", "codeerror");
		NameValuePair inputSeverity = new NameValuePair("severity", severity); // ���صȼ�
		NameValuePair inputCases = new NameValuePair("case", "0");
		NameValuePair inputPhonedes = new NameValuePair("phonedes", phonedes); // �ͺ�
		NameValuePair inputPhoneteamid = new NameValuePair("phoneteamid",
				phoneteamid); // ����
		post2.setRequestBody(new NameValuePair[] { inputProduct, inputModule,
				inputOpenedBuild, inputAssignedTo, inputTitle, inputSteps,
				inputType, inputSeverity, inputCases, inputPhonedes,
				inputPhoneteamid });
		try {
			int status2 = client.executeMethod(post2);
			System.out.println(post2.getResponseBodyAsString());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		post2.releaseConnection();
		System.out.println("���ύbug");
		System.out.println("���⣺"+"[MTTF]"+title+"���ݣ�"+steps+"log·����"+logPath);
	}
	
	//��post���ύ���ĵ����ݣ���������     
	public static class UTF8PostMethod extends PostMethod{     
	    public UTF8PostMethod(String url){     
	    super(url);     
	    }     
	    @Override     
	    public String getRequestCharSet() {     
	        //return super.getRequestCharSet();     
	        return "UTF-8";     
	    }  
	}  
	
	// ��ȡϵͳʱ��
	private static String getTime(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(time);
		String Time = dateFormat.format(new Date());
		return Time;
	}

//	public static void main(String[] args) {
//		UploadBug t = new UploadBug();
//		try {
//			t.uploadBug(
//					"tim.zheng",
//					"888888",
//					"0",
//					"trunk",
//					"tim.zheng",
//					"test",
//					"<p>[monkeyLog]</p> <p>test</p> <p>[logPath]</p> <p>xxx/xxx.log</p>",
//					"3", "U0001", "28");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
