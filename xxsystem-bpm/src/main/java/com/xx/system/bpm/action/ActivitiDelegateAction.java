/**
 * @文件名 ActivitiDelegateAction.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 委托授权控制层
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dqgb.sshframe.bpm.service.IActivitiDelegateService;
import com.dqgb.sshframe.bpm.vo.ActivitiDelegateVo;
import com.dqgb.sshframe.common.action.BaseAction;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.util.JsonUtil;
import com.dqgb.sshframe.common.util.RequestUtil;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.dict.service.IDictService;
import com.dqgb.sshframe.log.service.ILogService;
import com.dqgb.sshframe.user.entity.User;
import com.dqgb.sshframe.user.service.IUserService;

/**
 * 
 * 委托授权控制层
 * 
 * @author zhxh
 * @version V1.20,2013-12-25 下午5:12:47
 * @since V1.20
 */
public class ActivitiDelegateAction extends BaseAction
{
    private static final long serialVersionUID = 1L;
    
    /** 流程表单逻辑层接口 */
    @Autowired
    @Qualifier("activitiDelegateService")
    private IActivitiDelegateService activitiDelegateService;
    
    /** 委托授权Vo */
    private ActivitiDelegateVo delegateVo;
    
    /** @Fields userService : 用户服务类 */
    @Autowired
    @Qualifier("userService")
    private IUserService userService;
    
    /** @Fields user : 用于存储当前用户信息 */
    private User user;
    
    /** @Fields photoImg : 个人信息页面 - 头像上传 */
    private File photoImg;
    
    public File getPhotoImg() {
		return photoImg;
	}

	public void setPhotoImg(File photoImg) {
		this.photoImg = photoImg;
	}

	public ActivitiDelegateVo getDelegateVo()
    {
        return delegateVo;
    }
    
    public void setDelegateVo(ActivitiDelegateVo delegateVo)
    {
        this.delegateVo = delegateVo;
    }
    
    /**
     * @Fields dictService : 字典服务
     */
    @Resource
    public IDictService dictService;
    
    /**
     * @Fields roleService : 日志服务
     */
    @Autowired(required = true)
    @Qualifier("logService")
    private ILogService logService;
    
    /**
     * 
     * @Title addDelegate
     * @author zhxh
     * @Description:增加托管设置
     * @date 2013-12-27
     * @return String
     */
    public String addDelegate()
    {
        try
        {
            this.activitiDelegateService.addDelegate(delegateVo,
                this.getCurrentUser());
            JsonUtil.outJson("{success:true,msg:'保存成功！'}");
        }
        catch (ServiceException e)
        {
            JsonUtil.outJson("{success:false,msg:'保存失败！" + e.getMessageKey()
                + "'}");
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'保存失败！'}");
        }
        
        return null;
    }
    
    /**
     * 
     * @Title updateDelegate
     * @author zhxh
     * @Description:更新托管
     * @date 2013-12-27
     * @return String
     */
    public String updateDelegate()
    {
        try
        {
            this.activitiDelegateService.updateDelegate(delegateVo,
                this.getCurrentUser());
            JsonUtil.outJson("{success:true,msg:'更新成功！'}");
        }
        catch (ServiceException e)
        {
            JsonUtil.outJson("{success:false,msg:'保存失败！" + e.getMessageKey()
                + "'}");
        }
        
        return null;
    }
    
    /**
     * 
     * @Title deleteDelegate
     * @author zhxh
     * @Description:删除托管
     * @date 2013-12-27
     * @return String
     */
    public String deleteDelegate()
    {
        try
        {
            String ids = this.getRequest().getParameter("ids");
            this.activitiDelegateService.deleteDelegate(ids);
            JsonUtil.outJson("{success:true,msg:'删除成功！'}");
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'删除失败！'}");
        }
        
        return null;
    }
    
    /**
     * 
     * @Title getDelegateByPage
     * @author zhxh
     * @Description:分页查询托管
     * @date 2013-12-27
     * @return String
     */
    public String getDelegateByPage()
    {
        try
        {
            String startTime = this.getRequest().getParameter("startTime");
            String startEndTime =
                this.getRequest().getParameter("startEndTime");
            String endStartTime =
                this.getRequest().getParameter("endStartTime");
            String endTime = this.getRequest().getParameter("endTime");
            String statusFlag = this.getRequest().getParameter("statusFlag");
            String limit = this.getRequest().getParameter("limit");
            String start = this.getRequest().getParameter("start");
            String delegateUserName =
                this.getRequest().getParameter("delegateUserName");
            Map paramMap = new HashMap();
            paramMap.put("user", this.getCurrentUser());
            paramMap.put("startTime", startTime);
            paramMap.put("startEndTime", startEndTime);
            paramMap.put("endStartTime", endStartTime);
            paramMap.put("statusFlag", statusFlag);
            paramMap.put("endTime", endTime);
            paramMap.put("delegateUserName", delegateUserName);
            paramMap.put("start", start);
            paramMap.put("limit", limit);
            ListVo listVo =
                this.activitiDelegateService.getDelegateByPage(paramMap);
            JsonUtil.outJson(listVo);
        }
        catch (Exception e)
        {
        	this.excepAndLogHandle(ActivitiCategoryAction.class, "分页查询托管", e, false);
            /*Log log = new Log();
            log.setIpUrl(ServletActionContext.getRequest().getRemoteAddr());
            log.setOpContent("分页查询托管失败,详细错误：" + e.getMessage());
            log.setOpDate(new Date());
            log.setType(dictService.getDictByTypeCode(Constant.LOGTYPE).get(1));
            log.setUser(RequestUtil.getLoginUser());
            logService.addLog(log);*/
            JsonUtil.outJson("{success:false,msg:'失败！'}");
        }
        
        return null;
    }
    
    /**
     * @Title updateUser
     * @author wlt
     * @Description:更新个人信息
     * @date 2014-8-19
     * @return
     */
    public String updateUser()
    {
        try
        {
            User currentUser =
                this.getCurrentUser();
            currentUser.setPhoneNo(user.getPhoneNo());
            currentUser.setShortNo1(user.getShortNo1());
            currentUser.setShortNo2(user.getShortNo2());
            currentUser.setEmail(user.getEmail());
            currentUser.setMobileNo1(user.getMobileNo1());
            currentUser.setMobileNo2(user.getMobileNo2());
            this.userService.addUpdateUser(currentUser);
            JsonUtil.outJson("{success:true,msg:'更新用户信息成功!'}");
        }
        catch (Exception e)
        {
            JsonUtil.outJson("{success:false,msg:'更新用户信息失败!'}");
            this.excepAndLogHandle(ActivitiDelegateAction.class, "更新个人信息", e,
					false);
        }
        return null;
    }
    
    /**
     * @Title uploadFile
     * @author wlt
     * @Description: 个人信息 - 上传头像
     * @date 2014-8-19
     * @return
     */
    public String uploadFile()
    {
        FileInputStream fin = null;
        try
        {
            User currentUser =
                this.getCurrentUser();
            String tag = this.getRequest().getParameter("tag");
            if (photoImg == null || photoImg.getTotalSpace() == 0)
            {
                JsonUtil.outJson("{success:false,msg:'请选择有效的文件上传(文件格式为jpg或者png)!'}");
            }
            else if (photoImg.length() >= 500 * 1024)
            {
                JsonUtil.outJson("{success:false,msg:'请选择大小为500k以内的文件!'}");
            }
            else if (photoImg != null)
            {
                fin = new FileInputStream(photoImg);
                activitiDelegateService.uploadPhoto(currentUser, fin, tag);
                JsonUtil.outJson("{success:true,msg:'上传成功!'}");
            }
            
        }
        catch (Exception e)
        {
        	JsonUtil.outJson("{success:false,msg:'上传失败!'}");
        	this.excepAndLogHandle(ActivitiDelegateAction.class, "上传文件", e,
					false);
            
        }
        finally
        {
            try
            {
                if (fin != null)
                {
                    fin.close();
                }
            }
            catch (IOException e)
            {
            	this.excepAndLogHandle(ActivitiDelegateAction.class, "上传文件", e,
    					false);
            }
        }
        return null;
    }
    
    /**
     * @Title previewImg
     * @author wlt
     * @Description: 预览头像
     * @date 2014-8-19
     * @return
     */
    public String previewImg()
    {
        
        try
        {
            com.dqgb.sshframe.user.entity.User currentUser =
                this.getCurrentUser();
            InputStream inputStream =
                activitiDelegateService.previewImg(currentUser);
            
            getResponse().setContentType("text/html");
            OutputStream os = getResponse().getOutputStream();
            int num;
            byte buf[] = new byte[1024];
            
            while ((num = inputStream.read(buf)) != -1)
            {
                os.write(buf, 0, num);
            }
            
            inputStream.close();
            os.close();
            
            // activitiDelegateService.previewImg(currentUser,String path);
            // JsonUtil.outJson("{success:true,msg:'更新用户信息成功!'}");
        }
        catch (Exception e)
        {
        	this.excepAndLogHandle(ActivitiDelegateAction.class, "预览个人头像", e,
					false);
            // JsonUtil.outJson("{success:false,msg:'更新用户信息失败!'}");
        }
        return null;
    }
    
    /**
     * 根据ID查询用户信息（用于个人信息页面显示）
     * @Title queryUserInfoById
     * @author wlt
     * @Description:
     * @date 2014-8-19
     * @return
     */
    public void queryUserInfoById(){
    	try {
			Map<String, String> map = RequestUtil.getParameterMap(getRequest());
			JsonUtil.outJson(activitiDelegateService.queryUserInfoById(map));
		} catch (Exception e) {
			JsonUtil.outJson("{success:false,msg:'获取组织信息失败！'}");
			this.excepAndLogHandle(ActivitiDelegateAction.class, "获取个人组织信息", e,
					false);
		}
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
    
    
    
}
