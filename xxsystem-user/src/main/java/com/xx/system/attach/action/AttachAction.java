package com.xx.system.attach.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.xx.system.attach.entity.Attach;
import com.xx.system.attach.entity.AttachGroup;
import com.xx.system.attach.service.IAttachGroupService;
import com.xx.system.attach.service.IAttachService;
import com.xx.system.attach.vo.AttachVo;
import com.xx.system.common.action.BaseAction;
import com.xx.system.common.util.FileUtil;
import com.xx.system.common.util.FormatUnitsUtil;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.PropertyUtil;
import com.xx.system.common.vo.ResponseVo;
import com.xx.system.user.action.UserAction;

public class AttachAction  extends BaseAction{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private IAttachService attachService;
	@Resource
	private IAttachGroupService attachGroupService;
	
	private static final int STATUS_NOT_DELETE = 0; //未删除
	
	private File uploadAttach;   //上传的附件
    private String uploadAttachContentType;   
    
    private String uploadAttachFileName;
    
    private Attach attach;   //上传的附件实体
    
    public InputStream fileStream;
    
	private int attachGroupId;

	private int attachId;
	
	private String attachType;	

	private AttachGroup attachGroup;

	private String result;	

	private String savePath; //保存到服务上的路径
	private Long maximumSize; //文件大小
	private String allowedExtensions;
	private List<Attach> attachs;

	/**
	 * 添加附件
	 *{"attachType":null,"attachName":"intel_ICS_Dx32.exe","result":"Y","attachGroupId":281,"attachId":345,"attachSize":"7.42 MB"}
	 * @author 何道军
	 * @date 2011-1-7
	 * @return String
	 */
	public String addAttach(){
		Map<String,Object> attachMap = new HashMap<String, Object>();
		if (uploadAttach == null ) {
			attachMap.put("success",false);
			attachMap.put("result","添加失败。");
			JsonUtil.outJson(attachMap);
			return null;
		}
		
		if (uploadAttach.length()==0) {
			attachMap.put("success",false);
			attachMap.put("result","添加失败：不能上传大小为0 KB的文件。");
			JsonUtil.outJson(attachMap);
			return null;
		}
		
		if (maximumSize != null && maximumSize > 0 && maximumSize < uploadAttach.length()) {
			attachMap.put("success",false);
			attachMap.put("result","添加失败：文件大小不能超过 "+ FormatUnitsUtil.formatBytes(maximumSize)
					+"。当前文件大小为："+FormatUnitsUtil.formatBytes(uploadAttach.length()));
			JsonUtil.outJson(attachMap);
			return null;
		}
        if (StringUtils.isNotBlank(allowedExtensions) && ! allowedExtensions.contains(FileUtil.getFileExp(uploadAttachFileName))) {
        	attachMap.put("success",false);
        	attachMap.put("result","添加失败：只能上传后缀名为：" + allowedExtensions +"的文件。");
        	JsonUtil.outJson(attachMap);
        	return null;
        } 
		try{
			if(attachGroupId != 0){
				attachGroup =attachGroupService.getAttachGroup(attachGroupId);
				if(attachGroup == null){
					attachMap.put("success",false);
					attachMap.put("result","系统错误：更新的附件组ID:"+attachGroupId+"不存在，如果要重新生成附件组ID,请设置附件组ID为空或0！");
					JsonUtil.outJson(attachMap);
					return null;
				}
			}else{
				attachGroup= new AttachGroup();
				attachGroup.setStatus(STATUS_NOT_DELETE);
				attachGroup.setSubmitDate(new Date());
				attachGroupService.saveAttachGroup(attachGroup);
			}
			
			dealSavePath();
			
			attach = new Attach();
			attach.setStatus(STATUS_NOT_DELETE);
			attach.setAttachName(uploadAttachFileName);
			attach.setAttachType(attachType);
			String attachURL = FileUtil.upload(uploadAttach, uploadAttachFileName, savePath);
			attach.setAttachUrl(attachURL);
			attach.setAttachGroup(attachGroup);
			attach.setSubmitDate(new Date());
			attach.setFileSize(FormatUnitsUtil.formatBytes(uploadAttach.length()));
			attach.setDownloadTime(0);
			attachService.saveAttach(attach);
		
			attachMap.put("attachGroupId",attachGroup.getAttachGroupId());
			attachMap.put("attachName",attach.getAttachName());
			attachMap.put("attachId",attach.getAttachId());
			attachMap.put("attachType",attach.getAttachType());
			attachMap.put("fileSize",attach.getFileSize());
			attachMap.put("success",true);
		}
		catch(Exception e){
			attachMap.put("success",false);
			attachMap.put("result","添加失败："+e.getMessage());
			e.printStackTrace();
		}
		JsonUtil.outJson(attachMap);
		return null;
	}
	
	/**
	 * 处理保存路径
	 */
	private void dealSavePath(){
		if(StringUtils.isBlank(savePath)){
			savePath = PropertyUtil.get("attach.file.savePath");
		}
		if(StringUtils.isBlank(savePath)){
			savePath = "C:\\uploadFile\\$date{yyyy}\\$date{MM}" ;
		}
		//路径为相对路径时，转为绝对路径。
		if(savePath.indexOf(":\\") == -1){
			savePath = ServletActionContext.getServletContext().getRealPath("/") +savePath ;
		}
		savePath = formatPath(savePath);
	}
	
	/**
	 * 格式化 savePath
	 * @param savePath
	 * @return
	 */
	private String formatPath(String savePath){
		if(savePath.indexOf("$date{")>=0){
			String dateFmt = savePath.substring(savePath.indexOf("{")+1, savePath.indexOf("}")).trim();
			SimpleDateFormat dateformat=new SimpleDateFormat(dateFmt);   
			savePath = savePath.replaceFirst("\\$date\\{.*?\\}", dateformat.format(new Date()));
			savePath = formatPath(savePath);
		}
		return savePath;
	}
	
	/**
	 * 下载附件
	 *
	 * @author 何道军
	 * @date 2011-1-7
	 * @return String
	 */
	public String downloadAttach(){
		try{
			if(attachId == 0){
				result = "不存在的附件ID,下载失败，请稍后再试！";
				return "success";
			}
			Attach attach = attachService.getAttach(attachId);
			if(attach == null){
				write( "<html><head><title>出错了</title></head><body>" +
						"<div style=\"text-align: center;margin-top: 100px;\">您下载的附件不存在，请检查数据完整性！</div>" +
						"</body></html>");
				return null;
			}
			fileStream = new FileInputStream(attach.getAttachUrl());
			uploadAttachFileName = attach.getAttachName();
			
			attach.setDownloadTime(attach.getDownloadTime()+1);
			attachService.updateAttach(attach);
			
		}
		catch(FileNotFoundException e){
			write( "<html><head><title>出错了</title></head><body>" +
					"<div style=\"text-align: center;margin-top: 100px;\">您下载的文件不存在，可能已经从服务器上删除！</div>" +
					"</body></html>");
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
     * 批量删除附件
     * 
     * @Title deleteUser
     * @author hedaojun
     * @date 2015-2-15
     * @return String
     */
    public String deleteAttachs() {
        try {
        	String attachIds = this.getRequest().getParameter("attachIds");
        	for(String attachId : attachIds.split(",")){
        		Attach attach = attachService.getAttach(Integer.parseInt(attachId));
    			//删除数据库记录
    			attachService.removeAttach(attach.getAttachId());
    			//从服务器硬盘删除文件
    			FileUtil.delFile(attach.getAttachUrl());
        	}
            JsonUtil.outJson("{success:true,msg:'删除成功！'}");
        } catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'删除失败！'}");
            this.excepAndLogHandle(UserAction.class, "批量删除附件", e, false);
        }
        return null;
    }
	
	/**
	 * 删除附件
	 *
	 * @author 何道军
	 * @date 2011-1-7
	 * @return String
	 */
	public String deleteAttach(){
		try{
			if(attachId == 0){
				result = "不存在的附件ID,删除失败，请稍后再试！";
				return "success";
			}
			Attach attach = attachService.getAttach(attachId);
			//删除数据库记录
			attachService.removeAttach(attach.getAttachId());
			//从服务器硬盘删除文件
			FileUtil.delFile(attach.getAttachUrl());
			
			result = "Y";
		}
		catch(Exception e){
			e.printStackTrace();
			result = "删除失败，请稍后再试！";
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		JsonUtil.outJson(resultMap);
		return null;
	}

	/**
	 *
	 * @author 何道军
	 * @date 2011-1-7
	 * @return String
	 */
	public String getAttachsByGroupId(){
		ResponseVo rv = new ResponseVo();
		try{
			if(attachGroupId == 0){
				JsonUtil.outJson(rv);
				return null;
			}
			List<AttachVo> attachVos = attachService.getAttachsByGroupId(attachGroupId);
			rv.setList(attachVos);
			rv.setTotalSize(attachVos.size());
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		JsonUtil.outJson(rv);
		return null;
	}
	
	public void write(String str) {
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		try {
			out = ServletActionContext.getResponse().getWriter();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getUploadAttach() {
		return uploadAttach;
	}

	public void setUploadAttach(File uploadAttach) {
		this.uploadAttach = uploadAttach;
	}

	public String getUploadAttachContentType() {
		return uploadAttachContentType;
	}

	public void setUploadAttachContentType(String uploadAttachContentType) {
		this.uploadAttachContentType = uploadAttachContentType;
	}

	public String getUploadAttachFileName() {
		return uploadAttachFileName;
	}
	
	public String getFileName() {
		String fileName =null;
		try {
			fileName = new String(uploadAttachFileName.getBytes("GBK"),"ISO8859-1");  //把file转换成ISO8859-1编码格式
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	public void setUploadAttachFileName(String uploadAttachFileName) {
		this.uploadAttachFileName = uploadAttachFileName;
	}

	public Attach getAttach() {
		return attach;
	}

	public void setAttach(Attach attach) {
		this.attach = attach;
	}

	public int getAttachGroupId() {
		return attachGroupId;
	}

	public void setAttachGroupId(int attachGroupId) {
		this.attachGroupId = attachGroupId;
	}
	
	public int getAttachId() {
		return attachId;
	}

	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}

	public AttachGroup getAttachGroup() {
		return attachGroup;
	}

	public void setAttachGroup(AttachGroup attachGroup) {
		this.attachGroup = attachGroup;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public Long getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(Long maximumSize) {
		this.maximumSize = maximumSize;
	}

	public String getAllowedExtensions() {
		return allowedExtensions;
	}

	public void setAllowedExtensions(String allowedExtensions) {
		this.allowedExtensions = allowedExtensions;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public void setAttachService(IAttachService attachService) {
		this.attachService = attachService;
	}
	
	public void setAttachGroupService(IAttachGroupService attachGroupService) {
		this.attachGroupService = attachGroupService;
	}

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	public List<Attach> getAttachs() {
		return attachs;
	}

}
