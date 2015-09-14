package com.xx.system.common.vo;

import java.util.List;

public class IntervalVo {
    
    private List<Integer> users; // 在线用户
    
    private List<Integer> notReadedSugTaskIds; // 当前用户有未读意见的任务IDs.
    
    public IntervalVo() {
    }
    
    public List<Integer> getUsers() {
        return users;
    }
    
    public void setUsers(List<Integer> users) {
        this.users = users;
    }
    
    public List<Integer> getNotReadedSugTaskIds() {
        return notReadedSugTaskIds;
    }
    
    public void setNotReadedSugTaskIds(List<Integer> notReadedSugTaskIds) {
        this.notReadedSugTaskIds = notReadedSugTaskIds;
    }
}
