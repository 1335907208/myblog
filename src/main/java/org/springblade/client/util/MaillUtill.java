package org.springblade.client.util;
import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.tool.utils.SpringUtil;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MaillUtill {
	private static BladeProperties bladeProperties;
	private static String mailFrom = null;// 指明邮件的发件人
	private static String password_mailFrom = null;// 指明邮件的发件人登陆密码
//	private static String mailTo = null;	// 指明邮件的收件人
//	private static String mailTittle = null;// 邮件的标题
//	private static String mailText =null;	// 邮件的文本内容
	private static String mail_host =null;	// 邮件的服务器域名
	private static String smtp =null;	// 邮件的服务器域名
	private String active_host = null;

//	static {
//		bladeProperties= SpringUtil.getBean(BladeProperties.class);
//	}

	public MaillUtill() {
		bladeProperties= SpringUtil.getBean(BladeProperties.class);
		mailFrom = bladeProperties.get("mailFrom");
		password_mailFrom= bladeProperties.get("password_mailFrom");
		mail_host=bladeProperties.get("mail_host");
		smtp=bladeProperties.get("smtp");
		active_host = bladeProperties.get("active_host");
	}

	public boolean sendResetPasswordMail(String mailTo,String code,String identity){
		Properties prop = new Properties();
		prop.setProperty("mail.host", mail_host);
		prop.setProperty("mail.transport.protocol", smtp);
		prop.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(prop);
		session.setDebug(true);
		try {
			Transport transport = session.getTransport();
			transport.connect(mail_host,mailFrom, password_mailFrom);
			Message message = createResetPasswordMail(session,mailFrom,mailTo,code,identity);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean send(String mailTo,String code,String idnt)  {
		boolean b=true;
//		mailFrom = "982976029@qq.com";
//		password_mailFrom="tirfaxwgffnubdcf";
//		mail_host="smtp.qq.com";
//		smtp="smtp";
		mailFrom = bladeProperties.get("mailFrom");
		password_mailFrom= bladeProperties.get("password_mailFrom");
		mail_host=bladeProperties.get("mail_host");
		smtp=bladeProperties.get("smtp");
		active_host = bladeProperties.get("active_host");

		Properties prop = new Properties();
		prop.setProperty("mail.host", mail_host);
		prop.setProperty("mail.transport.protocol", smtp);
		prop.setProperty("mail.smtp.auth", "true");

		// 使用JavaMail发送邮件的5个步骤

		// 1、创建session
		Session session = Session.getInstance(prop);
		// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
//		session.setDebug(true);
		// 2、通过session得到transport对象
		try {


		Transport ts = session.getTransport();
		// 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
		ts.connect(mail_host,mailFrom, password_mailFrom);
		// 4、创建邮件
		Message message = createSimpleMail(session,mailFrom,mailTo,code,idnt,active_host);
		// 5、发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
		}catch (Exception e) {
			System.out.println("e:"+e);
			b=false;
		}

		return b;
	}
	public boolean send(String mailTo,String code)  {
		boolean b=true;
//		mailFrom = "982976029@qq.com";
//		password_mailFrom="tirfaxwgffnubdcf";
//		mail_host="smtp.qq.com";
//		smtp="smtp";
		mailFrom = bladeProperties.get("mailFrom");
		password_mailFrom= bladeProperties.get("password_mailFrom");
		mail_host=bladeProperties.get("mail_host");
		smtp=bladeProperties.get("smtp");

		Properties prop = new Properties();
		prop.setProperty("mail.host", mail_host);
		prop.setProperty("mail.transport.protocol", smtp);
		prop.setProperty("mail.smtp.auth", "true");

		// 使用JavaMail发送邮件的5个步骤

		// 1、创建session
		Session session = Session.getInstance(prop);
		// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(true);
		// 2、通过session得到transport对象
		try {


			Transport ts = session.getTransport();
			// 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
			ts.connect(mail_host,mailFrom, password_mailFrom);
			// 4、创建邮件
			Message message = createSimpleMail(session,mailFrom,mailTo,code);
			// 5、发送邮件
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
		}catch (Exception e) {
			System.out.println("e:"+e);
			b=false;
		}

		return b;
	}

	/**
	 * @Method: createSimpleMail
	 * @Description: 创建一封只包含文本的邮件
	 */
	public  MimeMessage createSimpleMail(Session session, String mailfrom, String mailTo,
			String code,String idnt,String active_host) throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(mailfrom));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
		// 邮件的标题
		message.setSubject("[MCC] Please verify your email address.");
		// 邮件的文本内容
		String content = "<html>\n" +
			"    <body>\n" +
			"        <div style=\"width: 90%;max-width: 600px;margin: 20px auto;\">\n" +
			"            <div style=\"margin: 20px 0;\">\n" +
			"                <img style=\"clear: both; display: block; float: none; height: 60px; margin: 0 auto; outline: none; text-decoration: none;\" src=\"data:image/png;base64,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\">\n" +
			"            </div>\n" +
			"            <div style=\"border: 1px solid #dddddd;padding: 40px;\">\n" +
			"                <h1 style=\"color:#586069;font-size:19px;font-weight:400;line-height:1.25;margin:0 0 30px;padding:0;text-align:left;word-break:normal\">\n" +
			"Almost done, <strong style=\"color:#24292e\">" +
			mailTo.substring(0,mailTo.indexOf('@')) +
			"</strong>!\n" +
			"                    To complete your MCC sign up,\n" +
			"                    we just need to verify your email address:\n" +
			"                    <strong style=\"color:#24292e\">\n" +
			"                    \t<a href=\""+mailTo+"\" target=\"_blank\">"+mailTo+"</a>\n" +
			"                    </strong>\n" +
			"                </h1>\n" +
			"                <a style=\"background:#0366d6;border-radius:5px;border:1px solid #0366d6;box-sizing:border-box;color:#ffffff;display:inline-block;font-size:14px;font-weight:bold;margin:0;padding:10px 20px;text-decoration:none\" " +
			"					href=\"http://"+active_host+"/client/"+idnt+"?code="+code+"\">Verify email address</a>\n" +
			"                <p style=\"color:#222222;font-size:13px;font-weight:normal;line-height:1.5;margin:20px 0 20px;padding:0;text-align:left\" align=\"left\">\n" +
			"                    Once verified, you can start using all of MCC's features to explore, build, and share projects.\n" +
			"                </p>\n" +
			"                <p style=\"color:#586069!;font-size:12px!;font-weight:normal;line-height:1.5;margin:15px 0 15px;padding:0;text-align:left\" align=\"left\">Button not working? Paste the following link into your browser: \n" +
			"                \t<a style=\"box-sizing:border-box;color:#0366d6;text-decoration:none;word-break:break-all\" href=\"http://"+active_host+"/client/"+idnt+"?code="+code+"\">\n" +
			"                \t\thttp://"+active_host+"/client/"+idnt+"?code="+code+"\n" +
			"                \t</a>\n" +
			"                </p>\n" +
			"                <p style=\"color:#586069!;font-size:12px!;font-weight:normal;line-height:1.5;margin:15px 0 15px;padding:0;text-align:left\" align=\"left\">\n" +
			"                \tYou’re receiving this email because you recently created a new MCC account or added a new email address.\n" +
			"                \tIf this wasn’t you, please ignore this email.\n" +
			"                </p>\n" +
			"            </div>\n" +
			"        </div>\n" +
			"    </body>\n" +
			"</html>";
		message.setContent(content, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}
	/**
	 * @Method: createSimpleMail
	 * @Description: 创建一封只包含文本的邮件
	 */
	public  MimeMessage createSimpleMail(Session session, String mailfrom, String mailTo,
										 String code) throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(mailfrom));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
		// 邮件的标题
		message.setSubject("抽签码");
		// 邮件的文本内容
		String content = "已收到您的支票信息，申请已被确认，这是您的抽签号码："+code;
		message.setContent(content, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}

	/**
	 * @Method: createResetPasswordMail
	 * @Description: 创建一封重置密码的邮件
	 */
	public  MimeMessage createResetPasswordMail(
		Session session, String mailfrom, String mailTo, String code,String identity
	) throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(mailfrom));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
		// 邮件的标题
		message.setSubject("[MCC] Resert your Password.");
		// 邮件的文本内容
		String content = "<html>\n" +
			"    <body>\n" +
			"        <div style=\"width: 90%;max-width: 600px;margin: 20px auto;\">\n" +
			"            <div style=\"margin: 20px 0;\">\n" +
			"                <img style=\"clear: both; display: block; float: none; height: 60px; margin: 0 auto; outline: none; text-decoration: none;\" src=\"data:image/png;base64,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\">\n" +
			"</div>\n" +
			"<div style=\"border: 1px solid #dddddd;padding: 40px;\">\n" +
			"<h1 style=\"color:#586069;font-size:19px;font-weight:400;line-height:1.25;margin:0 0 30px;padding:0;text-align:left;word-break:normal\">\n" +
			"Almost done, <strong style=\"color:#24292e\">" + mailTo.substring(0,mailTo.indexOf('@')) +
			"</strong>!\n" +
			"                    resetting your Password\n" +
			"                </h1>\n" +
			"                <a style=\"background:#0366d6;border-radius:5px;border:1px solid #0366d6;box-sizing:border-box;color:#ffffff;display:inline-block;font-size:14px;font-weight:bold;margin:0;padding:10px 20px;text-decoration:none\" " +
			"href=\"http://"+active_host+
			"/index/resetPassword?identity="+identity+
			"&username="+mailTo+
			"&code="+code+"\">Reset Password</a>\n" +
			"<p style=\"color:#586069!;font-size:12px!;font-weight:normal;line-height:" +
			"1.5;margin:15px 0 15px;padding:0;text-align:left\" align=\"left\">" +
			"Button not working? Paste the following link into your browser: \n" +
			"                \t<a style=\"box-sizing:border-box;color:#0366d6;text-decoration:" +
			"none;word-break:break-all\" href=\"http://"+active_host+
			"/client/resetPassword?identity="+identity+
			"&username="+mailTo+
			"&code="+code+"\">\n" +
			"                \t\thttp://"+active_host+
			"/client/resetPassword?identity="+identity+
			"&username="+mailTo+
			"&code="+code+"\n" +
			"                \t</a>\n" +
			"                </p>\n" +
			"                <p style=\"color:#586069!;font-size:12px!;font-weight:normal;line-height:1.5;margin:15px 0 15px;padding:0;text-align:left\" align=\"left\">\n" +
			"                \tYou’re receiving this email because you recently resetting your password" +
			".\n" +
			"                \tIf this wasn’t you, please ignore this email.\n" +
			"                </p>\n" +
			"            </div>\n" +
			"        </div>\n" +
			"    </body>\n" +
			"</html>";
		message.setContent(content, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}


}
