package com.xx.system.common.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class PagerVo<T> {
    
    // 一页显示的记录数
    private int pageSize = 15;
    
    // 当前页码
    private int pageNum = 1;
    
    // 记录总数
    private int rowCount = 0;
    
    // 总页数
    private int pageCount = 1;
    
    // 起始行数
    private int startIndex = 1;
    
    // 结束行数
    private int endIndex = 1;
    
    // 首页
    private int firstPage = 1;
    
    // 上一页
    private int prePage = 1;
    
    // 下一页
    private int nextPage = 1;
    
    // 尾页
    private int lastPage = 1;
    
    // 每页可以显示的信息条数的列表，页面中通过选择可以改变每页显示的条数。
    public final static int[] PAGE_SIZE_LIST = {15, 25, 50, 75, 99};
    
    // 结果集存放List
    private List<?> rows = null;
    
    // 每页可以显示的记录数
    private List<String> pageSizeList = null;
    
    // 存放页面数的List
    private List<String> pageIndexList = null;
    
    /**
     * 构造函数，根据每页显示的信息条数、当前页数、总信息条数来初始化用于分页的其他参数。
     * 
     * @param pageSize 每页显示的信息条数
     * @param pageNum 当前页数
     * @param rowCount 总信息条数
     * @param resultList 结果集
     */
    public PagerVo(int pageNum, int pageSize, int rowCount, List<T> resultList) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.rowCount = rowCount;
        this.rows = resultList;
        
        // 计算总页数
        if (rowCount % pageSize == 0) {
            this.pageCount = rowCount / pageSize;
        }
        else {
            this.pageCount = rowCount / pageSize + 1;
        }
        
        // 计算起始行数和结束行数
        this.startIndex = pageSize * (pageNum - 1);
        this.endIndex = this.startIndex + resultList.size();
        
        // 计算尾页、上一页和下一页
        this.lastPage = this.pageCount;
        if (this.pageNum > 1)
            this.prePage = this.pageNum - 1;
        if (this.pageNum == this.lastPage) {
            this.nextPage = this.lastPage;
        }
        else {
            this.nextPage = this.pageNum + 1;
        }
        
        // 构建每页可显示的记录数的List，在前台可以选择每页显示多少条记录
        pageSizeList = new ArrayList<String>(PAGE_SIZE_LIST.length);
        for (int i = 0; i < PAGE_SIZE_LIST.length; i++) {
            pageSizeList.add(String.valueOf(PAGE_SIZE_LIST[i]));
        }
        
        // 构建页面数的List，翻页时使用
        pageIndexList = new ArrayList<String>(pageCount);
        for (int i = 0; i < pageCount; i++) {
            pageIndexList.add(String.valueOf(i + 1));
        }
    }
    
    /**
     * 返回第一条信息的序号。
     * 
     * @return 第一条信息的序号
     */
    public int getStartIndex() {
        return startIndex;
    }
    
    /**
     * 设置第一条信息的序号。
     * 
     * @param startIndex 第一条信息的序号
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    
    /**
     * 返回最后一条信息的序号。
     * 
     * @return 最后一条信息的序号
     */
    public int getEndIndex() {
        return endIndex;
    }
    
    /**
     * 设置最后一条信息的序号。
     * 
     * @param endIndex 最后一条信息的序号
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
    
    /**
     * 返回首页的页码数。
     * 
     * @return 首页的页码数
     */
    public int getFirstPage() {
        return firstPage;
    }
    
    /**
     * 设置首页的页码数。
     * 
     * @param firstPage 首页的页码数
     */
    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }
    
    /**
     * 返回上一页的页码数。
     * 
     * @return 上一页的页码数
     */
    public int getPrePage() {
        return prePage;
    }
    
    /**
     * 设置上一页的页码数。
     * 
     * @param prePage 上一页的页码数
     */
    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }
    
    /**
     * 返回下一页的页码数。
     * 
     * @return 下一页的页码数
     */
    public int getNextPage() {
        return nextPage;
    }
    
    /**
     * 设置下一页的页码数。
     * 
     * @param nextPage 下一页的页码数
     */
    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
    
    /**
     * 返回尾页的页码数。
     * 
     * @return 尾页的页码数
     */
    public int getLastPage() {
        return lastPage;
    }
    
    /**
     * 设置尾页的页码数。
     * 
     * @param lastPage 尾页的页码数
     */
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
    
    /**
     * 返回总页数。
     * 
     * @return 总页数
     */
    public int getPageCount() {
        return pageCount;
    }
    
    /**
     * 设置总页数。
     * 
     * @param pageCount 总页数
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    
    /**
     * 返回当前页码数。
     * 
     * @return 当前页码数
     */
    public int getPageNum() {
        return pageNum;
    }
    
    /**
     * 设置当前页码数。
     * 
     * @param pageIndex 当前页码数
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    
    /**
     * 返回页码数的列表。
     * 
     * @return 页码数的列表
     */
    public List<String> getPageIndexList() {
        return pageIndexList;
    }
    
    /**
     * 设置页码数的列表。
     * 
     * @param pageIndexList 页码数的列表
     */
    public void setPageIndexList(List<String> pageIndexList) {
        this.pageIndexList = pageIndexList;
    }
    
    /**
     * 返回每页显示的信息数。
     * 
     * @return 每页显示的信息数。
     */
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * 设置每页显示的信息数。
     * 
     * @param pageSize 每页显示的信息数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * 返回每页显示的信息数的列表。
     * 
     * @return 每页显示的信息数的列表
     */
    public List<String> getPageSizeList() {
        return pageSizeList;
    }
    
    /**
     * 设置每页显示的信息数的列表。
     * 
     * @param pageSizeList 每页显示的信息数的列表
     */
    public void setPageSizeList(List<String> pageSizeList) {
        this.pageSizeList = pageSizeList;
    }
    
    /**
     * 返回结果集。
     * 
     * @return 结果集
     */
    @JSON(name = "resultList")
    public List<?> getRows() {
        return rows;
    }
    
    /**
     * 设置结果集。
     * 
     * @param resultList 结果集
     */
    public void setRows(List<?> rows) {
        this.rows = rows;
    }
    
    /**
     * 返回总信息条数。
     * 
     * @return 总信息条数
     */
    public int getRowCount() {
        return rowCount;
    }
    
    /**
     * 设置总信息条数。
     * 
     * @param rowCount 总信息条数
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
