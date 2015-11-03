package com.xx.grade.personal.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xx.grade.personal.service.IPersonalWeightService;
import com.xx.grade.personal.vo.IndexTypeRoleWeightVo;
import com.xx.grade.personal.vo.PersonalWeightVo;
import com.xx.system.common.action.BaseAction;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.RequestUtil;
import com.xx.system.common.vo.ListVo;

/**
 * 个人评分权重action
 * 
 * @author wujialing
 */
public class PersonalWeightAction extends BaseAction {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Autowired
	@Qualifier("personalWeightService")
	private IPersonalWeightService personalWeightService;
	
	/**
	 * 权重实体
	 */
	private PersonalWeightVo personalWeightVo ;
	
	/**
	 * 角色权重列表
	 */
	private List<IndexTypeRoleWeightVo> rwList = new ArrayList<IndexTypeRoleWeightVo>();

	public PersonalWeightVo getPersonalWeightVo() {
		return personalWeightVo;
	}

	public void setPersonalWeightVo(PersonalWeightVo personalWeightVo) {
		this.personalWeightVo = personalWeightVo;
	}

	public List<IndexTypeRoleWeightVo> getRwList() {
		return rwList;
	}

	public void setRwList(List<IndexTypeRoleWeightVo> rwList) {
		this.rwList = rwList;
	}

	/**
	 * 获取个人评分权重列表
	 * 
	 * @return
	 */
	public String getPersonalWeightList() {
		try {
			Map<String, String> paramMap = RequestUtil.getParameterMap(getRequest());
			ListVo<PersonalWeightVo> resultList = this.personalWeightService.getPersonalWeightList(paramMap);
			JsonUtil.outJson(resultList);
		} catch (Exception e) {
			this.excepAndLogHandle(PersonalWeightAction.class, "获取个人评分权重列表失败", e,
					false);
		}
		return null;
	}
	
	/**
	 * 添加个人评分权重
	 * 
	 * @return
	 */
    public String addPersonalWeigh() {
        try {
            personalWeightService.addPersonalWeigh(personalWeightVo);
            JsonUtil.outJson("{success:true,msg:'添加个人评分权重成功！'}");
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'添加个人评分权重失败！'}");
            this.excepAndLogHandle(PersonalWeightAction.class, "添加个人评分权重失败", e, false);
        }
        return null;
    }
    
	/**
	 * 修改个人评分权重
	 * 
	 * @return
	 */
    public String updatePersonalWeigh() {
        try {
            personalWeightService.updatePersonalWeigh(personalWeightVo);
            JsonUtil.outJson("{success:true,msg:'修改个人评分权重成功！'}");
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'修改个人评分权重失败！'}");
            this.excepAndLogHandle(PersonalWeightAction.class, "添加个人评分权重失败", e, false);
        }
        return null;
    }
    
    /**
     * 删除个人评分权重
     * 
     * @return
     */
    public String deletePersonalWeigh() {
        try {
            String ids = this.getRequest().getParameter("ids");
            personalWeightService.deletePersonalWeigh(ids);
            JsonUtil.outJson("{success:true,msg:'删除个人评分权重成功！'}");
            this.excepAndLogHandle(PersonalWeightAction.class, "删除个人评分权重", null, true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:false,msg:'删除个人评分权重失败！'}");
            this.excepAndLogHandle(PersonalWeightAction.class, "个人评分权重", e, false);
        }
        return null;
    }
}
