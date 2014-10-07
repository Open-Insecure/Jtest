//package com.br.dong.poi_Excel;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
///**
// * Created with IntelliJ IDEA.
// * User: Dong
// * Date: 14-9-1
// * Time: 上午10:16
// * To change this template use File | Settings | File Templates.
// */
//public class ExcelOperClass {
//    private static String EXCEL_2003 = ".xls";
//    private static String EXCEL_2007 = ".xlsx";
//
//    public static void readExcelJXL() {
//
//    }
//
//    /**
//     * 通过POI方式读取Excel
//     *
//     * @param excelFile
//     */
//    public static DataSet readExcelPOI(String filePath, Integer cons) throws Exception {
//        File excelFile = new File(filePath);
//        if (excelFile != null) {
//            String fileName = excelFile.getName();
//            fileName = fileName.toLowerCase();
//            if (fileName.toLowerCase().endsWith(EXCEL_2003)) {
//                DataSet dataSet = readExcelPOI2003(excelFile, cons);
//                return dataSet;
//            }
//            if (fileName.toLowerCase().endsWith(EXCEL_2007)) {
//                DataSet dataSet = readExcelPOI2007(excelFile, cons);
//                return dataSet;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 读取Excel2003的表单
//     *
//     * @param excelFile
//     * @return
//     * @throws Exception
//     */
//    private static DataSet readExcelPOI2003(File excelFile, Integer rCons)
//            throws Exception {
//        List<String[]> datasList = new ArrayList<String[]>();
//        Set<String> colsSet = new HashSet<String>();
//        InputStream input = new FileInputStream(excelFile);
//        HSSFWorkbook workBook = new HSSFWorkbook(input);
//        // 获取Excel的sheet数量
//        Integer sheetNum = workBook.getNumberOfSheets();
//        // 循环Sheet表单
//        for (int i = 0; i < sheetNum; i++) {
//            HSSFSheet sheet = workBook.getSheetAt(i);
//            if (sheet == null) {
//                continue;
//            }
//            // 获取Sheet里面的Row数量
//            Integer rowNum = sheet.getLastRowNum() + 1;
//            for (int j = 0; j < rowNum; j++) {
//                if (j>rCons) {
//                    System.out.println("===========");
//                    HSSFRow row = sheet.getRow(j);
//                    if (row == null) {
//                        continue;
//                    }
//
//                    Integer cellNum = row.getLastCellNum() + 1;
//                    String[] datas = new String[cellNum];
//                    for (int k = 0; k < cellNum; k++) {
//                        HSSFCell cell = row.getCell(k);
//                        if (cell == null) {
//                            continue;
//                        }
//                        if (cell != null) {
//                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                            String cellValue = "";
//                            int cellValueType = cell.getCellType();
//                            if (cellValueType == cell.CELL_TYPE_STRING) {
//                                cellValue = cell.getStringCellValue();
//                            }
//                            if (cellValueType == cell.CELL_TYPE_NUMERIC) {
//                                Double number = cell.getNumericCellValue();
//
//                                System.out.println("字符串+++=========="+number.intValue());
//                                cellValue = cell.getNumericCellValue() + "";
//                            }
//
//                            if (rCons==k) {
//                                colsSet.add(cellValue);
//                            }
//
//                            System.out.println(cellValue);
//                            datas[k] = cellValue;
//                        }
//                    }
//                    datasList.add(datas);
//                }
//            }
//        }
//        DataSet dataSet = new DataSet(null, null, datasList, colsSet);
//        return dataSet;
//    }
//
//    /**
//     * 读取Excel2007的表单
//     *
//     * @param excelFile
//     * @return
//     * @throws Exception
//     */
//    private static DataSet readExcelPOI2007(File excelFile, Integer rCons) throws Exception {
//        List<String[]> datasList = new ArrayList<String[]>();
//        Set<String> cosSet = new HashSet<String>();
//        InputStream input = new FileInputStream(excelFile);
//        XSSFWorkbook workBook = new XSSFWorkbook(input);
//        // 获取Sheet数量
//        Integer sheetNum = workBook.getNumberOfSheets();
//        for (int i = 0; i < sheetNum; i++) {
//            XSSFSheet sheet = workBook.getSheetAt(i);
//            if (sheet == null) {
//                continue;
//            }
//            // 获取行值
//            Integer rowNum = sheet.getLastRowNum() + 1;
//            for (int j = 0; j < rowNum; j++) {
//                if (j > rCons) {
//                    System.out.println("=============");
//                    XSSFRow row = sheet.getRow(j);
//                    if (row == null) {
//                        continue;
//                    }
//                    Integer cellNum = row.getLastCellNum() + 1;
//                    String[] datas = new String[cellNum];
//                    for (int k = 0; k < cellNum; k++) {
//                        XSSFCell cell = row.getCell(k);
//                        if (cell==null) {
//                            continue;
//                        }
//                        if (cell != null) {
//                            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//                            String cellValue = "";
//                            int cellValueType = cell.getCellType();
//                            if (cellValueType == cell.CELL_TYPE_STRING) {
//                                cellValue = cell.getStringCellValue();
//                            }
//                            if (cellValueType == cell.CELL_TYPE_NUMERIC) {
//                                Double number = cell.getNumericCellValue();
//                                System.out.println("字符串+++=========="+number.toString());
//                                cellValue = cell.getNumericCellValue() + "";
//                            }
//                            System.out.println(cellValue);
//                            if (rCons == k) {
//                                cosSet.add(cellValue);
//                            }
//                            datas[k] = cellValue;
//                        }
//                    }
//                    datasList.add(datas);
//                }
//            }
//        }
//        DataSet dataSet = new DataSet(null, null, datasList,cosSet);
//        return dataSet;
//    }
//
//    public static void main(String[] args) {
//        try {
//            DataSet dataSet = readExcelPOI("C:\\Users\\Dong\\Desktop\\util\\new.xlsx", 0);
//            System.out.println("================================");
//            Set<String> datas = dataSet.getConStrctSet();
//            String[] datastr = new String[datas.size()];
//            datastr = datas.toArray(datastr);
//            for (int i = 0; i < datastr.length; i++) {
//                System.out.println(datastr[i]);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
