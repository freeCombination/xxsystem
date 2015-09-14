package com.xx.system.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xx.system.common.constant.Constant;
import com.xx.system.common.dao.IBaseDao;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.exception.ServiceException;
import com.xx.system.common.vo.ListVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.dict.entity.IsExistVo;
import com.xx.system.dict.service.IDictService;
import com.xx.system.dict.vo.DictionaryVo;

/**
 * 数据字典业务处理实现类
 * 
 * @version V1.40,2014年8月25日 上午11:31:06
 * @see [相关类/方法]
 * @since V1.40
 */
@Service("dictService")
@Transactional(readOnly = false)
@SuppressWarnings({"unchecked", "rawtypes"})
public class DictServiceImpl implements IDictService {
    
    /**
     * 持久层接口
     */
    @Autowired
    private IBaseDao baseDao;
    
    /**
     * 新增数据字典
     * 
     * @Title addDicts
     * @author lizhengc
     * @date 2014年8月25日
     * @param dictionaryVo 字典VO对象
     * @return boolean
     */
    @Override
    public boolean addDicts(DictionaryVo dictionaryVo)
        throws BusinessException {
        boolean flag = false;
        try {
            // 判断是保存类型还是数据 0：类型 1：数据
            if ("0".equals(dictionaryVo.getFlag())) {
                Dictionary dictionary = new Dictionary();
                // 封装数据
                dictionary.setDictCode(dictionaryVo.getDictionaryCode());
                dictionary.setLevelOrder(dictionaryVo.getLevelOrder());
                dictionary.setStatus("0");
                dictionary.setDictionaryName(dictionaryVo.getDictionaryTypeName());
                dictionary.setDictionaryValue(dictionaryVo.getDictionaryValue());
                this.baseDao.save(dictionary);
                flag = true;
            }
            else if ("1".equals(dictionaryVo.getFlag())) {
                Dictionary dictionary = new Dictionary();
                // 封装数据
                dictionary.setDictionaryName(dictionaryVo.getDictionaryName());
                dictionary.setDictCode(dictionaryVo.getDictionaryCode());
                dictionary.setStatus("0");
                dictionary.setDictionaryValue(dictionaryVo.getDictionaryValue());
                dictionary.setLevelOrder(dictionaryVo.getLevelOrder());
                Dictionary parent = new Dictionary();
                parent.setPkDictionaryId(dictionaryVo.getDictionaryTypeId());
                dictionary.setDictionary(parent);
                this.baseDao.save(dictionary);
                flag = true;
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return flag;
    }
    
    /**
     * 数据字典修改
     * 
     * @Title updateDicts
     * @author lizhengc
     * @date 2014年8月25日
     * @param dictionaryVo 字典VO对象
     * @return boolean
     */
    @Override
    public boolean updateDicts(DictionaryVo dictionaryVo)
        throws BusinessException {
        boolean flag = false;
        try {
            Dictionary dict = getDictById(dictionaryVo.getPkDictionaryId());
            // 判断VO对象是否为空
            if (dictionaryVo == null
                || dictionaryVo.getPkDictionaryId() == null) {
                flag = false;
            }
            else {
                // 判断是修改类型还是字典信息 0：类型 ， 1：字典信息
                if ("0".equals(dictionaryVo.getFlag())) {
                    // 封装数据
                    dict.setDictionaryName(dictionaryVo.getDictionaryTypeName());
                    dict.setDictionaryValue(dictionaryVo.getDictionaryValue());
                    dict.setLevelOrder(dictionaryVo.getLevelOrder());
                    this.baseDao.update(dict);
                    flag = true;
                }
                else if ("1".equals(dictionaryVo.getFlag())) {
                    // 封装数据
                    Dictionary parent = new Dictionary();
                    parent.setPkDictionaryId(dictionaryVo.getDictionaryTypeId());
                    dict.setDictionary(parent);
                    dict.setDictionaryName(dictionaryVo.getDictionaryName());
                    dict.setDictionaryValue(dictionaryVo.getDictionaryValue());
                    dict.setLevelOrder(dictionaryVo.getLevelOrder());
                    this.baseDao.update(dict);
                    flag = true;
                }
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return flag;
    }
    
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
    @Override
    public String batchDelDicts(String ids, String tableName, String deleteField)
        throws BusinessException {
        String msg = "{success:'true',msg:'删除成功'}";
        try {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                // 根据id查询这条数据是否存在
                Dictionary d = getDictById(Integer.parseInt(id));
                // 判断该对象是否存在
                if (d == null) {
                    msg = "{'success':false,'msg':'该数据已删除，请刷新列表！'}";
                    break;
                }
                // 判断该条字典数据下是否有数据关联
                int b = getDictByTypeId(Integer.parseInt(id));
                if (b != 0) {
                    msg =
                        "{success:'false',msg:'请先删除字典类型【"
                            + d.getDictionaryName() + "】下的字典数据！'}";
                    break;
                }
                // 验证字典数据是否被引用 TODO
//                int size = validateDataExists(d.getDictUUID(), tableName);
//                if (size != 0) {
//                    msg =
//                        "{success:'false',msg:'字典数据【" + d.getDictionaryName()
//                            + "】已被使用，不允许删除！'}";
//                    break;
//                }
                this.baseDao.delete(id,
                    "Dictionary",
                    deleteField,
                    "pkDictionaryId",
                    Constant.STATUS_IS_DELETED + "");
            }
        }
        catch (Exception e) {
            msg = "{success:'false',msg:'删除失败'}";
            throw new BusinessException(e.getMessage());
        }
        return msg;
    }
    
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
        throws BusinessException {
        String hql =
            "from Dictionary d where d.status = '0' and d.dictionary.pkDictionaryId="
                + id;
        int count = baseDao.queryTotalCount(hql, new HashMap<String, Object>());
        return count;
    }
    
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
    @Override
    public Dictionary getDict(int pkDictionaryId, int states)
        throws BusinessException {
        String hql =
            "from Dictionary o where o.pkDictionaryId=" + pkDictionaryId
                + " and o.status=" + states;
        List<Dictionary> list = baseDao.queryEntitys(hql);
        // 判断字典对象是否为空
        if (list.size() != 0) {
            return (Dictionary)baseDao.queryEntitys(hql).get(0);
        }
        return null;
    }
    
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
    @Override
    public List<DictionaryVo> getDictTypes()
        throws BusinessException {
        String hql =
            "from Dictionary o where o.dictionary.pkDictionaryId is null and o.status='0' order by o.levelOrder asc";
        List<DictionaryVo> dictionaryVo = new ArrayList<DictionaryVo>();
        try {
            // 查询有效的字典所有类型
            List<Dictionary> poList = baseDao.queryEntitys(hql);
            DictionaryVo dVo = null;
            for (Dictionary dic : poList) {
                dVo = new DictionaryVo();
                // 封装数据 转换成字典vo对象
                if (dic.getDictionary() != null) {
                    dVo.setDictionaryTypeName(dic.getDictionary()
                        .getDictionaryName());
                }
                dVo.setDictionaryName(dic.getDictionaryName());
                dVo.setDictionaryValue(dic.getDictionaryValue());
                dVo.setDictionaryCode(dic.getDictCode());
                dVo.setLevelOrder(dic.getLevelOrder());
                dVo.setPkDictionaryId(dic.getPkDictionaryId());
                dictionaryVo.add(dVo);
            }
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return dictionaryVo;
    }
    
    /**
     * 根据条件分页
     * 
     * @Title getDictsByMap
     * @author lizhengc
     * @date 2014年8月25日
     * @param map 分页用的start和limit，查询用的字典名称（dictName）和字典类型名称（dictType）
     * @return 字典VO集合
     */
    @Override
    public ListVo<DictionaryVo> getDictsByMap(Map<String, String> map)
        throws BusinessException {
        ListVo<DictionaryVo> dicsListVo = new ListVo<DictionaryVo>();
        List<Dictionary> dicList = new LinkedList<Dictionary>();
        Integer totalRecords = 0;
        
        try {
            // map集合用来存储分页参数
            Map<String, Object> params = new HashMap<String, Object>();
            String start = map.get("start");
            String limit = map.get("limit");
            String dictionaryName = map.get("dictName");
            String dictType = map.get("dictType");
            String dictTypeCode = map.get("dictTypeCode");
            StringBuffer hql = new StringBuffer("");
            StringBuffer hqlCount = new StringBuffer("");
            hql.append("select o from Dictionary o  where 1=1 ");
            hqlCount.append("select count(*) from Dictionary o where 1=1 ");
            // 根据类型名模糊查询字典数据
            if (dictionaryName != null && !"".equals(dictionaryName)) {
                hql.append(" AND o.dictionaryName like '%" + dictionaryName
                    + "%' ");
                hqlCount.append(" AND o.dictionaryName like '%"
                    + dictionaryName + "%' ");
            }
            // 根据字典类型名模糊查询数据
            if (dictType != null && !"".equals(dictType)) {
                hql.append(" AND o.dictionary.dictionaryName = '"
                    + dictType + "' ");
                hqlCount.append(" AND o.dictionary.dictionaryName = '"
                    + dictType + "' ");
            }
            // 根据字典类型名模糊查询数据
            if (dictTypeCode != null && !"".equals(dictTypeCode)) {
            	hql.append(" AND o.dictionary.dictCode = '"
            			+ dictTypeCode + "' ");
            	hqlCount.append(" AND o.dictionary.dictCode = '"
            			+ dictTypeCode + "' ");
            }
            // 根据等级排序
            hql.append(" and o.status = 0 order by o.levelOrder asc");
            hqlCount.append(" and o.status = 0");
            dicList =
                ((List<Dictionary>)baseDao.queryEntitysByPage(Integer.parseInt(start),
                    Integer.parseInt(limit),
                    hql.toString(),
                    params));
            totalRecords = baseDao.queryTotalCount(hqlCount.toString(), params);
            
            DictionaryVo dVo = null;
            List<DictionaryVo> dictionaryVo = new ArrayList<DictionaryVo>();
            for (Dictionary dic : dicList) {
                dVo = new DictionaryVo();
                // 封装数据，将查询出的正常数据封装成vo
                if (dic.getDictionary() != null) {
                    dVo.setDictionaryTypeName(dic.getDictionary()
                        .getDictionaryName());
                    dVo.setDictionaryTypeId(dic.getDictionary()
                        .getPkDictionaryId());
                }
                dVo.setDictionaryName(dic.getDictionaryName());
                dVo.setDictionaryValue(dic.getDictionaryValue());
                dVo.setDictionaryCode(dic.getDictCode());
                dVo.setLevelOrder(dic.getLevelOrder());
                dVo.setPkDictionaryId(dic.getPkDictionaryId());
                dictionaryVo.add(dVo);
            }
            dicsListVo.setList(dictionaryVo);
            dicsListVo.setTotalSize(totalRecords);
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return dicsListVo;
    }
    
    /**
     * 根据主键获取字典数据实体，包括已经删除的实体
     * 
     * @Title getDictByIdIncludeAll
     * @author lizhengc
     * @date 2014年8月25日
     * @param id 字典ID
     * @return 字典对象
     */
    @Override
    public Dictionary getDictByIdIncludeAll(int id)
        throws BusinessException {
        try {
            return (Dictionary)baseDao.queryEntityById(Dictionary.class, id);
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
    
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
    @Override
    public int validateDataExists(String code, String tableName)
        throws BusinessException {
        try {
            // getRetrievalProcedures(code);
            int size = 0;
            String sql = "select t.table_name,t.table_field from T_ISEXIST t";
            // 数据是否存在其他表，是否被引用
            List<IsExistVo> list =
                this.baseDao.executeNativeSQLForBean(0,
                    100,
                    sql,
                    IsExistVo.class);
            if (list.size() != 0) {
                // 循环拿到查询出的表名进行查询看是否存在于表中
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).getTABLE_NAME().equals(tableName)) {
                        String sql1 = "";
                        if ("T_LOG".equals(list.get(i).getTABLE_NAME())) {
                            sql1 =
                                "select count(*) from "
                                    + list.get(i).getTABLE_NAME() + " where  "
                                    + list.get(i).getTABLE_FIELD() + "='"
                                    + code + "'";
                        }
                        else if ("T_LOG".equals(list.get(i).getTABLE_NAME())) {
                            sql1 =
                                "select count(*) from "
                                    + list.get(i).getTABLE_NAME()
                                    + " where status=0 and "
                                    + list.get(i).getTABLE_FIELD() + "='"
                                    + code + "'";
                        }
                        else {
                            sql1 =
                                "select count(*) from "
                                    + list.get(i).getTABLE_NAME()
                                    + " where status=0 and "
                                    + list.get(i).getTABLE_FIELD() + "='"
                                    + code + "'";
                        }
                        size = this.baseDao.queryCountSQL(sql1);
                        if (size != 0) {
                            size = 1;
                            break;
                        }
                    }
                }
            }
            return size;
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        
    }
    
    /**
     * 验证字典属性值的唯一性
     * 
     * @Title validateDictproperties
     * @author lizhengc
     * @date 2014年8月25日
     * @param paramsMap 接收的参数map
     * @return 返回验证结果，其中包含验证的结果和理由
     */
    @Override
    public Map<String, Object> validateDictTypeProperties(
        Map<String, String> paramsMap)
        throws BusinessException {
        
        String value = paramsMap.get("value");
        Map<String, Object> vaildator = new HashMap<String, Object>();
        // 根据页面传的字典名称判断是否唯一
        String hql =
            "from Dictionary d where d.status = 0 and d.dictionaryName = '"
                + value + "' ";
        try {
            int totleSize = baseDao.queryTotalCount(hql, new HashMap());
            if (totleSize > 0) {
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "数据已存在");
            }
            else {
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
            return vaildator;
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        
    }
    
    /**
     * 验证字典属性值的唯一性
     * 
     * @Title validateDictproperties
     * @author lizhengc
     * @date 2014年8月25日
     * @param paramsMap 接收的参数map
     * @return 返回验证结果，其中包含验证的结果和理由
     */
    @Override
    public Map<String, Object> validateDictproperties(
        Map<String, String> paramsMap)
        throws BusinessException {
        String key = paramsMap.get("key");
        String value = paramsMap.get("value");
        String typeId = paramsMap.get("typeId");
        Map<String, Object> vaildator = new HashMap<String, Object>();
        String hql = "";
        // 0: 验证字典名称 1: 验证字典值
        switch (NumberUtils.toInt(key)) {
            case 0:
                hql =
                    "from Dictionary d where d.status = 0 and d.dictionaryName = '"
                        + value + "' and d.dictionary.pkDictionaryId=" + typeId;
                break;
            case 1:
                hql =
                    "from Dictionary d where d.status = 0 and d.dictionaryValue = '"
                        + value + "'";
                break;
            default:
                break;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        
        try {
            // 根据查询出的数量判断数据是否存在
            int totleSize = baseDao.queryTotalCount(hql, map);
            if (totleSize > 0) {
                vaildator.put("success", true);
                vaildator.put("valid", false);
                vaildator.put("reason", "数据已存在");
            }
            else {
                vaildator.put("success", true);
                vaildator.put("valid", true);
                vaildator.put("reason", "");
            }
            return vaildator;
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
    
    /**
     * 验证字典code值的唯一性
     * 
     * @Title validateDictCodeproperties
     * @author lizhengc
     * @date 2014年8月25日
     * @param paramsMap 接收的参数map
     * @return 返回验证结果，其中包含验证的结果和理由
     */
    @Override
    public Map<String, Object> validateDictCodeproperties(
        Map<String, String> paramsMap)
        throws BusinessException {
        String value = paramsMap.get("value");
        
        Map<String, Object> vaildator = new HashMap<String, Object>();
        // 根据页面传来的字典编码验证字典code值的唯一性
        String hql =
            "from Dictionary d where d.status = 0 and d.dictCode" + " = '"
                + value + "'";
        int totleSize = 0;
        try {
            totleSize =
                baseDao.queryTotalCount(hql, new HashMap<String, Object>());
        }
        catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        // 根据查询出的数量判断数据是否存在
        if (totleSize > 0) {
            vaildator.put("success", true);
            vaildator.put("valid", false);
            vaildator.put("reason", "数据已存在");
        }
        else {
            vaildator.put("success", true);
            vaildator.put("valid", true);
            vaildator.put("reason", "");
        }
        return vaildator;
    }
    
    /**
     * 根据字典主键ID查询字典对象
     * 
     * @Title getDictById
     * @author lizhengc
     * @date 2014年8月25日
     * @param pkDictionaryId 字典主键
     * @return 字典实体
     * @throws ServiceException
     */
    @Override
    public Dictionary getDictById(int pkDictionaryId)
        throws BusinessException {
        String hql =
            "from Dictionary d where d.pkDictionaryId = " + pkDictionaryId
                + " and d.status = " + Constant.STATUS_NOT_DELETE;
        List<Dictionary> dicts = baseDao.queryEntitys(hql);
        if (dicts != null && dicts.size() > 0) {
            return dicts.get(0);
        }
        else {
            return null;
        }
    }
    
    /**
     * 通过字典类型Code获取字典数据
     * 
     * @Title getDictByTypeCode
     * @author lizhegnc
     * @date 2014年8月25日
     * @param dictType 字典类型
     * @return 字典集合
     * @throws ServiceException
     */
    @Override
    public List<Dictionary> getDictByTypeCode(String dictType)
        throws ServiceException {
        String hql =
            "from Dictionary d where d.status = 0 and d.dictionary.dictCode = '" + dictType
                + "' order by d.levelOrder, d.pkDictionaryId";
        return (List<Dictionary>)baseDao.queryEntitys(hql);
    }
    
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
    @Override
    public String getUUIDById(int pkDictionaryId) {
        Dictionary dict =
            (Dictionary)baseDao.queryEntityById(Dictionary.class,
                pkDictionaryId);
        if (dict == null) {
            return "";
        }
        return dict.getDictUUID();
    }
    
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
    @Override
    public Dictionary getDictByTypeAndValue(String typeCode,
        String dictionaryValue)
        throws ServiceException {
        StringBuffer hql = new StringBuffer();
        hql.append("from Dictionary d where d.status = "
            + Constant.STATUS_NOT_DELETE);
        
        if (typeCode != null && !"".equals(typeCode)) {
            // hql += " and d.dictionary.dictCode = '" + typeCode + "'";
            hql.append(" and d.dictionary.dictCode = '" + typeCode + "'");
        }
        if (dictionaryValue != null && !"".equals(dictionaryValue)) {
            // hql += " and d.dictionaryValue = '" + dictionaryValue + "'";
            hql.append(" and d.dictionaryValue = '" + dictionaryValue + "'");
        }
        List<Dictionary> dictList =
            (List<Dictionary>)baseDao.queryEntitys(hql.toString());
        if (dictList != null && dictList.size() > 0) {
            return dictList.get(0);
        }
        return null;
    }
    
}
