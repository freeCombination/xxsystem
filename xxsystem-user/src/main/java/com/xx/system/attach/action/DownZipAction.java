package com.xx.system.attach.action;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.xx.system.attach.entity.Attach;
import com.xx.system.attach.service.IAttachService;
import com.xx.system.common.action.BaseAction;

public class DownZipAction extends BaseAction {   
	
	@Resource
	private IAttachService attachService;
	
   private static final long serialVersionUID = 1L;
   private String attachIds;
   private OutputStream res;   
   private ZipOutputStream zos;
 
   /**
    * 打包下载ZIP
    * @return
    * @throws Exception
    */
   public String downLoadZip() throws Exception {
	   if(StringUtils.isBlank(attachIds)){
		   write( "参数错误！");
    	   return null;
	   }
	   List<Attach> attachList = attachService.getAttachsByIds(attachIds);
       // 有数据可以下载   
       if (attachList.size()!= 0) {   
           // 进行预处理
           preProcess(attachList);
       } else {
    	   write( "您下载的附件不存在！");
    	   return null;
       }
       // 处理
       writeZip(attachList);
       // 后处理关闭流
       afterProcess();
       return null;
   }   
 
   // 压缩处理
   private void writeZip(List<Attach> AttachList) throws IOException {   
       byte[] buf = new byte[8192];
       int len;
       for (Attach attach : AttachList) {
           File file = new File(attach.getAttachUrl());
           if (!file.exists() || !file.isFile())
               continue;
           ZipEntry ze = new ZipEntry(attach.getAttachName());
           zos.putNextEntry(ze);   
           BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));   
           while ((len = bis.read(buf)) > 0) {   
               zos.write(buf, 0, len);   
           }
           bis.close();
           zos.closeEntry();   
       }   
   }
 
   /**
    * 预处理
    * @param attachList
    * @throws Exception
    */
   private void preProcess(List<Attach> attachList) throws Exception {
	   String firstFileName = attachList.get(0).getAttachName();
	   firstFileName = firstFileName.substring(0,firstFileName.lastIndexOf('.'));
	   if(attachList.size()==1){
		   firstFileName = firstFileName+".zip";
	   }else{
		   firstFileName = "【批量下载】"+firstFileName+"等.zip";
	   }
       HttpServletResponse response = ServletActionContext.getResponse();   
       res = response.getOutputStream();
       // 清空输出流(在迅雷下载不会出现一长窜)   
       response.reset();   
       // 设定输出文件头   
       response.setHeader("Content-Disposition", "attachment;filename=\""+new String(firstFileName.getBytes("GBK"),"ISO8859-1")+"\"");   
       response.setContentType("application/zip");   
       zos = new ZipOutputStream(res);
       zos.setEncoding("GBK");
   }   
 
   public void write(String str) {
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		try {
			out = ServletActionContext.getResponse().getWriter();
			out.write("<html><head><title>出错了</title></head><body>" +
					"<div style=\"text-align: center;margin-top: 100px;\">"+str+"</div>" +
					"</body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   
   // 后处理   
   private void afterProcess() throws IOException {   
 
       zos.close();   
       res.close();   
   }   
 
   public OutputStream getRes() {   
       return res;   
   }   
 
   public void setRes(OutputStream res) {   
       this.res = res;   
   }   
 
   public ZipOutputStream getZos() {   
       return zos;   
   }   
 
   public void setZos(ZipOutputStream zos) {   
       this.zos = zos;   
   }

public String getAttachIds() {
	return attachIds;
}

public void setAttachIds(String attachIds) {
	this.attachIds = attachIds;
}   
  
}  
