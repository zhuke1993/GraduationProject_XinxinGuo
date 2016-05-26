package com.gxx.nqh.service.impl;

import com.gxx.nqh.service.MailService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by GXX on 2016/3/28.
 */
@Service
@Scope(value = "prototype")
public class MailServiceImpl implements MailService {

    private String toAddr;
    private String content;

    Logger logger = LogManager.getLogger(MailService.class);

    public void setMailInfo(String toAddr, String content) {
        this.toAddr = toAddr;
        this.content = content;
    }

    public boolean sendMail(String toAddr, String content) {
        final Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(URLDecoder.decode(this.getClass().getResource("/").getFile() + "nqh.properties", "UTF-8")));
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("mail.username"), properties.getProperty("mail.password"));
                }
            });

            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(properties.getProperty("mail.username")));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));

            // 设置消息体
            message.setText(content);
            // 发送消息
            Transport.send(message);

            logger.info("Send mail success");
            return true;
        } catch (Exception e) {
            logger.error("An error occured.", e);
            return false;
        }
    }

    public void run() {
        Assert.notNull(toAddr);
        Assert.notNull(content);
        sendMail(toAddr, content);
    }
}
