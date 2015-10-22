package com.xx.system.common.constant;

import java.io.File;

/**
 * 常量类
 * 
 * @version V1.20,2013-12-5 下午5:16:09
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class Constant {
    
    /** @Fields ORGTYPE : 组织类型： 0：公司；1：部门；2：小组；3：装置 */
    public static final String ORGTYPE = "ORGTYPE";
    
    /** @Fields LOGTYPE : 日志类型 1系统日志，2，用户日志 3，系统错误日志 */
    public static final String LOGTYPE = "LOGTYPE";
    
    /** @Fields USER_MENU : 用户菜单 */
    public static final String USER_MENU = "USER_MENU";
    
    /** @Fields USERTYPE : 用户类别 */
    public static final String USERTYPE = "USERTYPE";
    
    /** @Fields TEAM : 班组 */
    public static final String TEAM = "TEAM";
    
    /** @Fields JOB : 职务 */
    public static final String JOB = "JOB";
    
    /** @Fields POST : 职位 */
    public static final String POST = "POST";
    
    /** @Fields POSTTITLE : 职称 */
    public static final String POSTTITLE = "POSTTITLE";
    
    /** @Fields JOBLEVEL : 职级 */
    public static final String JOBLEVEL = "JOBLEVEL";
    
    /** @Fields JOBLEVEL : 资源类别 */
    public static final String RESOURCETYPE = "RESOURCETYPE";
    
    /** @Fields ZORE : 0 值 */
    public static final int ZORE = 0;
    
    /** @Fields ALLOW_DELETE : 0 允许删除 */
    public static final int ALLOW_DELETE = 0;
    
    /** @Fields NOT_ALLOW_DELETE : 1 禁止删除 */
    public static final int NOT_ALLOW_DELETE = 1;
    
    /** @Fields STATUS_NOT_DELETE : 0未删除 */
    public static final int STATUS_NOT_DELETE = 0;
    
    /** @Fields STATUS_DELETE : 1已删除 */
    public static final int STATUS_DELETE_BYXTJG = 1;
    
    /** @Fields STATUS_DELETE : 1已删除 */
    public static final int STATUS_DELETE_BYJCPT = 2;
    
    /** @Fields STATUS_FOREVERY_DELETE : 2永久删除 */
    public static final int STATUS_FOREVERY_DELETE = 2;
    
    /** @Fields ROOT : 根节点id */
    public final static String ROOT = "root";
    
    /** @Fields DATA :返回给grid控件的json数据中存放data的名字 */
    public final static String DATA = "data";
    
    /** @Fields TOTAL_SIZE :返回给grid控件的json数据中存放数据size的名字 */
    public final static String TOTAL_SIZE = "totalSize";
    
    /** @Fields PROCESS_DEFINITION_ID : 自定义流程id */
    public final static String PROCESS_DEFINITION_ID = "processDefinitionId";
    
    /** @Fields PROCESS_DEFINITION_NAME : 自定义流程名 */
    public final static String PROCESS_DEFINITION_NAME =
        "processDefinitionName";
    
    /** @Fields PROCESS_PATICIPATOR_FIELD : 自定义流程配置时 用户字段名 */
    public final static String PROCESS_PATICIPATOR_FIELD = "users";
    
    /** @Fields LDAPURL : 域服务器地址 */
    public static final String LDAPURL = "ldap://10.33.17.1:389";
    
    /** @Fields USERNAME_SUFFIX : 邮箱扩展名 */
    public static final String USERNAME_SUFFIX = "@petrochina.com.cn";
    
    /** @Fields USERNAME_SUFFIX_PTR : 邮箱第二种扩展名 */
    public static final String USERNAME_SUFFIX_PTR = "@ptr.petrochina";
    
    /** @Fields USER_LIST : application 对象中存储用户列表的 key值 */
    public final static String USER_LIST = "UserList";
    
    /** @Fields CURRENT_USER : session 对象中存储用户信息的 key值 */
    public final static String CURRENT_USER = "CurrentUser";
    
    /** @Fields RANDOM_CODE : 随机码 */
    public final static String RANDOM_CODE = "randomCode";
    
    /** @Fields CURRENT_USER_AUTHORITY : 当前用户所有权限(角色权限+用户权限) */
    public static final String CURRENT_USER_AUTHORITY = "currentUserAuthority";
    
    /** @Fields UPLOAD_FOLDER_IMAGE : 上传文件夹-图片 */
    public static final String UPLOAD_FOLDER_IMAGE = "upload" + File.separator
        + "images";
    
    /** @Fields USER_MENUS : 用户拥有的菜单列表 */
    public static final String USER_MENUS = "user_menus";
    
    /** @Fields SSOTAKEN : corys令牌 */
    public static final String SSOTAKEN = "SSOTAKEN";
    
    /** @Fields CORDYSTOKEN : cordys会话 */
    public static final String CORDYSTOKEN = "CORDYSTOKEN";
    
    /** @Fields CORDYSTEMPORGANIZATION : cordys临时部门 */
    public static final String CORDYSTEMPORGANIZATION =
        "CORDYSTEMPORGANIZATION";
    
    /** @Fields CORDYSTEMPROLE : cordys角色 */
    public static final String CORDYSTEMPROLE = "CORDYSTEMPROLE";
    
    /** @Fields DICTIONARY_LIST : 字典信息列表 */
    public static final String DICTIONARY_LIST = "dictionaryList";
    
    /** @Fields ENABLE : 禁用标识 0：未禁用 */
    public static final int ENABLE = 0;
    
    /** @Fields DISABLE : 禁用标识 1：已禁用 */
    public static final int DISABLE = 1;
    
    /** @Fields ENABLE_ADN_DISABLE : ENABLE_ADN_DISABLE */
    public static final int ENABLE_ADN_DISABLE = -1;
    
    /** @Fields STATUS_IS_DELETED : 删除标识 1：删除 */
    public static final int STATUS_IS_DELETED = 1;
    
    /** @Fields USER_TYPE_REMOTE :用户类型： 1：远程用户（中油邮箱登录） */
    public static final int USER_TYPE_REMOTE = 1;
    
    /** @Fields USER_TYPE_LOCAL : 用户类型：0：本地用户（本地数据库用户名密码登录） */
    public static final int USER_TYPE_LOCAL = 0;
    
    /** @Fields SSOTOKEN : 口令 */
    public static final String SSOTOKEN = "SSOTOKEN";
    
    /** @Fields SYS_CONFIG_TYPE_RESOURCE : 系统配制 资源类型 */
    public static final String SYS_CONFIG_TYPE_RESOURCE = "resource";
    
    /** @Fields RESOURCE_TYPE_CONTROL : 控件 */
    public static final String RESOURCE_TYPE_CONTROL = "控件";
    
    /** @Fields USER_CONTROL_KEY : 用户控制 */
    public static final String USER_CONTROL_KEY = "userControl";
    
    /** @Fields CURRENT_USER_KEY : 用户对象存放在session中KEY值 */
    public static final String CURRENT_USER_KEY = "current_user";
    
    /** @Fields CIPHERTEXT_PROPS_SUFFIX : 数据库参数的后缀名 */
    public static final String CIPHERTEXT_PROPS_SUFFIX = "_ciphertext";
    
    /** @Fields PROCESS_AUTO_LOGINNAME : 申请自动提交人员 */
    public static final String PROCESS_AUTO_LOGINNAME =
        "APPLY_AUTO_COMMIT_PERSON";
    
    /** @Fields UPLOAD_FOLDER_ACTIVITI : 上传文件夹-activiti */
    public static final String UPLOAD_FOLDER_ACTIVITI = "process";
    
    /** @Fields PAGE_FOLDER_ACTIVITI :page文件夹-activiti */
    public static final String PAGE_FOLDER_ACTIVITI = "pages" + File.separator
        + "bpmform";
    
    /** @Fields DEFAULT_PASSWORD :默认密码 */
    public static final String DEFAULT_PASSWORD = "123456";
    
    public static final String ROLETYPE = "ROLETYPE";
    
    /** @Fields USER :用户 */
    public static final String USER = "USER";
    
    /** @Fields GROUP :群组 */
    public static final String GROUP = "GROUP";
    
    /** @Fields ORG :部门 */
    public static final String ORG = "ORG";
    
    /** @Fields RES_PAGE :部门 */
    public static final String RES_PAGE = "PAGE";
    
    /** @Fields GRADE_QZFL :个人评分权重分类 */
    public static final String GRADE_QZFL = "GRADE_QZFL" ;
    
    /** @Fields QZFL_YBYG :个人评分权重分类-一般员工个人绩效评价 */
    public static final String QZFL_YBYG = "QZFL_YBYG" ;
    
    /** @Fields QZFL_YBYG :个人评分权重分类-主任、副主任绩效评价 */
    public static final String QZFL_BMLD = "QZFL_BMLD" ;
    
    /** @Fields QZFL_YBYG :个人评分权重分类-总工、副总工、质检中心总工绩效评价*/
    public static final String QZFL_GSLD = "QZFL_GSLD" ;
    
    /** @Fields GRADE_ZBFL :个人评分指标分类 */
    public static final String GRADE_ZBFL = "GRADE_ZBFL" ;
    
}
