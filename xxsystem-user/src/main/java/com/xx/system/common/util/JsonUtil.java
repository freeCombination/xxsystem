package com.xx.system.common.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.xx.system.common.vo.ResponseVo;

/**
 * json数据转化类
 * 
 * @version V1.20,2013-12-6 下午4:37:35
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class JsonUtil {
    
    private static final Logger log = Logger.getLogger(JsonUtil.class);
    
    
    /**
     * getResponse
     * 
     * @Title getResponse
     * @author wanglc
     * @Description:
     * @date 2013-12-6
     * @return
     */
    public static HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }
    
    /**
     * 将对象等转换为json格式并输出
     * 
     * @Title outJson
     * @author wanglc
     * @Description:
     * @date 2013-12-6
     * @param obj
     * @return
     */
    public static String outJson(Object obj) {
        try {
            String json = JSONObject.fromObject(obj).toString();
            getResponse().setContentType("text/html;charset=UTF-8");
            PrintWriter out = getResponse().getWriter();
            out.write(json);
            return json;
        }
        catch (Exception e) {
            log.error(" 转换JSON错误：" + e);
            return "";
        }
    }
    
    /**
     * 将对象等转换为json格式并输出
     * 
     * @Title outJson
     * @author hedaojun
     * @Description:
     * @date 2013-12-6
     * @param obj
     * @return
     */
    public static String outJson(ResponseVo rv) {
    	try {
    		String json = JSONObject.fromObject(rv.getJsonObject()).toString();
    		getResponse().setContentType("text/html;charset=UTF-8");
    		PrintWriter out = getResponse().getWriter();
    		out.write(json);
    		return json;
    	}
    	catch (Exception e) {
    		log.error("  转换JSON错误：" + e);
    		return "";
    	}
    }
    
    /**
     * 将list.数组格式数据转换为json
     * 
     * @Title outJsonArray
     * @author wanglc
     * @Description:
     * @date 2013-12-6
     * @param obj
     * @param excludes
     * @return
     */
    public static String outJsonArray(Object obj, String... excludes) {
        try {
            String json = "";
            JsonConfig jc = new JsonConfig();
            jc.setExcludes(excludes);
            json += JSONArray.fromObject(obj, jc).toString();
            getResponse().setContentType("text/html;charset=UTF-8");
            PrintWriter out = getResponse().getWriter();
            out.write(json);
            return json;
        }
        catch (Exception e) {
            
            return "";
        }
    }
    
}
