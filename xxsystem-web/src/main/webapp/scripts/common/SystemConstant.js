var SystemConstant = {
	companyName:"**有限责任公司",
	projectName:"公司架构系统", 
    displayMsg: "第 {0} - {1} 条，共 {2} 条",
    emptyMsg: "没有数据",
    commonSize: 99,//分页条数
    alertTitle:"系统提示",
    deleteMsg:"确认删除这些数据吗?"   ,
    delSuccess:"删除成功",
    addSuccess:"添加成功",
    editSuccess:"修改成功",
    blankText:"该项不能为空！" ,
    regexText:"内容不符合规范！" ,
    ajaxFailure:"系统繁忙，请稍后再试！",
    delFailure:"删除失败，请联系管理员",
    delSuccess:"删除成功",
    deleteMsgStart:"确认删除这",
    deleteMsgEnd:"条数据吗?",
    addBtnText:"添加",
    deleteBtnText:"删除",
    modifyBtnText:"修改",
    updateBtnText:"更新",
    searchBtnText:"查询",
    synchronizeBtnText:"同步",
    resetBtnText:"重置",
    uploadBtnText:"上传",
    importBtnText:"导入",
    exportBtnText:"导出",
    saveBtnText:"确定",
    allocationOperaText:"分配",
    closeBtnText:"关闭",
    yesBtnText:"确定",
    cancelBtnText:"取消",
    resource_user:"用户",
    resource_role:"角色",
    resource_org:"组织",
    resource_res:"资源",
    resource_dict:"字典",
    resource_user_role:"用户角色关系",
    resource_role_resource:"角色资源关系",
    resource_user_org:"用户组织关系",
    importUserInfoBtnText:"用户导入",
    exportUserInfoBtnText:"用户导出",
    downloadUserInfoImportTemplateBtnText:"下载用户导入模板",
    importRoleInfoBtnText:"角色导入",
    exportRoleInfoBtnText:"角色导出",
    downloadRoleInfoImportTemplateBtnText:"下载角色导入模板",
    importUserRoleInfoBtnText:"用户角色关系导入",
    exportUserRoleInfoBtnText:"用户角色关系导出",
    downloadUserRoleInfoImportTemplateBtnText:"下载用户角色关系导入模板",
    importResourceInfoBtnText:"资源导入",
    exportResourceInfoBtnText:"资源导出",
    downloadResourceInfoImportTemplateBtnText:"下载用户角色关系导入模板",
    importRoleResourceInfoBtnText:"角色与资源关系导入",
    exportRoleResourceInfoBtnText:"角色与资源关系导出",
    downloadRoleResourceInfoImportTemplateBtnText:"下载角色与资源关系导入模板",
    processTemplate:'模板',
    process:'流程',
    gender_male:"男",
    gender_female:"女",
    basicInfo:"基本信息",
    please:'请',
    chooseOneInfo:'请选择一条数据',
    viewInfo:'查看',
    manageInfo:"管理",
    onlyOneInfo:"只能选择一条数据",
    notApproveInfo:"未找到相关审核记录!",
    expandInfo:"扩展信息",
    userIsExist:"用户不存在！",
    userDisable:"用户已禁用！",
    userResourcesFailure:"获取用户资源失败！",
    theSystemIsBusy:"系统繁忙，请稍后再试！",
    expandInfo:"扩展信息",
	passwordMinLength:6,		//用户密码最小长度
	logoutError:"注销错误，请联系管理员",
	startTime:"开始时间",
	endTime:"结束时间",
	addParentBtnText:"添加根节点",
	addNextBtnText:"添加下级资源",
	addNextOrgBtnText:"添加下级组织",
	resetPwd:"密码重置",
	updatePwd:"修改密码",
	defaultPassword:'123456'
};
var basePath=(function(){
	var href=window.location.href;
	var host=window.location.host;
	var index = href.indexOf(host)+host.length+1; //host结束位置的索引（包含/）
	return href.substring(0,href.indexOf('/',index));
})(window);