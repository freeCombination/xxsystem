package com.dqgb.activemq.springdemo;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueMessageListener implements MessageListener {
 private static Logger logger = LoggerFactory.getLogger(QueueMessageListener.class);
 private NotifyMessageConverter messageConverter;
 
 /**
  * 接收消息
  */
 @Override
 public void onMessage(Message message) {
  // TODO Auto-generated method stub
  try {
   ObjectMessage objectMessage = (ObjectMessage)message;
   PhoneNoticeInfo noticeInfo = (PhoneNoticeInfo)messageConverter.fromMessage(objectMessage);
   System.out.println("queue收到消息"+noticeInfo.getNoticeContent());
   System.out.println("model:"+objectMessage.getJMSDeliveryMode());  
   System.out.println("destination:"+objectMessage.getJMSDestination());  
   System.out.println("type:"+objectMessage.getJMSType());  
   System.out.println("messageId:"+objectMessage.getJMSMessageID());  
   System.out.println("time:"+objectMessage.getJMSTimestamp());  
   System.out.println("expiredTime:"+objectMessage.getJMSExpiration());  
   System.out.println("priority:"+objectMessage.getJMSPriority()); 

  } catch (Exception e) {
   // TODO: handle exception
   logger.error("处理信息时发生异常",e);
  }
 }
 public NotifyMessageConverter getMessageConverter() {
  return messageConverter;
 }
 public void setMessageConverter(NotifyMessageConverter messageConverter) {
  this.messageConverter = messageConverter;
 }

}

