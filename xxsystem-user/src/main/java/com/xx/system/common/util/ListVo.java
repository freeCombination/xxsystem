package com.xx.system.common.util;

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
    
    /** @Fields totalSize : 总条数 */
    private int totalSize;
    
    /** @Fields data : 数据集合 */
    private List<T> data;
    
    /**
     * <p>
     * Title: ListVo
     * </p>
     * <p>
     * Description: 默认构造方法
     * </p>
     */
    public ListVo() {
        this.totalSize = 0;
        data = new ArrayList<T>();
    }
    
    /**
     * <p>
     * Title: ListVo
     * </p>
     * <p>
     * Description: full构造方法
     * </p>
     * 
     * @param totalSize 总条数
     * @param data 数据集合
     */
    public ListVo(int totalSize, List<T> data) {
        this.totalSize = totalSize;
        this.data = data;
    }
    
    public List<T> getData() {
        return data;
    }
    
    public void setData(List<T> data) {
        this.data = data;
    }
    
    public int getTotalSize() {
        return totalSize;
    }
    
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
