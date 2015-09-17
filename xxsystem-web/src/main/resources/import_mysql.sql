insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (1, 'USERTYPE', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '用户类型', '1', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 1, '0', null);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (2, 'POST', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '职位', '2', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 1, '0', null);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (3, 'JOB', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '职务', '3', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 1, '0', null);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (4, 'POSTTITLE', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '职称', '4', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 1, '0', null);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (5, 'JOBLEVEL', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '职级', '5', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 1, '0', null);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (6, 'ROLETYPE', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '角色类型', '6', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 1, '0', null);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (7, 'ORGTYPE', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '组织类型', '7', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 1, '0', null);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (8, 'LOCALUSER', 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '本地用户', '0', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 1);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (11, 'ZYUSER', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '中油用户', '1', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 1);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (12, 'RESOURCETYPE', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '资源类型', '1', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 1, '0', null);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (13, 'COMPANY', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '公司', '4', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 12);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (14, 'PAGE', 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', '页面', '2', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 12);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (15, 'BUTTON', 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', '按钮', '3', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 12);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (21, null, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '职位一', '21', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 2);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (22, null, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '职位二', '22', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 2);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (31, null, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '财务总监', '31', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 3);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (32, null, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '处长', '32', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 3);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (33, null, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '行政管理', '31', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 3);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (34, null, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '副处长', '32', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 3);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (35, null, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', 'HSE管理', '31', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 3);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (36, null, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '车间主任', '32', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 3);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (37, null, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '综合管理员', '31', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 3);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (38, null, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '劳资工程师', '32', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 3);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (41, null, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '职称一', '41', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 4);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (42, null, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '职称二', '42', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 4);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (51, null, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '职级一', '51', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 5);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (52, null, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '职级二', '52', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 5);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (53, 'LOGTYPE', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '日志类型', '1', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 1, '0', null);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (54, 'SYS_LOG', 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '系统日志', '2', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 53);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (56, 'ERR_LOG', 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '错误日志', '3', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 53);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (63, null, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '流程角色', '61', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 6);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (62, null, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '系统角色', '62', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 6);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (71, null, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', '公司', '71', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 7);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (72, null, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '部门', '72', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 7);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (73, null, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', '装置', '72', 'DictionaryType-1c85e7a1-49cb-4ae4-8321-a7216f01d23a', 2, '0', 7);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (1919, 'asdff', null, 'fasd', 'asdfasdf', null, 1, '0', null);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (4051, 'zhiwue', null, '职位2', 'asdfas', null, 2, '0', null);

insert into T_DICTIONARY (PK_ID, DICT_CODE, DICT_UUID, DICTIONARY_NAME, DICTIONARY_VALUE, FK_DICT_TYPE_UUID, LEVEL_ORDER, STATUS, PARENT_ID)
values (2585, '234dd', null, '234234', 'sdsd', null, 4, '0', 1919);

-- 初始化资源数据
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (35, '系统管理', 'XTGL', 14, 'XTGL', '系统管理', null, '5', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (2, '用户管理', 'YHGL', 14, 'user/toUserIndex.action', '用户管理页面跳转', 35, '4', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (3, '资源管理', 'ZYGL', 14, 'resource/toResourceIndex.action', '资源管理页面跳转', 35, '7', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (4, '角色管理', 'JSGL', 14, 'role/toRoleIndex.action', '角色管理页面跳转', 35, '8', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (5, '组织管理', 'ZZGL', 14, 'org/toOrgIndex.action', '组织管理页面跳转', 35, '5', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (6, '字典管理', 'ZDGL', 14, 'dict/toDictIndex.action', '字典管理页面跳转', 35, '10', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (7, '日志管理', 'RZGL', 14, 'log/toLogIndex.action', '日志管理页面跳转', 35, '11', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (8, '流程管理', 'LCGL', 14, 'bpm/toProcessManage.action', '流程管理页面跳转', null, '4', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (10, '用户管理-添加按钮', 'usermanage_add_btn', 15, '${ctx}/user/addUser.action', '用户管理-添加按钮', 2, '1', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (11, '用户管理-删除按钮', 'usermanage_delete_btn', 15, '${ctx}/user/deleteUser.action', '用户管理-删除按钮', 2, '2', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (12, '用户管理-修改按钮', 'usermanage_update_btn', 15, '${ctx}/user/editUser.action', '用户管理-修改按钮', 2, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (13, '用户管理-EXCEL操作按钮组', 'usermanage_excel_btn', 15, null, '用户管理-EXCEL操作按钮组', 2, '4', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (14, '用户管理-EXCEL操作按钮组-用户导入按钮', 'usermanage_excel_userImp_btn', 15, '${ctx}/user/uploadExcelToBacthImportUser.action', '用户管理-EXCEL操作按钮组-用户导入按钮', 13, '1', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (15, '用户管理-EXCEL操作按钮组-用户导出按钮', 'usermanage_excel_userExp_btn', 15, '${ctx}/user/exportAllUsers.action', '用户管理-EXCEL操作按钮组-用户导出按钮', 13, '2', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (17, '用户管理-EXCEL操作按钮组-用户导入模版下载按钮', 'usermanage_excel_userImptemplate_btn', 15, '${ctx}/user/downloadUserExcelTemplate.action', '用户管理-EXCEL操作按钮组-用户导入模版下载按钮', 13, '4', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (19, '角色管理-添加按钮', 'rolemanage_add_btn', 15, '/role/addRole.action', '角色管理-添加按钮', 4, null, 0, now(), 0, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (20, '角色管理-删除按钮', 'rolemanage_delete_btn', 15, '${ctx}/role/updateRole.action', '角色管理-删除按钮', 4, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (21, '角色管理-修改按钮', 'rolemanage_update_btn', 15, '${ctx}/role/deleteRole.action', '角色管理-修改按钮', 4, '3', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (22, '资源管理-添加父级菜单', 'resourcemanage_addpra_btn', 15, '${ctx}/resource/addResource.action', '资源管理-添加父级菜单', 3, '1', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (23, '资源管理-添加菜单', 'resourcemanage_add_btn', 15, '${ctx}/resource/addResource.action', '资源管理-添加菜单', 3, '2', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (24, '资源管理-删除菜单', 'resourcemanage_delete_btn', 15, '${ctx}/resource/deleteResource.action', '资源管理-删除菜单', 3, '3', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (25, '资源管理-修改菜单', 'resourcemanage_update_btn', 15, '${ctx}/resource/editResource.action', '资源管理-修改菜单', 3, '4', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (26, '组织管理-添加组织', 'organization_addOrg_btn', 15, '${ctx}/org/addOrg.action', '组织管理-添加组织', 5, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (27, '组织管理-删除组织', 'organization_deleteOrg_btn', 15, '${ctx}/org/delOrg.action', '组织管理-删除组织', 5, '2', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (28, '用户管理-同步按钮', 'usermanage_synch_btn', 15, '${ctx}/user/synchronizeUserInfo.action', '用户管理-同步按钮', 2, '5', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (29, '角色管理-同步按钮', 'rolemanage_synch_btn', 15, '${ctx}/role/synchronizeRoleInfo.action', '角色管理-同步按钮', 4, '4', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (30, '资源管理-同步按钮', 'resourcemanage_synch_btn', 15, '${ctx}/resource/synchronizeResourceInfo.action', '资源管理-同步按钮', 3, '5', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (31, '组织管理-同步按钮', 'organization_synch_btn', 15, '${ctx}/org/synchOrgData.action', '组织管理-同步按钮', 5, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (34, '组织管理-修改按钮', 'organization_update_btn', 15, '${ctx}/org/updateOrg.action', '组织管理-修改按钮', 5, '5', 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 1);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (37, '字典管理-修改按钮', 'dict_update_btn', 15, '${ctx}/dict/updateDictInfo.action', '字典管理-修改按钮', 6, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (38, '字典管理-添加按钮', 'dict_add_btn', 15, '${ctx}/dict/addDictInfo.action', '字典管理-添加按钮', 6, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (39, '字典管理-删除按钮', 'dict_delete_btn', 15, '${ctx}/dict/deleteTypes.action', '字典管理-删除按钮', 6, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (40, '个人中心', 'GRZX', 14, 'bpm/toPersonManage.action', '个人中心', null, '1', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (41, '班组管理', 'BZGL', 14, 'workteam/toWorkTeam.action', '班组管理页面跳转', 35, '12', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (42, '班次管理', 'BCGL', 14, 'workturns/toRoundPage.action', '班次管理页面跳转', 35, '13', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (43, '倒班管理', 'DBGL', 14, 'workturnsrule/toWorkTurnsRuleManage.action', '倒班管理页面跳转', 35, '14', 0, now(), 0, 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (44, '班组管理-添加按钮', 'workteam_add_btn', 15, 'workteam_add_btn', '添加按钮', 41, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (45, '班组管理-修改按钮', 'workteam_update_btn', 15, 'workteam_update_btn', '修改按钮', 41, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (46, '班组管理删除按钮', 'workteam_delete_btn', 15, 'workteam_delete_btn', '删除按钮', 41, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (47, '班次管理-添加按钮', 'workround_add_btn', 15, 'workround_add_btn', '添加按钮', 42, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (48, '班次管理-修改按钮', 'workround_update_btn', 15, 'workround_update_btn', '修改按钮', 42, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (49, '班次管理-删除按钮', 'workround_delete_btn', 15, 'workround_delete_btn', '删除按钮', 42, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (50, '倒班管理-添加按钮', 'turnsrule_add_btn', 15, 'turnsrule_add_btn', '添加按钮', 43, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (51, '倒班管理-修改按钮', 'turnsrule_update_btn', 15, 'turnsrule_update_btn', '修改按钮', 43, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (52, '倒班管理-删除按钮', 'turnsrule_delete_btn', 15, 'turnsrule_delete_btn', '删除按钮', 43, null, 0, now(), 0, 'Dictionary-7c396583-7f52-4ef0-9973-2cbf3e9e573e', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (53, '群组管理', 'QZGL', 14, 'group/toGroupManagementIndex.action', '群组管理', 35, '6', 0, now(), 0, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (54, '添加按钮', 'group_add_btn', 15, 'group_add_btn', '群组_添加', 53, null, 0, now(), 0, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (55, '删除按钮', 'group_delete_btn', 15, 'group_delete_btn', '群组_删除', 53, null, 0, now(), 0, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (56, '修改按钮', 'group_update_btn', 15, 'group_update_btn', '群组_修改', 53, null, 0, now(), 0, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (57, '成员添加按钮', 'group_member_add_btn', 15, 'group_member_add_btn', '成员添加按钮', 53, null, 0, now(), 0, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (58, '成员删除按钮', 'group_member_delete_btn', 15, 'group_member_delete_btn', '成员删除按钮', 53, null, 0, now(), 0, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (59, '角色授权', 'QXGL', 14, 'role/toRoleMember.action', '角色成员页面跳转', 35, '9', 0, now(), 0, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (60, '添加_用户', 'add_role_user_btn', 15, 'add_role_user_btn', '添加_用户', 59, null, 0, now(), 0, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (61, '删除_用户', 'delete_role_user_btn', 15, 'delete_role_user_btn', '删除_用户', 59, null, 0, now(), 0, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (62, '添加_群组', 'add_role_group_btn', 15, 'add_role_group_btn', '添加_群组', 59, null, 0, now(), 0, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', 0);
insert into T_RESOURCE (RESOURCE_ID, RESOURCE_NAME, CODE, RESOURCE_TYPE, URLPATH, REMARKS, PARENT_ID, DIS_ORDER, STATUS, CREATE_DATE, RESOURCE_FROM, RESOURCE_TYPE_UUID, ISDELETEABLE) values (63, '删除_群组', 'delete_role_group_btn', 15, 'delete_role_group_btn', '删除_群组', 59, null, 0, now(), 0, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', 0);
insert into T_RESOURCE (RESOURCE_ID, CODE, CREATE_DATE, DIS_ORDER, ISDELETEABLE, REMARKS, RESOURCE_FROM, RESOURCE_NAME, RESOURCE_TYPE_UUID, STATUS, URLPATH, PARENT_ID, RESOURCE_TYPE) values (64, 'ZDXX', now(), 8, 0, null, 0, '站点信息', 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 0, 'site/index.html', null, 14);
insert into T_RESOURCE (RESOURCE_ID, CODE, CREATE_DATE, DIS_ORDER, ISDELETEABLE, REMARKS, RESOURCE_FROM, RESOURCE_NAME, RESOURCE_TYPE_UUID, STATUS, URLPATH, PARENT_ID, RESOURCE_TYPE) values (65, 'TYKJ', now(), 7, 0, null, 0, '通用控件', 'Dictionary-c2065724-3d53-4bf7-b29a-8b57e61c0976', 0, 'examples/index.jsp', null, 14);
-- 初始化用户数据
insert into T_USER (USER_ID, USERNAME, ERP_ID, PASSWORD, MOBILE_PHONE1, ELECTRONIC_SIGNATURE, PERSONAL_IMAGE, REALNAME, GENDER, STATUS,DIS_ORDER, ISENABLE, FK_TYPE, USER_ONLINE, FK_TEAM, SHORT_NO1, PHONE_NO, BIRTH_PLACE, EMAIL, CARD_CODE, ID_CARD, FK_POSTTITLE, FK_POST, FK_JOB1, FK_JOBLEVEL, BIRTHDAY, ISDELETABLE, MOBILE_PHONE2, SHORT_NO2, FK_JOB2, FK_USERTYPE_UUID, FK_TEAM_UUID, FK_POSTTITLE_UUID, FK_POST_UUID, FK_JOB1_UUID, FK_JOB2_UUID, FK_JOBLEVEL_UUID)values (1, 'jg_admin', '111111', 'e10adc3949ba59abbe56e057f20f883e', '12345678911', null, null, '管理员', '男', 0, null, 0, 8, 0, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null, null, 'Dictionary-d9e3b576-6880-4d15-aff8-97fba5daf111', null, null, null, null, null, null);

-- 初始化组织数据
INSERT INTO `t_organization` VALUES (1, 1, 0, 'ZCL_CD', '中储粮成都粮食储藏所', 0, 1, 71, NULL, 1);

-- 初始化用户组织关系
insert into T_ORG_USER (PK_ORG_USER_ID, FK_ORG_ID, FK_USER_ID, ISDELETE) values (1, 1, 1, 0);
-- 初始化角色数据
insert into T_ROLE (ROLE_ID, ROLE_NAME, ROLE_CODE, DESCRIPTION, ISDELETE, FK_ROLE_TYPE, FK_ROLE_TYPE_UUID, ISDELETEABLE) values (1, '管理员', 'admin', 'administrator', 0, 62, 'Dictionary-7b90b11c-54de-4901-9f5b-3dd8f66fa0b1', 0);
-- 初始化角色资源关系数据
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (2, 37, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (4, 4, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (5, 21, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (6, 29, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (7, 19, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (8, 20, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (9, 6, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (10, 57, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (11, 38, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (12, 39, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (13, 8, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (14, 7, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (15, 2, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (16, 10, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (17, 11, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (18, 13, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (19, 14, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (20, 15, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (21, 17, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (22, 28, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (23, 12, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (24, 3, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (25, 22, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (26, 23, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (27, 24, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (28, 25, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (29, 30, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (30, 5, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (31, 27, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (32, 34, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (33, 26, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (34, 31, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (35, 55, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (36, 54, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (37, 53, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (38, 58, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (39, 52, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (40, 51, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (41, 50, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (42, 49, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (43, 48, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (44, 47, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (45, 40, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (46, 41, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (47, 42, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (48, 43, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (49, 35, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (50, 44, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (51, 45, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (52, 46, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (53, 56, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (54, 62, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (55, 63, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (56, 61, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (57, 59, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (58, 60, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (59, 64, 1);
insert into T_ROLE_RESOURCE (ROLE_RESOURCE_ID, RESOURCE_ID, ROLE_ID) values (60, 65, 1);
-- 初始化用户角色权限范围关系
insert into T_ROLE_MEMBER_SCOPE (ID, ROLE_ID, USER_ID, GROUP_ID) values (1, 1, 1, null);
-- 权限范围
insert into T_SCOPE_MEMBER (ID, ROLE_MEMBER_ID, ORG_ID) values (1, 1, 1);
