package com.xx.system.dict.service;

import java.util.List;
import java.util.Map;

import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.exception.ServiceException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.dict.vo.DictionaryVo;

/**
 * 数据字典业务处理
 * 
 * @version V1.40,2014年8月25日 上午11:31:06
 * @see [相关类/方法]
 * @since V1.40
 */
public interface IDictService {
    /**
     * 新增数据字典
     * 
     * @Title addDicts
     * @author lizhengc
     * @date 2014年8月25日
     * @param dictionaryVo 字典VO对象
     * @return boolean
     */
    public boolean addDicts(DictionaryVo dictionaryVo)
        throws BusinessException;
    
    /**
     * 数据字典修改
     * 
     * @Title updateDicts
     * @author lizhengc
     * @date 2014年8月25日
     * @param dictionaryVo 字典VO对象
     * @return boolean
     */
    public boolean updateDicts(DictionaryVo dictionaryVo)
        throws BusinessException;
    
    /**
     * 批量删除字典信息
     * 
     * @Title batchDelDicts
     * @author lizhengc
     * @date 2014年8月25日
     * @param ids 主键
     * @param tableName 表名
     * @param deleteField 字段名
     * @return 字符串
     */
    public String batchDelDicts(String ids, String tableName, String deleteField)
        throws BusinessException;
    
    /**
     * 根据字典主键ID查询字典对象
     * 
     * @Title getDictById
     * @author lizhengc
     * @date 2014年8月25日
     * @param pkDictionaryId 字典主键
     * @return Dictionary 字典实体对象
     * @throws ServiceException
     */
    public Dictionary getDictById(int pkDictionaryId)
        throws BusinessException;
    
    /**
     * 根据字典主键ID查询字典对象
     * 
     * @Title getDict
     * @author lizhengc
     * @date 2014年8月25日
     * @param pkDictionaryId 字典主键
     * @param states 字典状态
     * @return Dictionary 字典实体对象
     * @throws ServiceException
     */
    public Dictionary getDict(int pkDictionaryId, int states)
        throws BusinessException;
    
    /**
     * 根据字典主键ID查询字典类型下是否有数据
     * 
     * @Title getDictByTypeId
     * @author lizhengc
     * @date 2014年8月29日
     * @param id 字典主键
     * @return 整数
     * @throws BusinessException
     */
    public int getDictByTypeId(int id)
        throws BusinessException;
    
    /**
     * 查询所有字典类型
     * 
     * @Title getDictTypes
     * @author lizhengc
     * @date 2014年8月25日
     * @param 
     * @return 字典集合
     * @throws ServiceException
     */
    public List<DictionaryVo> getDictTypes()
        throws BusinessException;
    
    /**
     * 根据条件分页
     * 
     * @Title getDictsByMap
     * @author lizhengc
     * @date 2014年8月25日
     * @param map 分页用的start和limit，查询用的字典名称（dictName）和字典类型名称（dictType）
     * @return 字典VO集合
     */
    public ListVo<DictionaryVo> getDictsByMap(Map<String, String> map)
        throws BusinessException;
    
    /**
     * 验证数据是否处于使用状态
     * 
     * @Title validateDataExists
     * @author lizhengc
     * @date 2014年8月25日
     * @param code 字典编码
     * @param tableName 表名
     * @return 整数
     */
    public int validateDataExists(String code, String tableName)
        throws BusinessException;
    
    /**
     * 根据主键获取字典数据实体，包括已经删除的实体
     * 
     * @Title getDictByIdIncludeAll
     * @author lizhengc
     * @date 2014年8月25日
     * @param id 字典ID
     * @return 字典对象
     */
    public Dictionary getDictByIdIncludeAll(int id)
        throws BusinessException;
    
    /**
     * 验证字典属性值的唯一性
     * 
     * @Title validateDictproperties
     * @author lizhengc
     * @date 2014年8月25日
     * @param paramsMap 接收的参数map
     * @return 返回验证结果，其中包含验证的结果和理由
     */
    public Map<String, Object> validateDictproperties(
        Map<String, String> paramsMap)
        throws BusinessException;
    
    /**
     * 验证字典code值的唯一性
     * 
     * @Title validateDictCodeproperties
     * @author lizhengc
     * @date 2014年8月25日
     * @param paramsMap 接收的参数map
     * @return 返回验证结果，其中包含验证的结果和理由
     */
    public Map<String, Object> validateDictCodeproperties(
        Map<String, String> paramsMap)
        throws BusinessException;
    
    /**
     * 验证字典类型名称值的唯一性
     * 
     * @Title validateDictTypeProperties
     * @author lizhengc
     * @date 2014年8月25日
     * @param paramsMap 接收的参数map
     * @return 返回验证结果，其中包含验证的结果和理由
     */
    public Map<String, Object> validateDictTypeProperties(
        Map<String, String> paramsMap)
        throws BusinessException;
    
    /**
     * 根据字典编码获取字典类型
     * 
     * @Title getDictByTypeCode
     * @author dong.he
     * @date 2014年9月10日
     * @param dictType 字典类型
     * @return 字典集合
     * @throws ServiceException
     */
    public List<Dictionary> getDictByTypeCode(String dictType)
        throws ServiceException;
    
    /**
     * 根据字段ID获取字典uuid
     * 
     * @Title getUUIDById
     * @author dong.he
     * @date 2014年9月10日
     * @param pkDictionaryId 字典主键
     * @return 字符串
     * @throws ServiceException
     */
    public String getUUIDById(int pkDictionaryId)
        throws ServiceException;
    
    /**
     * 根据字典类型CODE和字典值获取字典数据
     * 
     * @Title getDictByTypeAndValue
     * @author dong.he
     * @date 2014年9月10日
     * @param typeCode 类型CODE
     * @param dictionaryValue 字典值
     * @return 字典对象
     * @throws ServiceException
     */
    public Dictionary getDictByTypeAndValue(String typeCode,
        String dictionaryValue)
        throws ServiceException;
}
