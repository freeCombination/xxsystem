package com.dqgb.sshframe.workturns.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.exception.BusinessException;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.org.entity.OrgUser;
import com.dqgb.sshframe.user.vo.UserVo;
import com.dqgb.sshframe.workturns.entity.WorkTeam;
import com.dqgb.sshframe.workturns.entity.WorkTeamMember;
import com.dqgb.sshframe.workturns.vo.WorkTeamVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@SuppressWarnings("unchecked")
public class TestIWorkTeamService {
    @Autowired(required = true)
    @Qualifier("workTeamService")
    IWorkTeamService workTeamService;
    
    /**
     * 持久层接口
     */
    @Autowired
    private IBaseDao baseDao;
    
    /**
     * @Title addToWorkTeam
     * @author tangh
     * @Description: 添加班组
     * @date 2014年9月1日
     * @throws Exception
     */
    @Test
    public void addToWorkTeam()
        throws Exception {
        WorkTeamVo workTeamVo = new WorkTeamVo();
        workTeamVo.setWorkTeamName("Test班组名称");
        workTeamVo.setOrgId(1);
        workTeamVo.setRemark("描述");
        int count = workTeamService.addToWorkTeam(workTeamVo);
        String hql =
            "from WorkTeam o where o.teamName='Test班组名称' and o.org.orgId=1";
        
        List<WorkTeam> list = baseDao.queryEntitys(hql);
        int id = 0;
        if (list != null && list.size() > 0) {
            WorkTeam team = list.get(0);
            id = team.getId();
            Assert.assertEquals("Test班组名称", team.getTeamName());
            Assert.assertEquals("描述", team.getRemark());
            Assert.assertEquals(1, team.getOrg().getOrgId());
        }
        Assert.assertEquals(count, id);
    }
    
    /**
     * @Title validateWorkTeamName
     * @author tangh
     * @Description: 验证工作组名字
     * @date 2014年9月3日
     * @param teamName 班组名称
     * @param orgId 组织id
     * @param teamId 班组id
     * @return Integer 整形
     * @throws BusinessException
     */
    @Test
    public void validateWorkTeamName()
        throws Exception {
        String hql =
            "from WorkTeam o where o.teamName='Test班组名称' and o.org.orgId=1";
        
        List<WorkTeam> list = baseDao.queryEntitys(hql);
        WorkTeam teamWorkTeam = null;
        if(list.size()>0){
            teamWorkTeam = list.get(0);
        }
        
        Map<String,String> map = new HashMap<String, String>();
        map.put("value", teamWorkTeam.getTeamName());
        map.put("orgId", "1");
        
        try {
            int count = workTeamService.validateWorkTeamName(map);
            Assert.assertTrue(count>0);
        } catch (Exception e) {
            
        }
    }
    
    /**
     * @Title updateWorkTeam
     * @author tangh
     * @Description: 修改班组
     * @date 2014年9月1日
     * @throws Exception
     */
    @Test
    public void updateWorkTeam()
        throws Exception {
        String flag = "1";
        try {
            String hql =
                "from WorkTeam o where o.teamName='Test班组名称' and o.org.orgId=1";
            List<WorkTeam> list = baseDao.queryEntitys(hql);
            WorkTeam team = null;
            if (list != null && list.size() > 0) {
                team = list.get(0);
            }
            WorkTeamVo workTeamVo = new WorkTeamVo();
            workTeamVo.setRemark("描述修改");
            workTeamVo.setWorkTeamName("班组名称修改");
            workTeamVo.setOrgId(team.getOrg().getOrgId());
            workTeamVo.setWorkTeamId(team.getId());
            workTeamService.updateWorkTeam(workTeamVo, flag);
            
            List<WorkTeam> wtList = baseDao.queryEntitys(hql);
            WorkTeam workTeam = null;
            if (wtList != null && wtList.size() > 0) {
                workTeam = wtList.get(0);
            }
            Assert.assertEquals(team.getRemark(), workTeam.getRemark());
            Assert.assertEquals(team.getTeamName(), workTeam.getTeamName());
        } catch (Exception e) {
            // e.printStackTrace();
        }
        
    }
    
    /**
     * @Title getAllWorkTeam
     * @author tangh
     * @Description: 查询
     * @date 2014年9月1日
     * @throws Exception
     */
    @Test
    public void getAllWorkTeam()
        throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("start", "0");
        map.put("limit", "100");
        try {
            ListVo<WorkTeamVo> list = workTeamService.getAllWorkTeam(map);
            Assert.assertNotNull(list);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        
    }
    
    @Test
    public void getAllWorkTeam2()
        throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("start", "0");
        map.put("limit", "100");
        map.put("orgId", "1");
        try {
            ListVo<WorkTeamVo> list = workTeamService.getAllWorkTeam(map);
            Assert.assertNotNull(list);
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
    
    @Test
    public void getAllWorkTeam3()
        throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("start", "0");
        map.put("limit", "100");
        map.put("orgId", "1");
        map.put("workTeamName", "班组名称修改");
        
        try {
            ListVo<WorkTeamVo> list = workTeamService.getAllWorkTeam(map);
            Assert.assertNotNull(list);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        
    }
    
    /**
     * 获取组织成员
     * 
     * @author dong.he 2014-8-22
     * @param Map<String, String> paramMap
     * @throws BusinessException
     * @return ListVo<UserVo>
     */
    @Test
    public void getDeptMember()
        throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("start", "0");
        map.put("limit", "100");
        map.put("orgId", "1");
        map.put("userIds", "");
        try {
            ListVo<UserVo> list = workTeamService.getDeptMember(map);
            Assert.assertNotNull(list);
        } catch (Exception e) {
            
        }
    }
    
    /**
     * @author hedong 2014-7-16
     * @description 添加班组成员
     * @return String
     */
    @Test
    public void addWorkTeamMember()
        throws Exception {
        String orgId = "1";
        String hql =
            "from WorkTeam o where o.teamName='班组名称修改' and o.org.orgId="
                + NumberUtils.toInt(orgId);
        List<WorkTeam> list = baseDao.queryEntitys(hql);
        WorkTeam team = null;
        if (list != null && list.size() > 0) {
            team = list.get(0);
        }
        String teamId = team.getId().toString();
        
        StringBuffer orgUsrHql =
            new StringBuffer("from OrgUser ou where ou.user.status = 0 ");
        orgUsrHql.append(" and ou.organization.orgId ="
            + NumberUtils.toInt(orgId));
        List<OrgUser> ouLst =
            (List<OrgUser>)baseDao.queryEntitys(orgUsrHql.toString());
        OrgUser orgUser = null;
        if (ouLst != null && ouLst.size() > 0) {
            orgUser = ouLst.get(0);
        }
        int ids = orgUser.getUser().getUserId();
        Map<String, String> map = new HashMap<String, String>();
        map.put("teamId", teamId);
        map.put("orgId", orgId);
        map.put("ids", ids + "");
        
        try {
            workTeamService.addWorkTeamMember(map);
            
        } catch (Exception e) {
            
        }
        
    }
    
    /**
     * 获取班组成员
     * 
     * @author dong.he 2014-8-22
     * @param Map<String, String> paramMap
     * @throws BusinessException
     * @return ListVo<UserVo>
     */
    @Test
    public void getWorkTeamMember()
        throws Exception {
        String orgId = "1";
        String hql =
            "from WorkTeam o where o.teamName='班组名称修改' and o.org.orgId="
                + NumberUtils.toInt(orgId);
        List<WorkTeam> list = baseDao.queryEntitys(hql);
        WorkTeam team = null;
        if (list != null && list.size() > 0) {
            team = list.get(0);
        }
        
        String teamId = team.getId().toString();
        Map<String, String> map = new HashMap<String, String>();
        map.put("start", "0");
        map.put("limit", "100");
        map.put("teamId", teamId);
        try {
            ListVo<UserVo> userList = workTeamService.getWorkTeamMember(map);
            Assert.assertNotNull(userList);
        } catch (Exception e) {
            
        }
    }
    
    /**
     * 删除班组成员
     * 
     * @author dong.he 2014-8-22
     * @param Map<String, String> paramMap
     * @throws BusinessException
     * @return String
     */
    @Test
    public void delWorkTeamMember()
        throws Exception {
        String orgId = "1";
        String hql =
            "from WorkTeam o where o.teamName='班组名称修改' and o.org.orgId="
                + NumberUtils.toInt(orgId);
        List<WorkTeam> list = baseDao.queryEntitys(hql);
        WorkTeam team = null;
        if (list != null && list.size() > 0) {
            team = list.get(0);
        }
        
        String teamId = team.getId().toString();
        Map<String, String> map = new HashMap<String, String>();
        map.put("start", "0");
        map.put("limit", "100");
        map.put("teamId", teamId);
        // 查询所有数据
        ListVo<UserVo> userList1 = workTeamService.getWorkTeamMember(map);
        int id = userList1.getList().get(0).getUserId();
        
        // 根据用户id查询班组成员
        String userHql = "from WorkTeamMember o where o.members.userId=" + id;
        List<WorkTeamMember> wtmList = baseDao.queryEntitys(userHql);
        WorkTeamMember workTeamMember = null;
        if (wtmList.size() > 0) {
            workTeamMember = wtmList.get(0);
        }
        
        String delMemberIds = "";
        if (workTeamMember != null) {
            delMemberIds = workTeamMember.getId().toString();
        }
        
        Map<String, String> param = new HashMap<String, String>();
        param.put("teamId", teamId);
        param.put("delMemberIds", id + "");
        
        try {
            workTeamService.delWorkTeamMember(param);
            WorkTeamMember wtm =
                (WorkTeamMember)baseDao.queryEntityById(WorkTeamMember.class,
                    NumberUtils.toInt(delMemberIds));
            Assert.assertNull(wtm);
        } catch (Exception e) {
            
        }
    }
    
    
    /**
     * 根据班组ID获取班组信息
     * 
     * @author dong.he 2014-8-22
     * @param teamId
     * @throws BusinessException
     * @return WorkTeam
     */
    @Test
    public void getWorkTeamById()
        throws Exception {
        try {
            String orgId = "1";
            String hql =
                "from WorkTeam o where o.teamName='班组名称修改' and o.org.orgId="
                    + NumberUtils.toInt(orgId);
            List<WorkTeam> list = baseDao.queryEntitys(hql);
            WorkTeam team = null;
            if (list != null && list.size() > 0) {
                team = list.get(0);
            }
            
            int teamId = team.getId();
            WorkTeam workTeam = workTeamService.getWorkTeamById(teamId);
            Assert.assertNotNull(workTeam);
        } catch (Exception e) {
            
        }
    }
    
    /**
     * @Title delWorkTeam
     * @author tangh
     * @Description: 删除班组
     * @date 2014年9月1日
     * @throws Exception
     */
    @Test
    public void delWorkTeam()
        throws Exception {
        String hql =
            "from WorkTeam o where o.teamName='班组名称修改' and o.org.orgId=1";
        List<WorkTeam> list = baseDao.queryEntitys(hql);
        int id = 0;
        if (list != null && list.size() > 0) {
            WorkTeam team = list.get(0);
            id = team.getId();
        }
        try {
            workTeamService.delWorkTeam(id + "");
            List<WorkTeam> wtList = baseDao.queryEntitys(hql);
            Assert.assertEquals(null, wtList.get(0));
        } catch (Exception e) {
            // e.printStackTrace();
        }
        
    }
    
    /**
     * @Title delWorkTeamMemberAll
     * @author tangh
     * @Description: 删除班组下所有的成员
     * @date 2014年9月4日
     * @param teamId 班组id
     * @return 无
     * @throws BusinessException
     */
    @Test
    public void delWorkTeamMemberAll()
        throws Exception {
        String teamId = "333";
        try {
            workTeamService.delWorkTeamMemberAll(teamId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
