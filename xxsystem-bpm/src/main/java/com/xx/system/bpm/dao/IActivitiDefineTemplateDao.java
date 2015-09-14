/**
 * @文件名 IActivitiDefineTemplateDao.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程模板持久层接口
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.dao;

import java.util.List;
import java.util.Map;

import com.dqgb.sshframe.bpm.entity.ActivitiDefineTemplate;
import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.vo.ListVo;

/**
 * 
 * 流程模板持久层接口
 * 
 * @author zhxh
 * @version V1.20,2013-12-26 上午9:07:02
 * @since V1.20
 * @depricated
 */

public interface IActivitiDefineTemplateDao extends IBaseDao
{
    /**
     * 
     * @Title addActivitiDefineTemplate
     * @author zhxh
     * @Description: 添加流程模版定义数据
     * @date 2013-12-26
     * @param activitiDefineTemplate
     */
    public void addActivitiDefineTemplate(
        ActivitiDefineTemplate activitiDefineTemplate);
    
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
     * @Description:更新模板
     * @date 2013-12-27
     * @param activitiDefineTemplate
     */
    public void updateActivitiDefineTemplate(
        ActivitiDefineTemplate activitiDefineTemplate);
    
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
    
}
