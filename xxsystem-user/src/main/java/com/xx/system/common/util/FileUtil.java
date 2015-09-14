package com.xx.system.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * 文件操作工具类
 * 
 * @version V1.20,2013-12-6 下午4:44:23
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class FileUtil {
    
    /** @Fields fos : 文件输出流 */
    public static FileOutputStream fos = null;
    
    /** @Fields fis : 文件输入流 */
    public static FileInputStream fis = null;
    
    /**
     * 图片或附件上传 上传到upload文件夹下
     * 
     * @Title upload
     * @author wanglc
     * @date 2013-12-6
     * @param file 上传的文件
     * @param newName 上传文件的原始名字
     * @param folder 上传文件夹 在SystemConstant中定义
     * @return String 返回上传文件的URL
     * @throws Exception
     */
    public static String upload(File file, String fileName, String folder)
        throws Exception {
        
        // 为用户新上传的文件新取一个名字
        String newName = UUID.randomUUID().toString();
        
        // 获取用户上传的文件格式
        String exp = getFileExp(fileName);
        
        File pageTemp = new File(folder);
        if (!pageTemp.exists() || !pageTemp.isDirectory()){
            pageTemp.mkdirs();
        }
        
        String fileURL = folder  + File.separator + newName +"."+ exp;
        // 将文件写入文件夹
        FileOutputStream fos = new FileOutputStream(fileURL);
        FileInputStream fis = new FileInputStream(file);
        writeFile(fos, fis);
        return fileURL;
    }
    
    /**
     * 图片或附件上传 上传到相对路径fileURL下
     * 
     * @author 吴波
     * @date 2011-5-4
     * @param file 上传的文件
     * @param fileURL 相对路径
     * @throws Exception
     * @return void
     */
    public static void upload(File file, String fileURL)
        throws Exception {
        fos = new FileOutputStream(fileURL);
        fis = new FileInputStream(file);
        writeFile(fos, fis);
    }
    
    /**
     * 得到文件名的后缀
     * 
     * @author 焦运磊
     * @date 2011-4-6
     * @param fileName
     * @return String
     */
    public static String getFileExp(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos+1).toLowerCase();
    }
    
    /**
     * 将字符串写入文件中
     * 
     * @author 焦运磊
     * @date 2011-4-6
     * @param fileURL
     * @param content
     * @throws Exception
     * @return void
     */
    public static void writeFile(String fileURL, String content)
        throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileURL));
        BufferedReader br = new BufferedReader(new StringReader(content));
        writeFile(bw, br);
    }
    
    /**
     * 写字节流文件
     * 
     * @author 吴波
     * @date 2011-4-6
     * @param fos
     * @param fis
     * @throws Exception
     * @return void
     */
    public static void writeFile(OutputStream fos, InputStream fis)
        throws Exception {
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
            fos.flush();
        }
        fis.close();
        fos.close();
    }
    
    /**
     * 写字符流文件
     * 
     * @author 吴波
     * @date 2011-4-6
     * @param bw
     * @param br
     * @throws Exception
     * @return void
     */
    private static void writeFile(BufferedWriter bw, BufferedReader br)
        throws Exception {
        String str = null;
        while ((str = br.readLine()) != null) {
            bw.write(str + "\n");
            bw.flush();
        }
        br.close();
        bw.close();
    }
    
    /**
     * 得到字符流文件里面的内容
     * 
     * @author 吴波
     * @date 2011-5-11
     * @param br
     * @throws Exception
     * @return String
     */
    public static String getFileContent(BufferedReader br)
        throws Exception {
        StringBuffer sb = new StringBuffer();
        // String retStr = "";
        
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
            sb.append("\n");
            // retStr += str;
            // retStr += "\n";
        }
        br.close();
        
        return sb.toString();
        // return retStr;
    }
    
    /**
     * 删除文件
     * 
     * @author 吴波
     * @date 2011-4-6
     * @param fileURL
     * @return boolean
     */
    public static boolean delFile(String fileURL) {
        if (StringUtils.isBlank(fileURL)) {
            return true;
        }
        
        File file = new java.io.File(fileURL);
        if (!file.exists()) {
            return true;
        }
        return file.delete();
    }
    
}
