package com.xx.system.common.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装一个List及其总条目
 * 
 * @version V1.20,2013-12-6 下午3:06:23
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class ListVo<T> {
    
    /**
     * <p>
     * Title:ListVo
     * </p>
     * <p>
     * Description: 初始化构造方法
     * </p>
     */
    public ListVo() {
        this.totalSize = 0;
        list = new ArrayList<T>();
    }
    
    /** @Fields totalSize :记录总条数 */
    private int totalSize;
    
    /** @Fields list : 记录列表 */
    private List<T> list;
    
    public List<T> getList() {
        return list;
    }
    
    public void setList(List<T> list) {
        this.list = list;
    }
    
    public int getTotalSize() {
        return totalSize;
    }
    
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
    
}
