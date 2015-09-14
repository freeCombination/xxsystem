package com.xx.system.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * xml解析工具类
 * 
 * @version V1.20,2013-12-11 下午2:22:07
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class XMLUtil {
    
    /**
     * 得到指定路径下的xml的document
     * 
     * @Title getDocument
     * @author wanglc
     * @Description:
     * @date 2013-12-11
     * @param xmlPath
     * @return Document
     */
    public static Document getDocument(String xmlPath) {
        Document document = null;
        try {
            document =
                new SAXReader().read(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(xmlPath));
        }
        catch (Exception e) {
            
        }
        return document;
    }
    
    /**
     * 只将叶子节点中的元素，放入map中，数据无重复
     * 
     * @Title getXMLInfo
     * @author 张小虎
     * @Description:
     * @date 2013-12-11
     * @param element
     * @param ret
     * @return Map<String,Object>
     */
    public static Map<String, Object> getXMLInfo(Element element,
        Map<String, Object> ret) {
        if (element.elements().size() < 1) {
            ret.put(element.getQName().getName(), element.getText());
            return ret;
        }
        else {
            for (Iterator it = element.elementIterator(); it.hasNext();) {
                getXMLInfo((Element)it.next(), ret);
            }
            
        }
        return null;
        
    }
    
    /**
     * 将叶子节点中的元素，放入map中，并存入list
     * 
     * @Title getXMLInfo
     * @author 张小虎
     * @Description:
     * @date 2013-12-11
     * @param fileUrl
     * @param xpath
     * @return List<Map<String,Object>>
     * @throws Exception
     */
    public static List<Map<String, Object>> getXMLInfo(String fileUrl,
        String xpath)
        throws Exception {
        Document document =
            new SAXReader().read(new FileInputStream(new File(fileUrl)));
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        List<Element> dataItemList = (List<Element>)document.selectNodes(xpath);
        for (Element e : dataItemList) {
            Map tempMap = new HashMap();
            getXMLInfo(e, tempMap);
            ret.add(tempMap);
        }
        return ret;
        
    }
    
    /**
     * 获取xml中的元素
     * 
     * @Title getElementList
     * @author 张小虎
     * @Description:
     * @date 2013-12-11
     * @param doc
     * @param resultList 返回元素list
     * @param fileAbsoluteUrl 文件的绝对路径
     * @param elementName 查找的元素名称
     * @return List<Element>
     * @throws Exception
     */
    public static List<Element> getElementList(Document doc,
        List<Element> resultList, String fileAbsoluteUrl, String elementName)
        throws Exception {
        try {
            Document document = doc;
            if (doc == null) {
                InputStream in = new FileInputStream(new File(fileAbsoluteUrl));
                document = new SAXReader().read(in);
            }
            Element root = document.getRootElement();
            if (root.getQName().getName().trim().equals(elementName)) {
                resultList.add(root);
            }
            getElementList(resultList, root, elementName);
        }
        catch (FileNotFoundException e) {
            throw new Exception(fileAbsoluteUrl + ",文件未找到！");
        }
        return resultList;
    }
    
    /**
     * 递归查找xml中的元素
     * 
     * @Title getElementList
     * @author 张小虎
     * @Description:
     * @date 2013-12-11
     * @param resultList 返回元素list
     * @param element
     * @param elementName 查找的元素名称
     * @return
     */
    public static List<Element> getElementList(List<Element> resultList,
        Element element, String elementName) {
        
        if (element.elements().size() < 1) {
            return resultList;
        }
        else {
            for (Iterator it = element.elementIterator(); it.hasNext();) {
                Element e = (Element)it.next();
                if (e.getQName().getName().trim().equals(elementName)) {
                    resultList.add(e);
                }
                getElementList(resultList, e, elementName);
            }
            
        }
        return null;
        
    }
    
    /**
     * 修改系统维护中的数据
     * 
     * @Title writeXml
     * @author 张小虎
     * @Description:
     * @date 2013-12-11
     * @param document
     * @param targetFileUrl
     * @throws IOException
     */
    public static void writeXml(Document document, String targetFileUrl)
        throws IOException {
        File file = new File(targetFileUrl);
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
        writer.write(document);
        writer.close();
    }
    
    /**
     * 获取元素 document 不是null则按照document，否则按照inputStream,再次为reativeXmlPath
     * 
     * @Title getElement
     * @author 张小虎
     * @Description:
     * @date 2013-12-11
     * @param document
     * @param inputStream
     * @param reativeXmlPath
     * @param xPath
     * @return
     * @throws DocumentException
     */
    public static List<Element> getElement(Document document,
        InputStream inputStream, String reativeXmlPath, String xPath)
        throws DocumentException {
        List<Element> selects = new ArrayList();
        if (document != null) {
            selects = document.selectNodes(xPath);
        }
        else if (inputStream != null) {
            document = new SAXReader().read(inputStream);
            selects = document.selectNodes(xPath);
        }
        else if (StringUtils.isNotBlank(reativeXmlPath)) {
            document = getDocument(reativeXmlPath);
            selects = document.selectNodes(xPath);
        }
        return selects;
    }
    
}
