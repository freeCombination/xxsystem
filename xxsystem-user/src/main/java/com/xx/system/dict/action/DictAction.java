package com.xx.system.dict.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import com.xx.system.common.action.BaseAction;
import com.xx.system.common.exception.BusinessException;
import com.xx.system.common.util.JsonUtil;
import com.xx.system.common.util.RequestUtil;
import com.xx.system.common.vo.ListVo;
import com.xx.system.common.vo.TreeNodeVo;
import com.xx.system.dict.entity.Dictionary;
import com.xx.system.dict.vo.DictionaryVo;

/**
 * 字典Action
 * 
 * @version V1.40,2014年8月25日 上午11:49:31
 * @see [相关类/方法]
 * @since V1.40
 */
@SuppressWarnings("unused")
public class DictAction extends BaseAction {
    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = 3654110487812571254L;
    
    DictionaryVo dictionary;
    
    /**
     * @return the dictionary
     */
    public DictionaryVo getDictionary() {
        return dictionary;
    }
    
    /**
     * @param dictionary the dictionary to set
     */
    public void setDictionary(DictionaryVo dictionary) {
        this.dictionary = dictionary;
    }
    
    /**
     * 查询字典列表
     * 
     * @Title getDicts
     * @author lizhengc
     * @date 2014年8月25日
     */
    public void getDicts() {
        try {
            ListVo<DictionaryVo> voList = new ListVo<DictionaryVo>();
            // 获取页面参数
            Map<String, String> paramsMap =
                RequestUtil.getParameterMap(getRequest());
            // 执行查询
            voList = this.dictService.getDictsByMap(paramsMap);
            // 输出查询结果
            String str = JsonUtil.outJson(voList);
        }
        catch (Exception e) {
            // 发生异常是，进行日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "查询字典列表", e, false);
        }
    }
    
    /**
     * 查询所有字典类型
     * 
     * @Title getDictTypes
     * @author lizhengc
     * @date 2014年8月25日
     */
    public void getDictTypes() {
        try {
            // 获取所有字典类型，用以查询条件的下拉列表
            List<DictionaryVo> dictList = dictService.getDictTypes();
            JsonUtil.outJsonArray(dictList);
        }
        catch (Exception e) {
            // 发生异常是，进行日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "查询字典类型", e, false);
        }
    }
    
    /**
     * 新增数据字典
     * 
     * @Title addDictInfo
     * @author lizhengc
     * @date 2014年8月25日
     */
    public void addDictInfo() {
        try {
            boolean is = this.dictService.addDicts(dictionary);
            // 返回成功提示信息到页面
            JsonUtil.outJson("{success:'true',msg:'添加字典信息成功'}");
            // 操作日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "新增字典数据", null, true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:'false',msg:'添加字典信息失败'}");
            // 发生异常是，进行日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "新增字典数据", e, false);
        }
    }
    
    /**
     * 更新字典
     * 
     * @Title updateDictInfo
     * @author lizhengc
     * @date 2014年8月25日
     * @return
     */
    public String updateDictInfo() {
        try {
            boolean isExist = this.dictService.updateDicts(dictionary);
            // 返回成功提示信息到页面
            if (isExist) {
                JsonUtil.outJson("{success:'true',msg:'修改字典信息成功'}");
            }
            // 当所修改字典记录不存在时返回提示信息到页面
            else {
                JsonUtil.outJson("{success:'false',msg:'当前记录不存在，请刷新页面'}");
            }
            // 操作日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "修改字典数据", null, true);
        }
        catch (Exception e) {
            JsonUtil.outJson("{success:'false',msg:'修改字典信息失败'}");
            // 发生异常是，进行日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "修改字典数据", e, false);
        }
        return null;
    }
    
    /**
     * 删除字典
     * 
     * @Title delDictInfo
     * @author lizhengc
     * @date 2014年8月25日
     */
    public void delDictInfo() {
        String msg = "{success:'false',msg:'删除失败'}";
        try {
            String ids = getRequest().getParameter("ids");
            msg =
                this.dictService.batchDelDicts(ids, "T_DICTIONARY", "status");
            // 操作日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "删除数据字典", null, true);
        }
        catch (Exception e) {
            //JsonUtil.outJson(msg);
            // 发生异常是，进行日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "删除数据字典", e, false);
        }
        JsonUtil.outJson(msg);
    }
    
    /**
     * 检验字典属性值的唯一性
     * 
     * @Title validateDictproperties
     * @author lizhengc
     * @date 2014年8月25日
     * @return 字符串
     */
    public String validateDictproperties() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        Map<String, Object> vaildator = new HashMap<String, Object>();
        try {
            vaildator = dictService.validateDictproperties(paramsMap);
            JsonUtil.outJson(vaildator);
        }
        catch (BusinessException e) {
            // 发生异常是，进行日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "检验字典属性值的唯一性", e, false);
        }
        return null;
    }
    
    /**
     * 检验字典code值的唯一性
     * 
     * @Title validateDictproperties
     * @author lizhengc
     * @date 2014年8月25日
     * @return 字符串
     */
    public String validateDictCodeproperties() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        Map<String, Object> vaildator = new HashMap<String, Object>();
        try {
            vaildator = dictService.validateDictCodeproperties(paramsMap);
            JsonUtil.outJson(vaildator);
        }
        catch (BusinessException e) {
            // 发生异常是，进行日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "检验字典code值的唯一性", e, false);
        }
        return null;
    }
    
    /**
     * 检验字典类型名称的唯一性
     * 
     * @Title validateDictproperties
     * @author lizhengc
     * @date 2014年8月25日
     * @return 字符串
     */
    public String validateDictTypeProperties() {
        Map<String, String> paramsMap =
            RequestUtil.getParameterMap(getRequest());
        Map<String, Object> vaildator = new HashMap<String, Object>();
        try {
            vaildator = dictService.validateDictTypeProperties(paramsMap);
            JsonUtil.outJson(vaildator);
        }
        catch (BusinessException e) {
            // 发生异常是，进行日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "检验字典类型名称的唯一性", e, false);
        }
        return null;
    }
    
    /**
     * 通过id查询字典数据
     * 
     * @Title checkDictIsExist
     * @author lizhengc
     * @date 2014年8月25日
     * @return 字典对象
     */
    public Dictionary getById() {
        String id = getRequest().getParameter("id");
        Dictionary dict = new Dictionary();
        try {
            dict = dictService.getDictById(NumberUtils.toInt(id));
        }
        catch (BusinessException e) {
            // 发生异常是，进行日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "通过id查询字典数据", e, false);
        }
        return dict;
    }
    
    /**
     * 检查字典数据是否存在
     * 
     * @Title checkDictIsExist
     * @author lizhengc
     * @date 2014年8月25日
     * @return 字符串
     */
    public String checkDictIsExist() {
        String msg = "{'success':true,'msg':''}";
        String ids = getRequest().getParameter("ids");
        Dictionary dict = new Dictionary();
        try {
            if (!"".equals(ids) && !"0".equals(ids)) {
                String[] idArr = ids.split(",");
                for (String id : idArr) {
                    dict = dictService.getDictById(NumberUtils.toInt(id));
                    if (dict == null) {
                        dict =
                            dictService.getDictByIdIncludeAll(NumberUtils.toInt(id));
                        msg =
                            "{'success':false,'msg':'【"
                                + dict.getDictionaryName() + "】已删除，列表已刷新'}";
                        break;
                    }
                }
            }
            JsonUtil.outJson(msg);
        }
        catch (Exception e) {
            // 发生异常是，进行日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "检查字典数据是否存在", e, false);
        }
        return null;
    }
    
    /**
     * 通过字典类型Code获取字典数据
     * @Title getDictByTypeCode
     * @author dong.he
     * @date 2014年9月15日
     * @return
     */
    public String getDictByTypeCode() {
        String dictTypeCode = getRequest().getParameter("dictTypeCode");
        List<TreeNodeVo> result = new ArrayList<TreeNodeVo>();
        try {
            List<Dictionary> delResult =
                dictService.getDictByTypeCode(dictTypeCode);
            // entity向vo转换
            for (Dictionary dictionary : delResult) {
                TreeNodeVo tn = new TreeNodeVo();
                tn.setText(dictionary.getDictionaryName());
                tn.setDescription(dictionary.getDictionaryName());
                tn.setLeaf(true);
                tn.setNodeId(dictionary.getPkDictionaryId() + "");
                result.add(tn);
            }
        }
        catch (Exception e) {
            // 发生异常是，进行日志入库和生成日志文件
            this.excepAndLogHandle(DictAction.class, "通过字典类型获取字典数据", e, false);
        }
        JsonUtil.outJsonArray(result);
        return null;
    }
}
