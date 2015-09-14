package com.xx.system.common.vo;

/**
 * 封装排序VO
 * @version V1.0,2015年9月6日 下午2:45:39
 * @since V1.0
 * "[{"property":"description","direction":"ASC"}]"
 */
public class SortVo {
    
	/**
     * 升序常量
     */
    public static final String DIRECTION_ASC = "ASC";
    
    /**
     * 降序常量
     */
    public static final String DIRECTION_DESC = "DESC";
	
    /**
     * 属性
     */
    private String property;
    
    /**
     * 方向
     */
    private String direction=DIRECTION_ASC;

    /**
     * 默认构造方法
     */
    public SortVo() {
		super();
	}
    
    /**
     * 构造方法
     * @param property
     * @param direction
     */
    public SortVo(String property, String direction) {
		super();
		this.property = property;
		this.direction = direction;
	}
    
    /**
     * getter
     * @return property
     */
	public String getProperty() {
		return property;
	}

	/**
     * setter
     * @return
     */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
     * getter
     * @return direction
     */
	public String getDirection() {
		return direction;
	}

	/**
     * setter
     * @return
     */
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
