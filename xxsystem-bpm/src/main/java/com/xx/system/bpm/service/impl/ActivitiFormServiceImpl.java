/**
 * @文件名 ActivitiCategoryServiceImpl.java
 * @版权 Copyright 2009-2013 版权所有：中国石油四川石化有限责任公司
 * @描述 流程模板种类（类型）的逻辑层实现
 * @修改人 zhxh
 * @修改时间 2013-12-25 下午5:12:47
 */
package com.dqgb.sshframe.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.beans.BeanCopier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dqgb.sshframe.bpm.dao.IActivitiFormDao;
import com.dqgb.sshframe.bpm.entity.ActivitiCategory;
import com.dqgb.sshframe.bpm.entity.ActivitiForm;
import com.dqgb.sshframe.bpm.service.IActivitiCategoryService;
import com.dqgb.sshframe.bpm.service.IActivitiFormService;
import com.dqgb.sshframe.bpm.vo.ActivitiCategoryVo;
import com.dqgb.sshframe.bpm.vo.ActivitiFormVo;
import com.dqgb.sshframe.common.exception.ServiceException;
import com.dqgb.sshframe.common.vo.ListVo;

@Transactional(readOnly = true)
@Service("activitiFormService")
public class ActivitiFormServiceImpl implements IActivitiFormService
{
    @Autowired
    @Qualifier("activitiFormDao")
    /**流程表单持久层接口*/
    private IActivitiFormDao activitiFormDao;
    
    @Autowired
    @Qualifier("activitiCategoryService")
    /**流程种类层接口*/
    private IActivitiCategoryService activitiCategoryService;
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addActivitiForm(ActivitiForm activitiForm)
    {
        List<ActivitiCategory> categories =
            this.judgeFormInfo(activitiForm.getCategories());
        if (categories.size() < 1)
        {
            throw new ServiceException("不存在任何的种类!");
        }
        if (categories.size() != activitiForm.getCategories().size())
        {
            throw new ServiceException("存在选择的种类不存在，请重新选择!");
        }
        activitiForm.setAdaptationNode("," + activitiForm.getAdaptationNode()
            + ",");
        this.activitiFormDao.addActivitiForm(activitiForm);
        
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateActivitiForm(ActivitiForm activitiForm)
    {
        List<ActivitiCategory> categories =
            this.judgeFormInfo(activitiForm.getCategories());
        Map map = new HashMap();
        map.put("isDelete", 0);
        map.put("id", activitiForm.getId());
        List<ActivitiForm> forms = this.activitiFormDao.getActivitiForm(map);
        if (forms.size() < 1)
        {
            throw new ServiceException("数据已经不存在!");
        }
        ActivitiForm updateForm = forms.get(0);
        updateForm.setAdaptationNode(activitiForm.getAdaptationNode());
        updateForm.setDescription(activitiForm.getDescription());
        updateForm.setFormName(activitiForm.getFormName());
        updateForm.setFormUrl(activitiForm.getFormUrl());
        updateForm.setCategories(categories);
        this.activitiFormDao.updateActivitiForm(updateForm);
        
    }
    
    /**
     * 
     * @Title judgeFormInfo
     * @author zhxh
     * @Description:判断表单信息
     * @date 2013-12-26
     */
    private List<ActivitiCategory> judgeFormInfo(
        List<ActivitiCategory> tempCategories)
    {
        List<ActivitiCategory> categories = new ArrayList<ActivitiCategory>();
        int length = tempCategories.size();
        int num = 100;
        int count = 0;
        if (length % num == 0)
        {
            count = length / num;
        }
        else
        {
            count = length / num + 1;
        }
        StringBuffer ids = new StringBuffer(0);
        for (int i = 0; i < count; i++)
        {
            ids.delete(0, ids.length());
            for (int j = i * num; j < (i + 1) * num && j < length; j++)
            {
                ids.append(tempCategories.get(j).getCategoryId()).append(",");
            }
            List<ActivitiCategory> categorieTemps =
                this.activitiCategoryService.getActivitiCategoryById(ids.substring(0,
                    ids.length() - 1));
            categories.addAll(categorieTemps);
        }
        return categories;
    }
    
    @Override
    public List<ActivitiForm> getActivitiForm(Map map)
    {
        return this.activitiFormDao.getActivitiForm(map);
    }
    
    @Override
    public ListVo<ActivitiFormVo> getActivitiFormByPage(Map map)
    {
        ListVo<ActivitiForm> listVo =
            this.activitiFormDao.getActivitiFormByPage(map);
        List<ActivitiFormVo> vos = new ArrayList<ActivitiFormVo>();
        ListVo<ActivitiFormVo> returnListVo = new ListVo<ActivitiFormVo>();
        returnListVo.setTotalSize(listVo.getTotalSize());
        returnListVo.setList(vos);
        BeanCopier copier =
            BeanCopier.create(ActivitiForm.class, ActivitiFormVo.class, false);
        BeanCopier copier1 =
            BeanCopier.create(ActivitiCategory.class,
                ActivitiCategoryVo.class,
                false);
        for (ActivitiForm form : listVo.getList())
        {
            ActivitiFormVo vo = new ActivitiFormVo();
            copier.copy(form, vo, null);
            List<ActivitiCategoryVo> categories =
                new ArrayList<ActivitiCategoryVo>();
            vo.setCategories(categories);
            for (ActivitiCategory c : form.getCategories())
            {
                if (c.getIsDelete() == 0)
                {
                    ActivitiCategoryVo categoryVo = new ActivitiCategoryVo();
                    copier1.copy(c, categoryVo, null);
                    categories.add(categoryVo);
                }
            }
            vos.add(vo);
        }
        return returnListVo;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteActivitiForm(String ids)
    {
        try
        {
            List<ActivitiForm> forms =
                this.activitiFormDao.queryEntitys("from ActivitiForm where id in("
                    + ids + ")");
            for (ActivitiForm form : forms)
            {
                form.setCategories(null);
            }
            this.activitiFormDao.deleteEntities(forms);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(),e);
        }
    }
    
}
