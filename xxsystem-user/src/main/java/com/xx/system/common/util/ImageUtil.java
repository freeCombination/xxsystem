package com.xx.system.common.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;

/**
 * 图像工具类
 * 
 * @version V1.20,2013-12-6 下午4:49:53
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class ImageUtil {
    
    /**
     * 图片路径 图片类型
     * 
     * @param URLName
     * @param type
     * @return
     */
    public static String getImgeHexString(String URLName, String type) {
        String res = null;
        try {
            
            File file = new File(URLName);
            FileInputStream bfile = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(bfile);
            
            BufferedImage bm = ImageIO.read(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bm, type, bos);
            bos.flush();
            byte[] data = bos.toByteArray();
            
            res = byte2hex(data);
            bos.close();
        }
        catch (Exception e) {
            
        }
        return res;
    }
    
    /**
     * @title 根据二进制字符串生成图片
     * @param data 生成图片的二进制字符串
     * @param fileName 图片名称(完整路径)
     * @param type 图片类型
     * @return
     */
    public static void saveImage(String data, String fileName, String type) {
        
        BufferedImage image =
            new BufferedImage(100, 50, BufferedImage.TYPE_BYTE_BINARY);
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, byteOutputStream);
            // byte[] date = byteOutputStream.toByteArray();
            byte[] bytes = hex2byte(data);
            RandomAccessFile file = new RandomAccessFile(fileName, "rw");
            file.write(bytes);
            file.close();
        }
        catch (IOException e) {
            
        }
    }
    
    /**
     * 反格式化byte
     * 
     * @param s
     * @return
     */
    public static byte[] hex2byte(String s) {
        byte[] src = s.toLowerCase().getBytes();
        byte[] ret = new byte[src.length / 2];
        for (int i = 0; i < src.length; i += 2) {
            byte hi = src[i];
            byte low = src[i + 1];
            hi =
                (byte)((hi >= 'a' && hi <= 'f') ? 0x0a + (hi - 'a') : hi - '0');
            low =
                (byte)((low >= 'a' && low <= 'f') ? 0x0a + (low - 'a')
                    : low - '0');
            ret[i / 2] = (byte)(hi << 4 | low);
        }
        return ret;
    }
    
    /**
     * 格式化byte
     * 
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        char[] Digit =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                'D', 'E', 'F'};
        char[] out = new char[b.length * 2];
        for (int i = 0; i < b.length; i++) {
            byte c = b[i];
            out[i * 2] = Digit[(c >>> 4) & 0X0F];
            out[i * 2 + 1] = Digit[c & 0X0F];
        }
        
        return new String(out);
    }
    
}
