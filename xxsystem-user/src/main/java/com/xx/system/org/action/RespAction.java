package com.xx.system.org.action;

import java.util.ArrayList;
import java.util.List;

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
}
