package com.dqgb.sshframe.workRound.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.dqgb.sshframe.common.dao.IBaseDao;
import com.dqgb.sshframe.common.vo.ListVo;
import com.dqgb.sshframe.workturns.entity.WorkRound;
import com.dqgb.sshframe.workturns.service.IWorkRoundService;
import com.dqgb.sshframe.workturns.vo.WorkRoundVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public class TestWorkRoundService {
    @Autowired(required = true)
    @Qualifier("workRoundService")
    private IWorkRoundService workRoundService;
    
    /**
     * 持久层接口
     */
    @Autowired
    private IBaseDao baseDao;
    
    /**
     * @Title addRound
     * @author lizhengc
     * @Description: 测试添加班次信息
     * @date 2014年8月25日
     * @param
     * @return
     */
    @Test
    public void addRound()
        throws Exception {
        try {
            WorkRound round = new WorkRound();
            round.setRoundName("早晚班");
            round.setStartTime(1);
            round.setEndTime(6);
            workRoundService.addWorkRound(round);
            
            String hql = "from WorkRound w where w.roundName = '早晚班'";
            WorkRound w = (WorkRound)baseDao.queryEntitys(hql);
            String name = w.getRoundName();
            Integer start = w.getStartTime();
            Integer end = w.getEndTime();
            Assert.assertSame(name, "早晚班");
            Assert.assertSame(start, 1);
            Assert.assertSame(end, 6);
            
        }
        catch (Exception e) {
        }
    }
    
    /**
     * @Title updateRoound
     * @author lizhengc
     * @Description: 测试修改班次信息
     * @date 2014年8月25日
     * @param
     * @return
     */
    @Test
    public void updateRound()
        throws Exception {
        try {
            String hql = "from WorkRound w where w.roundName = '早晚班'";
            WorkRound wr = (WorkRound)baseDao.queryEntitys(hql).get(0);
            if (wr != null) {
                
                WorkRound w = new WorkRound();
                w.setRoundName("早晚班11");
                w.setStartTime(8);
                w.setEndTime(16);
                w.setId(wr.getId());
                workRoundService.updateWorkRound(w);
                
                String haql1 = "from WorkRound w where w.roundName = '早晚班11'";
                WorkRound wRound =
                    (WorkRound)baseDao.queryEntitys(haql1).get(0);
                String name = wRound.getRoundName();
                Integer start = wRound.getStartTime();
                Integer end = wRound.getEndTime();
                
                Assert.assertEquals(name, "早晚班11");
                Assert.assertEquals(start, w.getStartTime());
                Assert.assertEquals(end, w.getEndTime());
            }
            else {
                Assert.assertNull(wr);
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Title deleteRound
     * @author lizhengc
     * @Description: 测试删除班次信息
     * @date 2014年8月25日
     * @param
     * @return
     */
    @Test
    public void deleteRound()
        throws Exception {
        try {
            String ids = "3,4";
            workRoundService.deleteWorkRound(ids);
            
            String hql = "from WorkRound w where id = 3";
            WorkRound wr = (WorkRound)baseDao.queryEntitys(hql);
            Assert.assertNull(wr);
        }
        catch (Exception e) {
        }
    }
    
    /**
     * @Title getWorkRoundByParams1
     * @author lizhengc
     * @Description: 根据条件查询班次 条件为空
     * @date 2014年8月25日
     * @param
     * @return
     */
    @Test
    public void getWorkRoundByParams1()
        throws Exception {
        try {
            HashMap<String, String> maps = new HashMap<String, String>();
            String name = "";
            String start = "0";
            String limit = "9";
            maps.put("name", name);
            maps.put("start", start);
            maps.put("limit", limit);
            ListVo<WorkRoundVo> vos =
                workRoundService.getWorkRoundByParams(maps);
            Assert.assertNotNull(vos);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Title getWorkRoundByParams2
     * @author lizhengc
     * @Description: 根据条件查询班次 条件不为空
     * @date 2014年8月25日
     * @param
     * @return
     */
    @Test
    public void getWorkRoundByParams2()
        throws Exception {
        try {
            HashMap<String, String> maps = new HashMap<String, String>();
            String name = "白班";
            String start = "0";
            String limit = "9";
            maps.put("name", name);
            maps.put("start", start);
            maps.put("limit", limit);
            ListVo<WorkRoundVo> vos =
                workRoundService.getWorkRoundByParams(maps);
            Assert.assertNotNull(vos);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Title getWorkRoundById
     * @author lizhengc
     * @Description: 根据id获取班次信息
     * @date 2014年8月25日
     * @param
     * @return
     */
    @Test
    public void getWorkRoundById()
        throws Exception {
        try {
            String id = "3";
            WorkRoundVo vo = workRoundService.getWorkRoundById(id);
            Assert.assertNotNull(vo);
        }
        catch (Exception e) {
        }
        
    }
    
    /**
     * @Title getAllWorkRound
     * @author lizhengc
     * @Description: 获取所有班次信息
     * @date 2014年8月25日
     * @param
     * @return
     */
    @Test
    public void getAllWorkRound()
        throws Exception {
        try {
            List<WorkRound> list = workRoundService.getAllWorkRound();
            Assert.assertNotNull(list);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Title isTimeCross
     * @author lizhengc
     * @Description: 判断班次的时间是否相同
     * @date 2014年8月25日
     * @param
     * @return
     */
    @Test
    public void isTimeCross()
        throws Exception {
        try {
            WorkRound wr = new WorkRound();
            wr.setRoundName("sf");
            wr.setStartTime(0);
            wr.setEndTime(8);
            workRoundService.isTimeCross(wr);
            String hql =
                "from WorkRound wr where wr.startTime = 0 and wr.endTime = 8";
            List<WorkRound> w = baseDao.queryEntitys(hql);
            WorkRound q = w.get(0);
            Assert.assertEquals(wr.getStartTime(), q.getStartTime());
            Assert.assertEquals(wr.getEndTime(), q.getEndTime());
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Title isTimeCross
     * @author lizhengc
     * @Description: 判断班次的时间是否交叉
     * @date 2014年8月25日
     * @param
     * @return
     */
    @Test
    public void isTimeCross1()
        throws Exception {
        try {
            // String hql = "from WorkRound wr where wr.roundName = '白班'";
            WorkRound wr = new WorkRound();
            wr.setRoundName("sf");
            wr.setStartTime(25);
            wr.setEndTime(1);
            Map<String, String> maps1 = new HashMap<String, String>();
            maps1 = workRoundService.isTimeCross(wr);
            String hql =
                "from WorkRound wr where wr.startTime = 0 and wr.endTime = 8";
            List<WorkRound> w = baseDao.queryEntitys(hql);
            WorkRound q = w.get(0);
            String mame = q.getRoundName();
            Map<String, String> maps = new HashMap<String, String>();
            maps.put("success", "false");
            maps.put("info", "班次【sf】和班次【" + mame + "】的时间有交叉!");
            
            Assert.assertEquals(maps1, maps);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Title validateRoundName
     * @author lizhengc
     * @Description: 验证是否存在班次名称
     * @date 2014年8月25日
     * @param
     * @return
     */
    @Test
    public void validateRoundName()
        throws Exception {
        try {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("value", "白班");
            Map<String, Object> vaildator =
                workRoundService.validateRoundName(paramsMap);
            vaildator.get("reason");
            
            String exist = (String)vaildator.get("reason");
            Assert.assertSame(exist, "数据已存在");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
