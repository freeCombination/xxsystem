package com.xx.system.user.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xx.system.org.entity.OrgUser;
import com.xx.system.user.entity.User;

/**
 * 工具类 工具类
 * 
 * @version V1.20,2013-11-25 下午1:53:12
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class HSSFUtils {
    /**
     * 从excel2003 抽取内容
     * 
     * @Title extractTextFromXLS
     * @Description:
     * @date 2013-11-25
     * @param is
     * @return
     * @throws IOException
     */
    public static String[][] extractTextFromXLS(InputStream is)
        throws IOException {
        StringBuffer content = new StringBuffer();
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        int colNum = 0;
        int allColNum = 0;
        int sheetsNum = workbook.getNumberOfSheets();
        for (int numSheets = 0; numSheets < sheetsNum; numSheets++) {
            if (null != workbook.getSheetAt(numSheets)) {
                HSSFSheet aSheet = workbook.getSheetAt(numSheets);
                for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    if (null != aSheet.getRow(rowNumOfSheet)) {
                        HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
                        colNum = aRow.getLastCellNum();
                        if (rowNumOfSheet == 0) {
                            allColNum = colNum;
                        }
                        for (int cellNumOfRow = 0; cellNumOfRow < aRow.getLastCellNum(); cellNumOfRow++) {
                            if (null == aRow.getCell(cellNumOfRow)) {
                                content.append("null");
                                content.append("\n");
                            }
                            if (null != aRow.getCell(cellNumOfRow)) {
                                HSSFCell aCell = aRow.getCell(cellNumOfRow);
                                if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                    if (HSSFDateUtil.isCellDateFormatted(aCell)) {
                                        DateFormat sdf =
                                            new SimpleDateFormat("yyyy-MM-dd");
                                        String dateStringvalue =
                                            sdf.format(aCell.getDateCellValue());
                                        content.append(dateStringvalue);
                                        content.append("\n");
                                    } else {
                                        content.append(aCell.getNumericCellValue());
                                        content.append("\n");
                                    }
                                } else if (aCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
                                    content.append(aCell.getBooleanCellValue());
                                    content.append("\n");
                                } else {
                                    String str = aCell.getStringCellValue();
                                    if (StringUtils.isEmpty(str)
                                        || StringUtils.isBlank(str)) {
                                        content.append("null");
                                    } else {
                                        content.append(str);
                                    }
                                    content.append("\n");
                                }
                            }
                        }
                        content.append("\t");
                    }
                }
            }
        }
        String str = content.toString();
        String[] row = str.split("\t");
        String[][] cols = new String[row.length][allColNum];
        for (int i = 1; i < row.length; i++) {
            String[] col = row[i].split("\n");
            for (int j = 0; j < col.length; j++) {
                cols[i][j] = col[j];
            }
        }
        
        for (int i = 1; i < row.length; i++) {
            for (int j = 0; j < colNum; j++) {
                
            }
        }
        
        return cols;
    }
    
    /**
     * 从 excel 2007文档中提取纯文本
     * 
     * @Title extractTextFromXLS2007
     * @author wanglc
     * @date 2013-11-25
     * @param fileName 文件名
     * @return
     * @throws Exception
     */
    public static String[][] extractTextFromXLS2007(String fileName)
        throws Exception {
        StringBuffer content = new StringBuffer();
        int colNum = 0;
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        XSSFWorkbook xwb = new XSSFWorkbook(fileName);
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
            XSSFSheet xSheet = xwb.getSheetAt(numSheet);
            if (xSheet == null) {
                continue;
            }
            
            // 循环行Row
            for (int rowNum = 0; rowNum <= xSheet.getLastRowNum(); rowNum++) {
                XSSFRow xRow = xSheet.getRow(rowNum);
                if (xRow == null) {
                    continue;
                }
                if (rowNum == 0)
                    colNum = xRow.getLastCellNum();
                // 循环列Cell
                for (int cellNum = 0; cellNum < xRow.getLastCellNum(); cellNum++) {
                    XSSFCell xCell = xRow.getCell(cellNum);
                    if (xCell == null) {
                        content.append("null");
                        content.append("\n");
                    }
                    if (null != xCell) {
                        if (xCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                            content.append(xCell.getBooleanCellValue());
                            content.append("\n");
                        } else {
                            if (xCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                content.append(xCell.getNumericCellValue());
                                content.append("\n");
                            } else {
                                content.append(xCell.getStringCellValue());
                                content.append("\n");
                            }
                        }
                    }
                }
                content.append("\t");
            }
        }
        
        String str = content.toString();
        String[] row = str.split("\t");
        String[][] cols = new String[row.length][colNum];
        for (int i = 1; i < row.length; i++) {
            String[] col = row[i].split("\n");
            for (int j = 0; j < col.length; j++) {
                cols[i][j] = col[j];
            }
        }
        return cols;
    }
    
    public static InputStream updateTempleUser(String path,String[] textlist){
        InputStream in = null;
        String fileToBeRead = path; // excel位置
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
                    fileToBeRead));
            HSSFSheet sheet = workbook.getSheet("Sheet1");
            HSSFRow row = sheet.getRow((short) 1);
            HSSFCell cell = row.getCell((short) 6);
            //cell.setCellValue("123");
            sheet = setHSSFValidation(sheet, textlist, 1, 1, 6, 6);// 第一列的前501行都设置为选择列表形式.  
//            FileOutputStream out = null;
//            try {
//                out = new FileOutputStream(fileToBeRead);
//                workbook.write(out);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            
            File file = new File("importUserTemp1.xls");
            try {
                OutputStream out = new FileOutputStream(file);
                workbook.write(out);
                out.close();
                in = new FileInputStream(file);
            } catch (Exception e) {
                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }
    
    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     * @param sheet 要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol   开始列
     * @param endCol  结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet,
            String[] textlist, int firstRow, int endRow, int firstCol,
            int endCol) {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(data_validation_list);
        return sheet;
    }
    
    
    /**
     * 导出用户到Excel
     * 
     * @Title getExcelInputStream
     * @author wanglc
     * @date 2013-11-25
     * @param list
     * @param orgUserList 组织用户列表
     * @return
     */
    public static InputStream getExcelInputStream(List<User> list,
        List<OrgUser> orgUserList) {
        Map<Integer, String> userId2orgNameMap = new HashMap<Integer, String>();
        for (int j = 0; j < list.size(); j++) {
            String orgMapName = "";
            for (int i = 0; i < orgUserList.size(); i++) {
                if (list.get(j).getUserId() == orgUserList.get(i)
                    .getUser()
                    .getUserId()) {
                    orgMapName +=
                        (orgUserList.get(i).getOrganization().getOrgName() + ",");
                }
            }
            if (!"".equals(orgMapName)) {
                userId2orgNameMap.put(list.get(j).getUserId(),
                    orgMapName.substring(0, orgMapName.length() - 1));
            }
        }
        
        InputStream in = null;
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("用户信息");
        sheet.setDefaultRowHeight((short)16);
        sheet.setDefaultColumnWidth(10);
        sheet.setDefaultColumnWidth(20);
        HSSFRow row = sheet.createRow(0);
        
        // 创建一个单元格样式对象
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(new HSSFColor.BLUE().getIndex());
        cellStyle.setFillBackgroundColor(new HSSFColor.BLUE().getIndex());
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderTop((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);
        
        // 创建一个头部样式对象
        HSSFCellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFillForegroundColor((short)13);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        
        // 创建一个头部字体对象
        HSSFFont headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short)15);
        headerFont.setFontName("微软雅黑");
        headerStyle.setFont(headerFont);
        
        // 创建一个单元格字体对象
        HSSFFont cellFont = wb.createFont();
        cellFont.setTypeOffset((short)0);
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellFont.setFontHeightInPoints((short)13);
        cellFont.setFontName("黑体");
        cellStyle.setFont(cellFont);
        
        HSSFCell cell = null;
        // 创建Excel头部标题
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellType(HSSFCellStyle.ALIGN_CENTER);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("组织部门");
        
        cell = row.createCell(1);
        cell.setCellStyle(headerStyle);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("职位");
        
        cell = row.createCell(2);
        cell.setCellStyle(headerStyle);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("职务");
        
        cell = row.createCell(3);
        cell.setCellStyle(headerStyle);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("职称");
        
        cell = row.createCell(4);
        cell.setCellStyle(headerStyle);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("职级");
        
        cell = row.createCell(5);
        cell.setCellStyle(headerStyle);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("姓名");
        
        cell = row.createCell(6);
        cell.setCellStyle(headerStyle);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("登录名");
        
        cell = row.createCell(7);
        cell.setCellStyle(cellStyle);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("员工编号");
        
        cell = row.createCell(8);
        cell.setCellStyle(headerStyle);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("性别");
        
        cell = row.createCell(9);
        cell.setCellStyle(headerStyle);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("手机");
        
        cell = row.createCell(10);
        cell.setCellStyle(headerStyle);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("用户类型");
        
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            row = sheet.createRow(i + 1);
            
            cell = row.createCell(0);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            cell.setCellValue(userId2orgNameMap.get(user.getUserId()));
            
            cell = row.createCell(1);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            //cell.setCellValue(user.getPost() == null ? "" : user.getPost()
            //    .getDictionaryName());
            
            cell = row.createCell(2);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            //cell.setCellValue(user.getJob1() == null ? "" : user.getJob1()
            //    .getDictionaryName());
            
            cell = row.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            //cell.setCellValue(user.getPostTitle() == null ? ""
            //    : user.getPostTitle().getDictionaryName());
            
            cell = row.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            //cell.setCellValue(user.getJobLevel() == null ? ""
            //    : user.getJobLevel().getDictionaryName());
            
            cell = row.createCell(5);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            cell.setCellValue(user.getRealname());
            
            cell = row.createCell(6);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            cell.setCellValue(user.getUsername());
            
            cell = row.createCell(7);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            cell.setCellValue(user.getErpId());
            
            cell = row.createCell(8);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            cell.setCellValue(user.getGender());
            
            cell = row.createCell(9);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            //cell.setCellValue(user.getMobileNo1());
            
            cell = row.createCell(10);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            //cell.setCellValue(user.getType() == null ? "" : user.getType()
            //    .getDictionaryName());
        }
        File file = new File("importUserTemp.xls");
        try {
            OutputStream out = new FileOutputStream(file);
            wb.write(out);
            out.close();
            in = new FileInputStream(file);
        } catch (Exception e) {
            
        }
        return in;
    }
    
    /**
     * 判断是excel2003还是2007.将其解析内容取出
     * 
     * @Title extractTextFromExcel
     * @author wanglc
     * @date 2013-11-25
     * @param attachUrl
     * @return
     * @throws Exception
     */
    public static String[][] extractTextFromExcel(String attachUrl)
        throws Exception {
        String[][] excelContent = null;
        try {
            InputStream in = new FileInputStream(new File(attachUrl));
            String type =
                attachUrl.substring(attachUrl.lastIndexOf(".") + 1)
                    .toLowerCase();
            if ("xls".equals(type)) {
                excelContent = HSSFUtils.extractTextFromXLS(in);
            } else if ("xlsx".equals(type)) {
                excelContent = HSSFUtils.extractTextFromXLS2007(attachUrl);
            }
        } catch (FileNotFoundException e) {
            
        } catch (Exception e) {
            
        }
        return excelContent;
    }
    
}
