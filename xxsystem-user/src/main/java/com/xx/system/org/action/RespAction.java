package com.xx.system.org.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xx.system.common.action.BaseAction;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.StringUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.org.service.IRespService;
import com.xx.system.org.vo.DutyVo;
import com.xx.system.org.vo.RespVo;

/**
 * 岗位管理Action
 * 
 * @version V1.40,2014年8月25日 上午11:49:31
 * @see [相关类/方法]
 * @since V1.40
 */
public class RespAction extends BaseAction {

	private static final long serialVersionUID = 4085283941571490537L;
    
	@Resource
    public IRespService respService;
	
	/**
     * @Fields respVo : 岗位对象vo
     */
    private RespVo respVo;

	public RespVo getRespVo() {
		return respVo;
	}

	public void setRespVo(RespVo respVo) {
		this.respVo = respVo;
	}
	
	private List<DutyVo> dvoLst = new ArrayList<DutyVo>();
    
	public List<DutyVo> getDvoLst() {
		return dvoLst;
	}

	public void setDvoLst(List<DutyVo> dvoLst) {
		this.dvoLst = dvoLst;
	}

	/**
	 * 分页查询岗位
	 */
	public String getRespList() {
		try {
			ListVo<RespVo> lv = respService.getRespList(getStart(), getLimit(), respVo);
			JsonUtil.outJson(lv);
		} catch (BusinessException e) {
			this.excepAndLogHandle(RespAction.class, "分页查询岗位", e, false);
		}
		
		return null;
	}
	
	/**
	 * 添加岗位
	 */
	public String addResp() {
		String msg = "{success:'false',msg:'岗位添加失败'}";
		try {
			respService.addResp(respVo, dvoLst);
			msg = "{success:'true',msg:'岗位添加成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(RespAction.class, "岗位添加", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
	}
	
	/**
	 * 修改岗位
	 */
	public String updateResp() {
		String msg = "{success:'false',msg:'岗位修改失败'}";
		try {
			respService.updateResp(respVo, dvoLst);
			msg = "{success:'true',msg:'岗位修改成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(RespAction.class, "修改岗位", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
	}
	
	/**
	 * 查询岗位对应的职责列表
	 */
	public String getDutyListByRespId() {
		try {
			String id = getRequest().getParameter("respId");
			Integer respId = StringUtil.isNotBlank(id) ? Integer.valueOf(id) : 0;
			JsonUtil.outJsonArray(respService.getDutyListByRespId(respId));
		} catch (Exception e) {
			this.excepAndLogHandle(RespAction.class, "查询岗位对应的职责列表", e, false);
		}
		return null;
	}
	
	/**
	 * 删除岗位
	 */
	public String delResps() {
		String msg = "{success:'false',msg:'删除岗位失败'}";
		try {
			String ids = getRequest().getParameter("ids");
			respService.delResps(ids);
			msg = "{success:'true',msg:'删除岗位成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(RespAction.class, "删除岗位", e, false);
		}
		JsonUtil.outJson(msg);
		return null;
	}
	
	/**
	 * 检查岗位编号的唯一性
	 */
	public String checkNumber() {
		String number = getRequest().getParameter("value");
		try {
			Map<String, Object> vaildator = respService.checkNumber(number);
            JsonUtil.outJson(vaildator);
		} catch (Exception e) {
			this.excepAndLogHandle(RespAction.class, "检查岗位编号的唯一性", e, false);
		}
		return null;
	}
	
	/**
	 * 锁定和解锁岗位
	 * @return
	 */
	public String lockUnLock() {
		String msg = "{success:'false',msg:'锁定岗位失败'}";
		String msg1 = "{success:'false',msg:'解锁岗位失败'}";
		
		String id = getRequest().getParameter("respId");
		String enable = getRequest().getParameter("enable");
		Integer respId = StringUtil.isNotBlank(id) ? Integer.valueOf(id) : 0;
		Integer en = StringUtil.isNotBlank(enable) ? Integer.valueOf(enable) : 0;
		try {
			respService.lockUnLock(respId);
			msg = "{success:'true',msg:'锁定岗位成功'}";
			msg1 = "{success:'true',msg:'解锁岗位成功'}";
		} catch (Exception e) {
			this.excepAndLogHandle(RespAction.class, "锁定和解锁岗位", e, false);
		}
		
		if (en == 1) {
			JsonUtil.outJson(msg);
		}
		else {
			JsonUtil.outJson(msg1);
		}
		return null;
	}
	
	/**
	 * 根据部门查询岗位
	 */
	public String getAllResp() {
		String orgId = getRequest().getParameter("orgId");
		int id = StringUtil.isNotBlank(orgId) ? Integer.valueOf(orgId) : 0;
		try {
			JsonUtil.outJsonArray(respService.getAllResp(id));
		} catch (Exception e) {
			this.excepAndLogHandle(RespAction.class, "锁定和解锁岗位", e, false);
		}
		
		return null;
	}
}
