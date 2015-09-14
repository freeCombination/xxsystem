package com.xx.system.common.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 描述：Excel写操作帮助类
 * 
 * @author ALEX
 * @since 2010-11-24
 * @version 1.0v
 */
public class ExcelUtil {
    private static final Logger log = Logger.getLogger(ExcelUtil.class);
    
    /**
     * 功能：将HSSFWorkbook写入Excel文件
     * 
     * @param wb HSSFWorkbook
     * @param absPath 写入文件的相对路径
     * @param wbName 文件名
     */
    public static void writeWorkbook(HSSFWorkbook wb, String fileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            wb.write(fos);
        }
        catch (FileNotFoundException e) {
            log.error(new StringBuffer("[").append(e.getMessage())
                .append("]")
                .append(e.getCause()));
        }
        catch (IOException e) {
            log.error(new StringBuffer("[").append(e.getMessage())
                .append("]")
                .append(e.getCause()));
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException e) {
                log.error(new StringBuffer("[").append(e.getMessage())
                    .append("]")
                    .append(e.getCause()));
            }
        }
    }
    
    /**
     * 功能：创建HSSFSheet工作簿
     * 
     * @param wb HSSFWorkbook
     * @param sheetName String
     * @return HSSFSheet
     */
    public static HSSFSheet createSheet(HSSFWorkbook wb, String sheetName) {
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setDefaultColumnWidth(20);
        sheet.setGridsPrinted(true);
        sheet.setDisplayGridlines(true);
        return sheet;
    }
    
    /**
     * 功能：创建HSSFRow
     * 
     * @param sheet HSSFSheet
     * @param rowNum int
     * @param height float
     * @return HSSFRow
     */
    public static HSSFRow createRow(HSSFSheet sheet, int rowNum, float height) {
        HSSFRow row = sheet.createRow(rowNum);
        row.setHeightInPoints(height);
        return row;
    }
    
    /**
     * 功能：创建CellStyle样式
     * 
     * @param wb HSSFWorkbook
     * @param backgroundColor 背景色
     * @param foregroundColor 前置色
     * @param font 字体
     * @return CellStyle
     */
    public static CellStyle createCellStyle(HSSFWorkbook wb,
        short backgroundColor, short foregroundColor, short halign, Font font) {
        CellStyle cs = wb.createCellStyle();
        cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cs.setAlignment(halign);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs.setFillBackgroundColor(backgroundColor);
        cs.setFillForegroundColor(foregroundColor);
        cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs.setFont(font);
        return cs;
    }
    
    /**
     * 功能：创建带边框的CellStyle样式
     * 
     * @param wb HSSFWorkbook
     * @param backgroundColor 背景色
     * @param foregroundColor 前置色
     * @param font 字体
     * @return CellStyle
     */
    public static CellStyle createBorderCellStyle(HSSFWorkbook wb,
        short backgroundColor, short foregroundColor, short halign, Font font) {
        CellStyle cs = wb.createCellStyle();
        cs.setAlignment(halign);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs.setFillBackgroundColor(backgroundColor);
        cs.setFillForegroundColor(foregroundColor);
        cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs.setFont(font);
        cs.setBorderLeft(CellStyle.BORDER_DASHED);
        cs.setBorderRight(CellStyle.BORDER_DASHED);
        cs.setBorderTop(CellStyle.BORDER_DASHED);
        cs.setBorderBottom(CellStyle.BORDER_DASHED);
        return cs;
    }
    
    /**
     * 功能：创建CELL
     * 
     * @param row HSSFRow
     * @param cellNum int
     * @param style HSSFStyle
     * @return HSSFCell
     */
    public static HSSFCell createCell(HSSFRow row, int cellNum, CellStyle style) {
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellStyle(style);
        return cell;
    }
    
    /**
     * 功能：合并单元格
     * 
     * @param sheet HSSFSheet
     * @param firstRow int
     * @param lastRow int
     * @param firstColumn int
     * @param lastColumn int
     * @return int 合并区域号码
     */
    public static int mergeCell(HSSFSheet sheet, int firstRow, int lastRow,
        int firstColumn, int lastColumn) {
        return sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow,
            firstColumn, lastColumn));
    }
    
    /**
     * 功能：创建字体
     * 
     * @param wb HSSFWorkbook
     * @param boldweight short
     * @param color short
     * @return Font
     */
    public static Font createFont(HSSFWorkbook wb, short boldweight,
        short color, short size) {
        Font font = wb.createFont();
        font.setBoldweight(boldweight);
        font.setColor(color);
        font.setFontHeightInPoints(size);
        return font;
    }
    
    /**
     * 设置合并单元格的边框样式
     * 
     * @param sheet HSSFSheet
     * @param ca CellRangAddress
     * @param style CellStyle
     */
    public static void setRegionStyle(HSSFSheet sheet, CellRangeAddress ca,
        CellStyle style) {
        for (int i = ca.getFirstRow(); i <= ca.getLastRow(); i++) {
            HSSFRow row = HSSFCellUtil.getRow(i, sheet);
            for (int j = ca.getFirstColumn(); j <= ca.getLastColumn(); j++) {
                HSSFCell cell = HSSFCellUtil.getCell(row, j);
                cell.setCellStyle(style);
            }
        }
    }
    
    /**
     * 导出excel
     * 
     * @param wb excel对象
     * @param fileName 下载对话框上要显示的名字
     * @param response
     */
    public static void exportExcel(HSSFWorkbook wb, String fileName,
        HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        try {
            fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
        }
        catch (UnsupportedEncodingException e1) {
            log.error(" ExcelUtil文件中【导出excel】方法发生异常：" + e1);
        }
        response.addHeader("Content-Disposition", "attachment;filename="
            + fileName);
        
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            wb.write(out);
            out.flush();
        }
        catch (IOException e) {
            log.error(new StringBuffer("[").append(e.getMessage())
                .append("]")
                .append(e.getCause()));
        }
        finally {
            try {
                out.close();
            }
            catch (IOException e) {
                log.error(new StringBuffer("[").append(e.getMessage())
                    .append("]")
                    .append(e.getCause()));
            }
        }
    }
}