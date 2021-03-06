package com.dandanpili.email;

import java.security.GeneralSecurityException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.util.MailSSLSocketFactory;

public class SendEmail {
    public static void main(String[] args) throws GeneralSecurityException {
        //收件人电子邮箱
        String to = "收件人电子邮箱";

        //发件人电子邮箱
        String from = "发件人电子邮箱";

        //指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";

        //获取系统属性
        Properties properties = System.getProperties();

        //设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //获取默认Session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
           public PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication("你的邮箱", "授权码");
           }
        });

        try {
            for (int i = 1; i <= 100; i++) {
                //创建默认的MimeMessage对象
                MimeMessage message = new MimeMessage(session);
                //Set From:头部头字段
                message.setFrom(new InternetAddress(from));
                //Set To:头部字段
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                //Set Subject:头部字段
                message.setSubject("邮箱的消息头");
                //设置消息体
                message.setText("邮箱的消息体");

                //发送消息
                Transport.send(message);
                System.out.println("Sent message " + i + " successfully ... from 你的邮箱");
            }
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
