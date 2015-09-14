package com.dqgb.activemq.springdemo;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Sender { 
 private static ServletContext servletContext;
 private static WebApplicationContext ctx; 
 /**
  * 发送点对点信息
  * @param noticeInfo
  */
 public static void setQueueSender(){ 
  servletContext = ServletActionContext.getServletContext();
  ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
   QueueMessageProducer notifyMessageProducer = ((QueueMessageProducer) ctx.getBean("queueMessageProducer"));
   PhoneNoticeInfo noticeInfo = new PhoneNoticeInfo();

   //（下面先展示PhoneNoticeInfo 然后是 QueueMessageProducer ）
   noticeInfo.setNoticeContent("Hello Word");
   noticeInfo.setNoticeTitle("hello Word");
   noticeInfo.setReceiver("hello");
   noticeInfo.setReceiverPhone("1111111");
   notifyMessageProducer.sendQueue(noticeInfo);
  }

public static ServletContext getServletContext() {
  return servletContext;
 }
 public static void setServletContext(ServletContext servletContext) {
  Sender.servletContext = servletContext;
 }
 public static WebApplicationContext getCtx() {
  return ctx;
 }
 public static void setCtx(WebApplicationContext ctx) {
  Sender.ctx = ctx;
 } 
}

