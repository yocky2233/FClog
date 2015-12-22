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
 * 用来提交bug到禅道
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
		this.name = name;  //禅道账号
		this.password = password; //禅道密码
		this.module = module;  //bug模块
		this.openedBuild = openedBuild; // 影响版本
		this.assignedTo = assignedTo;  // 提交给谁
		this.title = title;  // 标题
		this.steps = steps;  // 内容
//		this.logPath = logPath;  //ftp上log的zip地址
		this.severity = severity;  // 严重等级
//		this.phonedes = phonedes;  // 手动输入的型号
		this.phoneteamid = phoneteamid;  // 机型
	}

	public void run() {
//		String nb = getTime("HHmmss");
//		String SaveLogTime = (new TimeContainer()).getSaveLogTime();
//		try {
//			File re = new File("./bug/"+SaveLogTime+"/"+title+nb+".txt");
//			System.out.println("提交的log文件："+"./bug/"+SaveLogTime+"/"+title+nb+".txt");
//			if (!re.exists()) {    //目录不存在的话就创建目录
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
		System.out.println("准备提交bug："+"\n"+"账号:"+name+"密码："+password+"标题："+title+"ftp地址："+logPath+"等级："+severity+"输入型号："+phonedes+"机型："+phoneteamid);
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT);

		// 登录页面
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

		// 查看 cookie 信息
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

		// // 访问所需的页面 main2.jsp
		// GetMethod get=new
		// GetMethod("http://18.8.0.148:88/aurora/bug-create-1-moduleID=0.html");
		// client.executeMethod(get);
		// System.out.println(get.getResponseBodyAsString());
		// get.releaseConnection();

		//上传log
//		PostMethod post2 = new PostMethod("http://18.8.0.148:88/aurora/bug-create-1-moduleID=0.html");
		PostMethod post2 = new UTF8PostMethod("http://18.8.0.148:88/aurora/bug-create-1-moduleID=0.html");
		NameValuePair inputProduct = new NameValuePair("product", "1");
		NameValuePair inputModule = new NameValuePair("module", module); // 模块
		NameValuePair inputOpenedBuild = new NameValuePair("openedBuild[]",
				openedBuild); // 影响版本
		NameValuePair inputAssignedTo = new NameValuePair("assignedTo",
				assignedTo); // 提交给谁
		NameValuePair inputTitle = new NameValuePair("title", "[日志上报]"+title); // 标题
		NameValuePair inputSteps = new NameValuePair("steps", "<p>[Log]</p> <p>"+steps+"</p> <p>[备注]</p> <p>"+logPath+"</p>"); // 内容
		NameValuePair inputType = new NameValuePair("type", "codeerror");
		NameValuePair inputSeverity = new NameValuePair("severity", severity); // 严重等级
		NameValuePair inputCases = new NameValuePair("case", "0");
		NameValuePair inputPhonedes = new NameValuePair("phonedes", phonedes); // 型号
		NameValuePair inputPhoneteamid = new NameValuePair("phoneteamid",
				phoneteamid); // 机型
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
		System.out.println("已提交bug");
		System.out.println("标题："+"[MTTF]"+title+"内容："+steps+"log路径："+logPath);
	}
	
	//让post能提交中文的内容，不会乱码     
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
	
	// 获取系统时间
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
