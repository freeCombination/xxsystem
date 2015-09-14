package com.xx.system.common.constant;

import java.io.File;

/**
 * 系统模块常量文件
 * 
 * @version V1.20,2013-12-5 下午5:28:30
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class SystemConstant {
    
    /** @Fields CURRENT_USER : 当前用户 */
    public static final String CURRENT_USER = "CurrentUser";
    
    /** @Fields DATE_PATTEN_LONG : 系统日期格式，带时分秒 */
    public static final String DATE_PATTEN_LONG = "yyyy-MM-dd hh:mm:ss";
    
    /** @Fields DATE_PATTEN_SHORT : 日期格式，简写 */
    public static final String DATE_PATTEN_SHORT = "yyyy-MM-dd";
    
    /** @Fields IS_NOT_DELETE : 删除标识符：0未删除 */
    public static final int IS_NOT_DELETE = 0;
    
    /** @Fields IS_DELETE : 删除标识符：1已删除 */
    public static final int IS_DELETE = 1;
    
    /** @Fields PROCESS_AUTO_LOGINNAME : 流程申请自动提交人员 */
    public static final String PROCESS_AUTO_LOGINNAME =
        "AUTO_COMMIT_APPLY_USER";
    
    /** @Fields PAGE_FOLDER_ACTIVITI : 流程表单目录 */
    public static final String PAGE_FOLDER_ACTIVITI = "WEB-INF"
        + File.separator + "pages" + File.separator + "bpmForm";
    
    /** @Fields UPLOAD_FOLDER_ACTIVITI : 流程模板创建目录 */
    public static final String UPLOAD_FOLDER_ACTIVITI = "process";
    
}
