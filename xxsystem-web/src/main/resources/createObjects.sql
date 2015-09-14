create sequence HIBERNATE_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
 cache 100
noorder;

create sequence SEQ_ACTIVITI_CATEGORY
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_ACTIVITI_FORM
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_ACTIVITI_TASK_ROLE
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_DICTIONARY
increment by 1
start with 1
 nomaxvalue
 nominvalue
nocycle
noorder;

create sequence SEQ_GROUP
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_GROUP_MEMBER
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_LOG
increment by 1
start with 1
 nomaxvalue
 nominvalue
nocycle
noorder;

create sequence SEQ_ORGANIZATION
increment by 1
start with 1
 nomaxvalue
 nominvalue
nocycle
noorder;

create sequence SEQ_ORG_USER
increment by 1
start with 1
 nomaxvalue
 nominvalue
nocycle
noorder;

create sequence SEQ_RESOURCE
increment by 1
start with 1
 nomaxvalue
 nominvalue
nocycle
noorder;

create sequence SEQ_ROLE
increment by 1
start with 1
 nomaxvalue
 nominvalue
nocycle
noorder;

create sequence SEQ_ROLE_MEMBER_SCOPE
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_ROLE_RESOURCE
increment by 1
start with 1
 nomaxvalue
 nominvalue
nocycle
noorder;

create sequence SEQ_RULE_WORK_TEAM_MAP
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_SCOPE_MEMBER
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_USER
increment by 1
start with 1
 nomaxvalue
 nominvalue
nocycle
noorder;

create sequence SEQ_WORK_ROUND
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_WORK_TEAM
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_WORK_TEAM_MEMBER
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_WORK_TURNS_PLAN
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence SEQ_WORK_TURNS_RULE
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence S_ACTIVITI_DEFINE_TEMPLATE
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence S_ACTIVITI_DELEGATE
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence S_ACTIVITI_PROCESS_APPROVAL
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

create sequence S_ACTIVITI_PROCESS_INST
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;


/*==============================================================*/
/* Table: SYS_ACTIVITI_CATEGORY                                 */
/*==============================================================*/
create table SYS_ACTIVITI_CATEGORY 
(
   PK_ACTIVITI_CATEGORY_ID NUMBER(10)           not null,
   CATEGORY_CODE        VARCHAR2(50)         not null,
   DESCRIPTION          VARCHAR2(1000),
   ISDELETE             NUMBER(10),
   LEAF                 NUMBER(10),
   NAME                 VARCHAR2(100)        not null,
   PARENTID             NUMBER(10),
   SORT                 NUMBER(10),
   constraint SYS_C0053661 primary key (PK_ACTIVITI_CATEGORY_ID)
);

/*==============================================================*/
/* Table: SYS_ACTIVITI_DEFINE_TEMPLATE                          */
/*==============================================================*/
create table SYS_ACTIVITI_DEFINE_TEMPLATE 
(
   PK_ACTIVITI_DEFINE_TEMPLATE_ID NUMBER(10)           not null,
   ISDELETE             NUMBER(10),
   NAME                 VARCHAR2(255)        not null,
   PROCESS_DEFINE_KEY   VARCHAR2(255)        not null,
   REAL_NAME            VARCHAR2(255),
   URL                  VARCHAR2(255)        not null,
   FK_CATEGORY_ID       NUMBER(10)           not null,
   constraint SYS_C0053667 primary key (PK_ACTIVITI_DEFINE_TEMPLATE_ID)
);

/*==============================================================*/
/* Table: SYS_ACTIVITI_DELEGATE                                 */
/*==============================================================*/
create table SYS_ACTIVITI_DELEGATE 
(
   PK_ACTIVITI_DELEGATE_ID NUMBER(10)           not null,
   DELEGATE_END_TIME    DATE,
   DELEGATE_START_TIME  DATE,
   DELEGATE_STATUS      NUMBER(10),
   ISDELETE             NUMBER(10),
   DELEGATE_ID          NUMBER(10),
   OWNER_ID             NUMBER(10),
   constraint SYS_C0053678 primary key (PK_ACTIVITI_DELEGATE_ID)
);

/*==============================================================*/
/* Table: SYS_ACTIVITI_FORM                                     */
/*==============================================================*/
create table SYS_ACTIVITI_FORM 
(
   PK_ACTIVITI_FORM_ID  NUMBER(10)           not null,
   ADAPTATION_NODE      VARCHAR2(500),
   DESCRIPTION          VARCHAR2(500),
   FORM_NAME            VARCHAR2(200),
   FORM_URL             VARCHAR2(200),
   ISDELETE             NUMBER(10),
   constraint SYS_C0053680 primary key (PK_ACTIVITI_FORM_ID)
);

/*==============================================================*/
/* Table: SYS_ACTIVITI_FORM_CATEGORY                            */
/*==============================================================*/
create table SYS_ACTIVITI_FORM_CATEGORY 
(
   PK_ACTIVITI_FORM_ID  NUMBER(10)           not null,
   PK_CATEGORY_ID       NUMBER(10)           not null
);

/*==============================================================*/
/* Table: SYS_ACTIVITI_PROCESS_APPROVAL                         */
/*==============================================================*/
create table SYS_ACTIVITI_PROCESS_APPROVAL 
(
   PK_ACTIVITI_PRO_APPROVAL_ID NUMBER(10)           not null,
   APPROVAL             NUMBER(10),
   ELECTRONIC_SIGNATURE BLOB,
   ELECTRONIC_SOURCE    NUMBER(10),
   ELECTRONIC_URL       VARCHAR2(300),
   ISDELETE             NUMBER(10),
   OPINION              VARCHAR2(2000),
   PROCESS_INSTANCE_ID  VARCHAR2(255),
   TASKID               VARCHAR2(255),
   FK_USER_ID           NUMBER(10),
   constraint SYS_C0053671 primary key (PK_ACTIVITI_PRO_APPROVAL_ID)
);

/*==============================================================*/
/* Table: SYS_ACTIVITI_PROCESS_INST                             */
/*==============================================================*/
create table SYS_ACTIVITI_PROCESS_INST 
(
   PK_ACTIVITI_PROCESS_INST_ID NUMBER(10)           not null,
   BUSINESS_ID          VARCHAR2(1000),
   BUSINESS_ORG         VARCHAR2(300),
   ISDELETE             NUMBER(10),
   PROCESS_CODE         VARCHAR2(255),
   PROCESS_CREATE_DATE  DATE,
   PROCESS_DEFINEKEY    VARCHAR2(255),
   PROCESS_INSTANCE_ID  VARCHAR2(255),
   PROCESS_NAME         VARCHAR2(255),
   PROCESS_TYPE         VARCHAR2(255),
   FK_ACTIVITI_CATEGORY_ID NUMBER(10),
   FK_PROCESS_CREATER   NUMBER(10),
   constraint SYS_C0053673 primary key (PK_ACTIVITI_PROCESS_INST_ID)
);

/*==============================================================*/
/* Table: SYS_ACTIVITI_ROLE                                     */
/*==============================================================*/
create table SYS_ACTIVITI_ROLE 
(
   PK_ACTIVITI_TASK_ROLE_ID NUMBER(10)           not null,
   TASKID               VARCHAR2(300)        not null,
   FK_ROLE_ID           NUMBER(10),
   constraint SYS_C0053676 primary key (PK_ACTIVITI_TASK_ROLE_ID)
);

/*==============================================================*/
/* Table: T_ATTACH                                              */
/*==============================================================*/
create table T_ATTACH 
(
   ATTACH_ID            NUMBER(10)           not null,
   ATTACH_NAME          VARCHAR2(100)        not null,
   ATTACH_TYPE          VARCHAR2(100),
   ATTACH_URL           VARCHAR2(200)        not null,
   DESCRIPTION          VARCHAR2(1000),
   DOWNLOAD_TIME        NUMBER(10),
   FILE_SIZE            VARCHAR2(50),
   REMARKS              VARCHAR2(2000),
   STATUS               NUMBER(10)           not null,
   SUBMIT_DATE          DATE                 not null,
   ATTACH_GROUP_ID      NUMBER(10)           not null,
   constraint SYS_C0045410 primary key (ATTACH_ID)
);

/*==============================================================*/
/* Table: T_ATTACH_GROUP                                        */
/*==============================================================*/
create table T_ATTACH_GROUP 
(
   ATTACH_GROUP_ID            NUMBER(10)           not null,
   REMARKS              VARCHAR2(2000),
   STATUS               NUMBER(10)           not null,
   SUBMIT_DATE          DATE                 not null,
   constraint SYS_C0045414 primary key (ATTACH_GROUP_ID)
);

/*==============================================================*/
/* Table: T_DICTIONARY                                          */
/*==============================================================*/
create table T_DICTIONARY 
(
   PK_ID                NUMBER(10)           not null,
   DICT_CODE            VARCHAR2(255),
   DICT_UUID            VARCHAR2(255),
   DICTIONARY_NAME      VARCHAR2(48),
   DICTIONARY_VALUE     VARCHAR2(480),
   FK_DICT_TYPE_UUID    VARCHAR2(255),
   LEVEL_ORDER          NUMBER(10),
   STATUS               VARCHAR2(1),
   PARENT_ID            NUMBER(10),
   constraint SYS_C0053979 primary key (PK_ID)
);

/*==============================================================*/
/* Table: T_GROUP                                               */
/*==============================================================*/
create table T_GROUP 
(
   ID                   NUMBER(10)           not null,
   CREATE_DATE          DATE                 not null,
   GROUP_NAME           VARCHAR2(100)        not null,
   REMARK               VARCHAR2(2000),
   constraint SYS_C0053983 primary key (ID)
);

/*==============================================================*/
/* Table: T_GROUP_MEMBER                                        */
/*==============================================================*/
create table T_GROUP_MEMBER 
(
   ID                   NUMBER(10)           not null,
   GROUP_ID             NUMBER(10)           not null,
   ORG_ID               NUMBER(10),
   USER_ID              NUMBER(10),
   constraint SYS_C0053986 primary key (ID)
);

/*==============================================================*/
/* Table: T_LOG                                                 */
/*==============================================================*/
create table T_LOG 
(
   PK_LOG_ID            NUMBER(19)           not null,
   IP_URL               VARCHAR2(50),
   FK_LOG_TYPE_UUID     VARCHAR2(255),
   OP_CONTENT           VARCHAR2(4000),
   OP_DATE              DATE                 not null,
   LOG_TYPE             NUMBER(10),
   USER_ID              NUMBER(10),
   constraint SYS_C0053989 primary key (PK_LOG_ID)
);

/*==============================================================*/
/* Table: T_ORGANIZATION                                        */
/*==============================================================*/
create table T_ORGANIZATION 
(
   ORG_ID               NUMBER(10)           not null,
   DIS_ORDER            NUMBER(10),
   ENABLE               NUMBER(10)           not null,
   ISDELETEABLE         NUMBER(10)           not null,
   ORG_CODE             VARCHAR2(50),
   ORG_FROM             NUMBER(10),
   ORG_NAME             VARCHAR2(100)        not null,
   FK_ORGTYPE_UUID      VARCHAR2(255),
   STATUS               NUMBER(10)           not null,
   FK_ORGTYPE           NUMBER(10),
   PARENT_ID            NUMBER(10),
   constraint SYS_C0053995 primary key (ORG_ID)
);

/*==============================================================*/
/* Table: T_ORG_USER                                            */
/*==============================================================*/
create table T_ORG_USER 
(
   PK_ORG_USER_ID       NUMBER(10)           not null,
   ISDELETE             NUMBER(10)           not null,
   FK_ORG_ID            NUMBER(10)           not null,
   FK_USER_ID           NUMBER(10)           not null,
   constraint SYS_C0054000 primary key (PK_ORG_USER_ID)
);

/*==============================================================*/
/* Table: T_RESOURCE                                            */
/*==============================================================*/
create table T_RESOURCE 
(
   RESOURCE_ID          NUMBER(10)           not null,
   CODE                 VARCHAR2(100),
   CREATE_DATE          DATE                 not null,
   DIS_ORDER            NUMBER(10),
   ISDELETEABLE         NUMBER(10)           not null,
   REMARKS              VARCHAR2(200),
   RESOURCE_FROM        NUMBER(10),
   RESOURCE_NAME        VARCHAR2(100)        not null,
   RESOURCE_TYPE_UUID   VARCHAR2(255),
   STATUS               NUMBER(10)           not null,
   URLPATH              VARCHAR2(200),
   PARENT_ID            NUMBER(10),
   RESOURCE_TYPE        NUMBER(10),
   constraint SYS_C0054006 primary key (RESOURCE_ID)
);

/*==============================================================*/
/* Table: T_ROLE                                                */
/*==============================================================*/
create table T_ROLE 
(
   ROLE_ID              NUMBER(10)           not null,
   DESCRIPTION          VARCHAR2(200),
   ISDELETE             NUMBER(10)           not null,
   ISDELETEABLE         NUMBER(10)           not null,
   ROLE_CODE            VARCHAR2(50),
   ROLE_NAME            VARCHAR2(50)         not null,
   FK_ROLE_TYPE_UUID    VARCHAR2(255),
   FK_ROLE_TYPE         NUMBER(10),
   constraint SYS_C0054011 primary key (ROLE_ID)
);

/*==============================================================*/
/* Table: T_ROLE_MEMBER_SCOPE                                   */
/*==============================================================*/
create table T_ROLE_MEMBER_SCOPE 
(
   ID                   NUMBER(10)           not null,
   GROUP_ID             NUMBER(10),
   ROLE_ID              NUMBER(10)           not null,
   USER_ID              NUMBER(10),
   constraint SYS_C0054014 primary key (ID)
);

/*==============================================================*/
/* Table: T_ROLE_RESOURCE                                       */
/*==============================================================*/
create table T_ROLE_RESOURCE 
(
   ROLE_RESOURCE_ID     NUMBER(10)           not null,
   RESOURCE_ID          NUMBER(10)           not null,
   ROLE_ID              NUMBER(10)           not null,
   constraint SYS_C0054018 primary key (ROLE_RESOURCE_ID)
);

/*==============================================================*/
/* Table: T_ROLE_TEMP_TEAM_MAP                                  */
/*==============================================================*/
create table T_ROLE_TEMP_TEAM_MAP 
(
   ID                   NUMBER               not null,
   RULE_ID              NUMBER               not null,
   RECYCLE_NUMBER       NUMBER               not null,
   WORK_ROUND_ID        NUMBER               not null,
   TEMP_TEAM_ID         NUMBER               not null,
   constraint PK_RULE_ROUND_ID primary key (ID)
);

/*==============================================================*/
/* Table: T_RULE_WORK_TEAM_MAP                                  */
/*==============================================================*/
create table T_RULE_WORK_TEAM_MAP 
(
   ID                   NUMBER(10)           not null,
   RECYCLE_NUMBER       NUMBER(10)           not null,
   WORK_ROUND_ID        NUMBER(10)           not null,
   WORK_TEAM_ID         NUMBER(10)           not null,
   RULE_ID              NUMBER(10)           not null,
   constraint SYS_C0054024 primary key (ID)
);

/*==============================================================*/
/* Table: T_SCOPE_MEMBER                                        */
/*==============================================================*/
create table T_SCOPE_MEMBER 
(
   ID                   NUMBER(10)           not null,
   GROUP_ID             NUMBER(10),
   ORG_ID               NUMBER(10),
   ROLE_MEMBER_ID       NUMBER(10),
   USER_ID              NUMBER(10),
   constraint SYS_C0054026 primary key (ID)
);

/*==============================================================*/
/* Table: T_TEMP_TEAM                                           */
/*==============================================================*/
create table T_TEMP_TEAM 
(
   ID                   NUMBER               not null,
   NAME                 VARCHAR2(100),
   CREATE_DATE          DATE,
   REMARKS              VARCHAR2(1000),
   constraint PK_TEMP_TEAM_ID primary key (ID)
);

/*==============================================================*/
/* Table: T_TEMP_TEAM_MENBER                                    */
/*==============================================================*/
create table T_TEMP_TEAM_MENBER 
(
   ID                   NUMBER               not null,
   USER_ID              NUMBER               not null,
   TEMP_TEAM_ID         NUMBER,
   constraint PK_RULE_ROUND_USER primary key (ID)
);

/*==============================================================*/
/* Table: T_USER                                                */
/*==============================================================*/
create table T_USER 
(
   USER_ID              NUMBER(10)           not null,
   BIRTHDAY             VARCHAR2(50),
   BIRTH_PLACE          VARCHAR2(255),
   CARD_CODE            VARCHAR2(255),
   DIS_ORDER            NUMBER(10),
   ELECTRONIC_SIGNATURE VARCHAR2(255),
   EMAIL                VARCHAR2(255),
   ISENABLE             NUMBER(10),
   ERP_ID               VARCHAR2(255),
   GENDER               VARCHAR2(3),
   ID_CARD              VARCHAR2(255),
   ISDELETABLE          NUMBER(10),
   FK_JOB1_UUID         VARCHAR2(255),
   FK_JOB2_UUID         VARCHAR2(255),
   FK_JOBLEVEL_UUID     VARCHAR2(255),
   MOBILE_PHONE1        VARCHAR2(20),
   MOBILE_PHONE2        VARCHAR2(20),
   PASSWORD             VARCHAR2(50),
   PERSONAL_IMAGE       VARCHAR2(255),
   PHONE_NO             VARCHAR2(20),
   FK_POSTTITLE_UUID    VARCHAR2(255),
   FK_POST_UUID         VARCHAR2(255),
   REALNAME             VARCHAR2(50),
   SHORT_NO1            VARCHAR2(20),
   SHORT_NO2            VARCHAR2(20),
   STATUS               NUMBER(10),
   FK_TEAM_UUID         VARCHAR2(255),
   USER_ONLINE          NUMBER(19),
   FK_USERTYPE_UUID     VARCHAR2(255),
   USERNAME             VARCHAR2(50),
   FK_JOB1              NUMBER(10),
   FK_JOB2              NUMBER(10),
   FK_JOBLEVEL          NUMBER(10),
   FK_POST              NUMBER(10),
   FK_POSTTITLE         NUMBER(10),
   FK_TEAM              NUMBER(10),
   FK_TYPE              NUMBER(10),
   constraint SYS_C0054028 primary key (USER_ID)
);

/*==============================================================*/
/* Table: T_WORK_ROUND                                          */
/*==============================================================*/
create table T_WORK_ROUND 
(
   ID                   NUMBER(10)           not null,
   END_TIME             NUMBER(10),
   ROUND_NAME           VARCHAR2(7)          not null,
   START_TIME           NUMBER(10),
   constraint SYS_C0054031 primary key (ID)
);

/*==============================================================*/
/* Table: T_WORK_TEAM                                           */
/*==============================================================*/
create table T_WORK_TEAM 
(
   ID                   NUMBER(10)           not null,
   CREATE_DATE          DATE                 not null,
   REMARK               VARCHAR2(2000),
   TEAM_NAME            VARCHAR2(100)        not null,
   MONITOR_ID           NUMBER(10),
   ORG_ID               NUMBER(10)           not null,
   constraint SYS_C0054036 primary key (ID)
);

/*==============================================================*/
/* Table: T_WORK_TEAM_MEMBER                                    */
/*==============================================================*/
create table T_WORK_TEAM_MEMBER 
(
   ID                   NUMBER(10)           not null,
   USER_ID              NUMBER(10),
   WORK_TEAM_ID         NUMBER(10)           not null,
   constraint SYS_C0054039 primary key (ID)
);

/*==============================================================*/
/* Table: T_WORK_TURNS_PLAN                                     */
/*==============================================================*/
create table T_WORK_TURNS_PLAN 
(
   ID                   NUMBER(10)           not null,
   PLAN_DATE            DATE                 not null,
   WORK_ROUND_ID        NUMBER(10),
   WORK_TEAM_ID         NUMBER(10),
   WORK_TURNS_RULE_ID   NUMBER(10),
   constraint SYS_C0054042 primary key (ID)
);

/*==============================================================*/
/* Table: T_WORK_TURNS_RULE                                     */
/*==============================================================*/
create table T_WORK_TURNS_RULE 
(
   ID                   NUMBER(10)           not null,
   CYCLE_DAYS           NUMBER(10),
   MAKE_TIME            DATE                 not null,
   RULE_NAME            VARCHAR2(255),
   REMARKS              VARCHAR2(255),
   ORG_ID               NUMBER(10),
   MAKE_USER_ID         NUMBER(10),
   constraint SYS_C0054045 primary key (ID)
);

/*==============================================================*/
/* Table: T_WORK_TURNS_USER_PLAN                                */
/*==============================================================*/
create table T_WORK_TURNS_USER_PLAN 
(
   ID                   NUMBER               not null,
   WORK_TURNS_RULE_ID   NUMBER               not null,
   PLAN_DATE            DATE                 not null,
   WORK_ROUND_ID        NUMBER               not null,
   TEMP_TEAM_ID         NUMBER               not null,
   constraint PK_WORK_TURNS_USER_PLAN primary key (ID)
);
/*===================ע��======================*/
comment on column SYS_ACTIVITI_PROCESS_INST.BUSINESS_ORG is
'ҵ����ݵĹ�����֯ID';
comment on column SYS_ACTIVITI_PROCESS_INST.BUSINESS_ORG is
'ҵ����ݵĹ�����֯ID';
comment on table T_DICTIONARY is
'�ֵ��';

comment on column T_DICTIONARY.PK_ID is
'�ֵ������';

comment on column T_DICTIONARY.DICTIONARY_NAME is
'�ֵ����';

comment on column T_DICTIONARY.DICTIONARY_VALUE is
'�ֵ�ֵ';

comment on column T_DICTIONARY.LEVEL_ORDER is
'�ȼ��������';

comment on column T_DICTIONARY.STATUS is
'ɾ���־ 0δɾ�� 1 ��ɾ��';

comment on column T_DICTIONARY.FK_DICT_TYPE_UUID is
'�ֵ����������';

comment on column T_DICTIONARY.DICT_UUID is
'�ֵ���������';

comment on column T_DICTIONARY.DICT_CODE is
'�ֵ����';

comment on column T_DICTIONARY.PARENT_ID is
'�ֵ�����ID';
comment on table T_GROUP is
'Ⱥ��';

comment on column T_GROUP.GROUP_NAME is
'Ⱥ�����';

comment on column T_GROUP.CREATE_DATE is
'����ʱ��';

comment on column T_GROUP.REMARK is
'��ע';
comment on table T_GROUP_MEMBER is
'Ⱥ���Ա';

comment on column T_GROUP_MEMBER.USER_ID is
'�û�ID';

comment on column T_GROUP_MEMBER.ORG_ID is
'����ID';

comment on column T_GROUP_MEMBER.GROUP_ID is
'Ⱥ��ID';
comment on table T_LOG is
'��־��';

comment on column T_LOG.PK_LOG_ID is
'��־������';

comment on column T_LOG.USER_ID is
'������ID';

comment on column T_LOG.OP_DATE is
'����ʱ��';

comment on column T_LOG.OP_CONTENT is
'��־����';

comment on column T_LOG.IP_URL is
'����IP��ַ';

comment on column T_LOG.LOG_TYPE is
'��־���� 1ϵͳ��־��2���û���־ 3��ϵͳ������־';

comment on column T_LOG.FK_LOG_TYPE_UUID is
'��־�����ֵ����UUID';
comment on table T_ORGANIZATION is
'��֯��';

comment on column T_ORGANIZATION.ORG_CODE is
'Ϊ�˸��û�ʹ�÷��㣬����������֯���룬���磺�����?�Ĵ��룺SCGL';

comment on column T_ORGANIZATION.FK_ORGTYPE is
'�ֵ����';

comment on column T_ORGANIZATION.DIS_ORDER is
'ͬ������';

comment on column T_ORGANIZATION.ENABLE is
'0�����ã�1������';

comment on column T_ORGANIZATION.STATUS is
'0��δɾ��
1��ɾ��   2�ܹ�ɾ��';

comment on column T_ORGANIZATION.ORG_FROM is
'��֯��Դ  0���ܹ��������     1���̳�ƽ̨ͬ��';

comment on column T_ORGANIZATION.FK_ORGTYPE_UUID is
'��֯�����ֵ����UUID';

comment on column T_ORGANIZATION.ISDELETEABLE is
'1������ɾ��0����ɾ��';
comment on table T_ORG_USER is
'�û�-��֯��';

comment on column T_ORG_USER.PK_ORG_USER_ID is
'����';

comment on column T_ORG_USER.FK_ORG_ID is
'��֯ID';

comment on column T_ORG_USER.FK_USER_ID is
'�û�ID';

comment on column T_ORG_USER.ISDELETE is
'0��δɾ��  1����ɾ��';
comment on table T_RESOURCE is
'��Դ��';

comment on column T_RESOURCE.RESOURCE_ID is
'����';

comment on column T_RESOURCE.RESOURCE_NAME is
'��Դ���';

comment on column T_RESOURCE.CODE is
'��Դ����';

comment on column T_RESOURCE.RESOURCE_TYPE is
'�ֵ����';

comment on column T_RESOURCE.URLPATH is
'���ֶο��Դ洢�˵���ҳ���URL';

comment on column T_RESOURCE.REMARKS is
'��ע';

comment on column T_RESOURCE.PARENT_ID is
'���˵�ID';

comment on column T_RESOURCE.DIS_ORDER is
'��1��ʼ����';

comment on column T_RESOURCE.STATUS is
'0��δɾ��
1����ɾ�� 2��ͬ��ʱɾ��';

comment on column T_RESOURCE.CREATE_DATE is
'¼��ʱ��';

comment on column T_RESOURCE.RESOURCE_FROM is
'��Դ��Դ 0���ܹ�������� 1������ƽ̨ͬ��';

comment on column T_RESOURCE.RESOURCE_TYPE_UUID is
'��Դ�����ֵ����UUID';

comment on column T_RESOURCE.ISDELETEABLE is
'1������ɾ��0����ɾ��';
comment on table T_ROLE is
'��ɫ��';

comment on column T_ROLE.ROLE_ID is
'����';

comment on column T_ROLE.ROLE_NAME is
'��ɫ��';

comment on column T_ROLE.ROLE_CODE is
'��ɫ����';

comment on column T_ROLE.DESCRIPTION is
'����';

comment on column T_ROLE.ISDELETE is
'ɾ���־��0δɾ�� 1��ɾ��';

comment on column T_ROLE.FK_ROLE_TYPE is
'��ɫ���ࣺ0�����н�ɫ 1����Ȩ��ɫ';

comment on column T_ROLE.FK_ROLE_TYPE_UUID is
'��ɫ�����ֵ����UUID';

comment on column T_ROLE.ISDELETEABLE is
'1������ɾ��0����ɾ��';
comment on table T_ROLE_MEMBER_SCOPE is
'��ɫ����ɫ��Ա����Ч��Χ�Ĺ�����';

comment on column T_ROLE_MEMBER_SCOPE.ROLE_ID is
'��ɫID';

comment on column T_ROLE_MEMBER_SCOPE.USER_ID is
'�û�ID';

comment on column T_ROLE_MEMBER_SCOPE.GROUP_ID is
'Ⱥ���ID';
comment on table T_ROLE_RESOURCE is
'��ɫ��Դ��';

comment on column T_ROLE_RESOURCE.ROLE_RESOURCE_ID is
'����';

comment on column T_ROLE_RESOURCE.RESOURCE_ID is
'��ԴID';

comment on column T_ROLE_RESOURCE.ROLE_ID is
'��ɫID';
comment on column T_ROLE_TEMP_TEAM_MAP.RULE_ID is
'�������ID';

comment on column T_ROLE_TEMP_TEAM_MAP.RECYCLE_NUMBER is
'���ں�,��ʾһ�������ڵĵڼ��졣';

comment on column T_ROLE_TEMP_TEAM_MAP.WORK_ROUND_ID is
'���ID';

comment on column T_ROLE_TEMP_TEAM_MAP.TEMP_TEAM_ID is
'��ʱ��ID';
comment on table T_RULE_WORK_TEAM_MAP is
'���������ϸ��';

comment on column T_RULE_WORK_TEAM_MAP.RULE_ID is
'�������ID';

comment on column T_RULE_WORK_TEAM_MAP.WORK_TEAM_ID is
'����ID';

comment on column T_RULE_WORK_TEAM_MAP.RECYCLE_NUMBER is
'���ں�,��ʾһ�������ڵĵڼ��졣';

comment on column T_RULE_WORK_TEAM_MAP.WORK_ROUND_ID is
'���ID';
comment on table T_SCOPE_MEMBER is
'Ȩ�޷�Χ';

comment on column T_SCOPE_MEMBER.ROLE_MEMBER_ID is
'��ɫ��ԱID';

comment on column T_SCOPE_MEMBER.ORG_ID is
'��֯ID';
comment on table T_USER is
'�û���';

comment on column T_USER.USER_ID is
'����';

comment on column T_USER.USERNAME is
'��¼��';

comment on column T_USER.ERP_ID is
'ERP���';

comment on column T_USER.PASSWORD is
'����';

comment on column T_USER.MOBILE_PHONE1 is
'�ֻ����1';

comment on column T_USER.ELECTRONIC_SIGNATURE is
'����ǩ��';

comment on column T_USER.PERSONAL_IMAGE is
'����ͷ��';

comment on column T_USER.REALNAME is
'��ʵ����';

comment on column T_USER.GENDER is
'�Ա�';

comment on column T_USER.STATUS is
'0��δɾ��
1����ɾ��';

comment on column T_USER.DIS_ORDER is
'����';

comment on column T_USER.ISENABLE is
'0:δ���ã�1������';

comment on column T_USER.FK_TYPE is
'�û�����-�ֵ��';

comment on column T_USER.USER_ONLINE is
'�������ʱ��(�ú����ʾ)';

comment on column T_USER.FK_TEAM is
'����';

comment on column T_USER.SHORT_NO1 is
'���Ŷ̺�1';

comment on column T_USER.PHONE_NO is
'������';

comment on column T_USER.BIRTH_PLACE is
'�����';

comment on column T_USER.EMAIL is
'����';

comment on column T_USER.CARD_CODE is
'����';

comment on column T_USER.ID_CARD is
'���֤��';

comment on column T_USER.FK_POSTTITLE is
'ְ��-�ֵ��';

comment on column T_USER.FK_POST is
'ְλ-�ֵ��';

comment on column T_USER.FK_JOB1 is
'ְ��1-�ֵ��';

comment on column T_USER.FK_JOBLEVEL is
'ְ��-�ֵ��';

comment on column T_USER.BIRTHDAY is
'��������';

comment on column T_USER.ISDELETABLE is
'�Ƿ�����ɾ�� 0 ����  1 ������';

comment on column T_USER.MOBILE_PHONE2 is
'�ֻ����2';

comment on column T_USER.SHORT_NO2 is
'���Ŷ̺�2';

comment on column T_USER.FK_JOB2 is
'ְ��2-�ֵ��';

comment on column T_USER.FK_USERTYPE_UUID is
'�û������ֵ����UUID';

comment on column T_USER.FK_TEAM_UUID is
'�����ֵ����UUID';

comment on column T_USER.FK_POSTTITLE_UUID is
'ְ���ֵ����UUID';

comment on column T_USER.FK_POST_UUID is
'ְλ�ֵ����UUID';

comment on column T_USER.FK_JOB1_UUID is
'ְ��1�ֵ����UUID';

comment on column T_USER.FK_JOB2_UUID is
'ְ��2�ֵ����UUID';

comment on column T_USER.FK_JOBLEVEL_UUID is
'ְ���ֵ����UUID';

/*======================Լ��=====================*/

alter table SYS_ACTIVITI_DEFINE_TEMPLATE
   add constraint FKB9E57A9010AF362B foreign key (FK_CATEGORY_ID)
      references SYS_ACTIVITI_CATEGORY (PK_ACTIVITI_CATEGORY_ID)
      not deferrable;

alter table SYS_ACTIVITI_FORM_CATEGORY
   add constraint FK5E10EAABBAF7CEE9 foreign key (PK_ACTIVITI_FORM_ID)
      references SYS_ACTIVITI_FORM (PK_ACTIVITI_FORM_ID)
      not deferrable;

alter table SYS_ACTIVITI_FORM_CATEGORY
   add constraint FK5E10EAABE3591661 foreign key (PK_CATEGORY_ID)
      references SYS_ACTIVITI_CATEGORY (PK_ACTIVITI_CATEGORY_ID)
      not deferrable;

alter table SYS_ACTIVITI_PROCESS_INST
   add constraint FKA2CEB7644D6CC09F foreign key (FK_ACTIVITI_CATEGORY_ID)
      references SYS_ACTIVITI_CATEGORY (PK_ACTIVITI_CATEGORY_ID)
      not deferrable;

alter table T_ATTACH
   add constraint FK2B049890EE20AD3D foreign key (ATTACH_GROUP_ID)
      references T_ATTACH_GROUP (ATTACH_GROUP_ID)
      not deferrable;

alter table T_DICTIONARY
   add constraint FKFEB7F561FA82F069 foreign key (PARENT_ID)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_GROUP_MEMBER
   add constraint FKAF71F68558DC4FB2 foreign key (USER_ID)
      references T_USER (USER_ID)
      not deferrable;

alter table T_GROUP_MEMBER
   add constraint FKAF71F685EFF3B282 foreign key (GROUP_ID)
      references T_GROUP (ID)
      not deferrable;

alter table T_GROUP_MEMBER
   add constraint FKAF71F685F0C9319A foreign key (ORG_ID)
      references T_ORGANIZATION (ORG_ID)
      not deferrable;

alter table T_LOG
   add constraint FK4CC0CB958DC4FB2 foreign key (USER_ID)
      references T_USER (USER_ID)
      not deferrable;

alter table T_LOG
   add constraint FK4CC0CB9F7234EEE foreign key (LOG_TYPE)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_ORGANIZATION
   add constraint FK6FC9673E11C78A3D foreign key (FK_ORGTYPE)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_ORGANIZATION
   add constraint FK6FC9673EA84F3A14 foreign key (PARENT_ID)
      references T_ORGANIZATION (ORG_ID)
      not deferrable;

alter table T_ORG_USER
   add constraint FKAC44D3F1109B2A54 foreign key (FK_ORG_ID)
      references T_ORGANIZATION (ORG_ID)
      not deferrable;

alter table T_ORG_USER
   add constraint FKAC44D3F133496E38 foreign key (FK_USER_ID)
      references T_USER (USER_ID)
      not deferrable;

alter table T_RESOURCE
   add constraint FK47D00399B9801C64 foreign key (RESOURCE_TYPE)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_RESOURCE
   add constraint FK47D00399CC7298F9 foreign key (PARENT_ID)
      references T_RESOURCE (RESOURCE_ID)
      not deferrable;

alter table T_ROLE
   add constraint FK94B8458186C37602 foreign key (FK_ROLE_TYPE)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_ROLE_MEMBER_SCOPE
   add constraint FKAF0E2A4D58DC4FB2 foreign key (USER_ID)
      references T_USER (USER_ID)
      not deferrable;

alter table T_ROLE_MEMBER_SCOPE
   add constraint FKAF0E2A4DB7F777FD foreign key (ROLE_ID)
      references T_ROLE (ROLE_ID)
      not deferrable;

alter table T_ROLE_MEMBER_SCOPE
   add constraint FKAF0E2A4DEFF3B282 foreign key (GROUP_ID)
      references T_GROUP (ID)
      not deferrable;

alter table T_ROLE_RESOURCE
   add constraint FK3E7E410C9AA17315 foreign key (RESOURCE_ID)
      references T_RESOURCE (RESOURCE_ID)
      not deferrable;

alter table T_ROLE_RESOURCE
   add constraint FK3E7E410CB7F777FD foreign key (ROLE_ID)
      references T_ROLE (ROLE_ID)
      not deferrable;

alter table T_RULE_WORK_TEAM_MAP
   add constraint FK94FE07905CF0A745 foreign key (WORK_ROUND_ID)
      references T_WORK_ROUND (ID)
      not deferrable;

alter table T_RULE_WORK_TEAM_MAP
   add constraint FK94FE07906FE94D4D foreign key (RULE_ID)
      references T_WORK_TURNS_RULE (ID)
      not deferrable;

alter table T_RULE_WORK_TEAM_MAP
   add constraint FK94FE0790A4F47AEF foreign key (WORK_TEAM_ID)
      references T_WORK_TEAM (ID)
      not deferrable;

alter table T_SCOPE_MEMBER
   add constraint FKF65C251058DC4FB2 foreign key (USER_ID)
      references T_USER (USER_ID)
      not deferrable;

alter table T_SCOPE_MEMBER
   add constraint FKF65C251089298BF8 foreign key (ROLE_MEMBER_ID)
      references T_ROLE_MEMBER_SCOPE (ID)
      not deferrable;

alter table T_SCOPE_MEMBER
   add constraint FKF65C2510EFF3B282 foreign key (GROUP_ID)
      references T_GROUP (ID)
      not deferrable;

alter table T_SCOPE_MEMBER
   add constraint FKF65C2510F0C9319A foreign key (ORG_ID)
      references T_ORGANIZATION (ORG_ID)
      not deferrable;

alter table T_TEMP_TEAM_MENBER
   add constraint FK_TEMP_USER_TEMP_TEM foreign key (TEMP_TEAM_ID)
      references T_TEMP_TEAM (ID)
      on delete cascade
      not deferrable;

alter table T_USER
   add constraint FK94B9B0D64EDAFAE7 foreign key (FK_JOB1)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_USER
   add constraint FK94B9B0D64EDAFAE8 foreign key (FK_JOB2)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_USER
   add constraint FK94B9B0D64EDDB773 foreign key (FK_POST)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_USER
   add constraint FK94B9B0D64EDF6130 foreign key (FK_TEAM)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_USER
   add constraint FK94B9B0D64EDFAE0D foreign key (FK_TYPE)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_USER
   add constraint FK94B9B0D692BFBFBA foreign key (FK_JOBLEVEL)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_USER
   add constraint FK94B9B0D6EB41C257 foreign key (FK_POSTTITLE)
      references T_DICTIONARY (PK_ID)
      not deferrable;

alter table T_WORK_TEAM
   add constraint FK6B2698A0D005DFC3 foreign key (MONITOR_ID)
      references T_USER (USER_ID)
      not deferrable;

alter table T_WORK_TEAM
   add constraint FK6B2698A0F0C9319A foreign key (ORG_ID)
      references T_ORGANIZATION (ORG_ID)
      not deferrable;

alter table T_WORK_TEAM_MEMBER
   add constraint FKA63FEF1958DC4FB2 foreign key (USER_ID)
      references T_USER (USER_ID)
      not deferrable;

alter table T_WORK_TEAM_MEMBER
   add constraint FKA63FEF19A4F47AEF foreign key (WORK_TEAM_ID)
      references T_WORK_TEAM (ID)
      not deferrable;

alter table T_WORK_TURNS_PLAN
   add constraint FKABDD3E555CF0A745 foreign key (WORK_ROUND_ID)
      references T_WORK_ROUND (ID)
      not deferrable;

alter table T_WORK_TURNS_PLAN
   add constraint FKABDD3E55A09BF4F6 foreign key (WORK_TURNS_RULE_ID)
      references T_WORK_TURNS_RULE (ID)
      not deferrable;

alter table T_WORK_TURNS_PLAN
   add constraint FKABDD3E55A4F47AEF foreign key (WORK_TEAM_ID)
      references T_WORK_TEAM (ID)
      not deferrable;

alter table T_WORK_TURNS_RULE
   add constraint FKABDE4A2830692E61 foreign key (MAKE_USER_ID)
      references T_USER (USER_ID)
      not deferrable;

alter table T_WORK_TURNS_RULE
   add constraint FKABDE4A28F0C9319A foreign key (ORG_ID)
      references T_ORGANIZATION (ORG_ID)
      not deferrable;

alter table T_WORK_TURNS_USER_PLAN
   add constraint FK_TEMP_TEAM_ID foreign key (TEMP_TEAM_ID)
      references T_TEMP_TEAM (ID)
      on delete cascade
      not deferrable;

