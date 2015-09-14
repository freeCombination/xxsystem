package com.xx.system.user.action;

import com.xx.system.common.action.BaseAction;

/**
 * AdminAction AdminAction相关Anction
 * 
 * @version V1.20,2013-11-25 上午11:22:43
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class AdminAction extends BaseAction {
    
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 返回登录信息
     * 
     * @Title adminLeft
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String adminLeft() {
        return SUCCESS;
    }
    
    /**
     * 返回登录信息
     * 
     * @Title toAdmin
     * @author wanglc
     * @date 2013-11-25
     * @return
     */
    public String toAdmin() {
        return SUCCESS;
    }
}
