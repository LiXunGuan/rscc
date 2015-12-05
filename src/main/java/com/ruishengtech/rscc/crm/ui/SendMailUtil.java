package com.ruishengtech.rscc.crm.ui;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruishengtech.framework.core.db.UUID;

public class SendMailUtil {
	
	public String sendMail(HttpServletRequest request,HttpServletResponse response,String name,String mail) throws ServletException, IOException{
		String secretKey = UUID.randomUUID().toString();  //生成随机字符串
		Timestamp outDate = new Timestamp(System.currentTimeMillis()+30*60*1000); //30分钟后过期
		long date = outDate.getTime()/1000*1000; //忽略毫秒
		String key = date+"-"+secretKey;   //生成最终秘钥
		
		String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String resetPassHref =  basePath+"user/reset_password?sid="+key;
        StringBuffer sb = new StringBuffer();
        sb.append(" 请勿回复本邮件.点击或者复制下面的链接,重设密码 \n\n");
        sb.append(resetPassHref);
        sb.append("\n\n  tips: (如果无法点击该URL链接地址，请将它复制并粘帖到浏览器的地址输入框，然后单击回车即可。该链接使用后将立即失效。)\n   本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'  ");
        String emailContent  = sb.toString();
        /*echo "<a href="www.baidu.com">点击跳转到百度</a></br>邮件测试咯" | mail -s "$(echo -e "test\nContent-Type: text/html;charset=UTF-8")" "a.6770261a@163.com"*/
    	  String cmd = "echo \""+emailContent+"\" | mail -s \"$(echo -e  \"找回密码\\nContent-Type: text/html;charset=UTF-8\")\" "+"\""+mail+"\"";
        try {
        	 System.out.print(cmd);
        	Process process=Runtime.getRuntime().exec(new String[]{"sh","-c",cmd});
        	// request.getRequestDispatcher("").forward(request, response);
        	InputStreamReader reader = new InputStreamReader(process.getInputStream());
        	LineNumberReader line = new LineNumberReader(reader);
        	String str;
        	while((str=line.readLine())!=null){
        	System.out.println(str);
        	}
        	}catch (Exception e){
        	e.printStackTrace();
        	}
        return key;
	}
}

