/**
 * @文件名 IActivitiDefineTemplateService.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述  流程模板的逻辑层接口
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.service;

import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.bpm.entity.ActivitiDefineTemplate;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.vo.ListVo;

/**
 * 
 * 流程模板的逻辑层接口
 * 
 * @author zhxh
 * @version V1.20,2013-12-25 下午5:36:23
 * @since V1.20
 * 
 */
public interface IActivitiDefineTemplateService
{
    /**
     * 
     * @Title addActivitiDefineTemplate
     * @author zhxh
     * @Description:添加流程模版定义数据
     * @date 2013-12-27
     * @param xmlContent
     * @throws ServiceException
     */
    public void addActivitiDefineTemplate(String xmlContent)
        throws ServiceException;
    
    /**
     * 
     * @Title getActivitiDefineTemplate
     * @author zhxh
     * @Description:查询流程模板
     * @date 2013-12-27
     * @param map 字段-值
     * @return List<ActivitiDefineTemplate>
     */
    public List<ActivitiDefineTemplate> getActivitiDefineTemplate(Map map);
    
    /**
     * 
     * @Title updateActivitiDefineTemplate
     * @author zhxh
     * @Description:修改流程模板
     * @date 2013-12-27
     * @param xmlContent
     */
    public void updateActivitiDefineTemplate(String xmlContent);
    
    /**
     * 
     * @Title deleteActivitiDefineTemplate
     * @author zhxh
     * @Description:删除流程模板
     * @date 2013-12-27
     * @param ids
     */
    public void deleteActivitiDefineTemplate(String ids);
    
    /**
     * 
     * @Title getActivitiDefineTemplateByPage
     * @author zhxh
     * @Description:分页查询流程模板
     * @date 2013-12-27
     * @param map
     * @return ListVo
     */
    public ListVo getActivitiDefineTemplateByPage(Map map);

    /**
     * 判断是否存在活动的流程实例
     *
     * @Title isProcessExist
     * @author 王龙涛
     * @Description: 
     * @date 2014-11-27
     * @param params
     * @return
     */ 
    public Map<String, Object> isProcessExist(Map<String, String> params);
    
}
