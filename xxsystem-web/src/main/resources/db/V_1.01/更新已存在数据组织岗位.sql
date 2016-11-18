update t_personal_grade t 
inner join t_user u on t.USER_ID = u.USER_ID
inner join t_responsibilities r on u.FK_RESP_ID = r.PK_RESP_ID
inner join t_org_user ou on ou.FK_USER_ID = u.USER_ID
inner join t_organization og on og.ORG_ID = ou.FK_ORG_ID
set t.ORG_CODE = og.ORG_CODE ,t.ORG_NAME = og.ORG_NAME,t.RESPONSIBILITY_CODE=r.NUMBER,t.RESPONSIBILITY_NAME=r.`NAME`,t.RESP_CHANGE_DATE=u.RESP_CHANGE_DATE;