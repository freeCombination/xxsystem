package com.xx.system.personal.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xx.system.common.action.BaseAction;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.RequestUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.personal.service.IPersonalGradeService;
import com.xx.system.personal.vo.PersonalGradeVo;
import com.xx.system.role.action.RoleAction;
import com.xx.system.user.entity.User;

/**
 * 个人评分action
 * 
 * @author wujialing
 */
public class PersonalGradeAction extends BaseAction {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("personalGradeService")
	private IPersonalGradeService personalGradeService ;
	
	/**
	 * 获取用户自评页面列表
	 * 
	 * @return
	 */
	public String getPersonalGradeForUserSelfList() {
        try {
            Map<String, String> paramMap =
                RequestUtil.getParameterMap(getRequest());
            User user =  getCurrentUser() ;
            if(user != null){
            	paramMap.put("userId", user.getUserId().toString());
            }
            ListVo<PersonalGradeVo> personalGradeList =
                this.personalGradeService.getPersonalGradeList(paramMap);
            JsonUtil.outJson(personalGradeList);
        }
        catch (Exception e) {
            this.excepAndLogHandle(PersonalGradeAction.class, "获取用户自评列表失败", e, false);
        }
        return null;
    }

}
