package com.br.dong.poi_Excel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-28
 * Time: 上午11:05
 * To change this template use File | Settings | File Templates.
 */
public class TestReadAndWrite {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Dong\\Desktop\\util\\";
        String fileName = "test";
        String fileType = "xlsx";
//        writer(path, fileName, fileType);
//        read(path, fileName, fileType);
       read(path,"new","xlsx");
//        r("", "test", "xlsx");
//        loadXlsx("C:\\Users\\Dong\\Desktop\\util\\new.xlsx");
    }
    private static void writer(String path, String fileName,String fileType) throws IOException {
        //创建工作文档对象
        Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook();
        }
        else if(fileType.equals("xlsx"))
        {
            wb = new XSSFWorkbook();
        }
        else
        {
            System.out.println("您的文档格式不正确！");
        }
        //创建sheet对象
        Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
        //循环写入行数据
        for (int i = 0; i < 5; i++) {
            Row row = (Row) sheet1.createRow(i);
            //循环写入列数据
            for (int j = 0; j < 8; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue("测试"+j);
            }
        }
        //创建文件流
        OutputStream stream = new FileOutputStream(path+fileName+"."+fileType);
        //写入数据
        wb.write(stream);
        //关闭文件流
        stream.close();
    }
    public static List read(String path,String fileName,String fileType) throws IOException
    {
        InputStream stream = new FileInputStream(path+fileName+"."+fileType);
        Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(stream);
        }
        else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(stream);
        }
        else {
            System.out.println("您输入的excel格式不正确");
        }

//        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            System.out.println("sheet名称：" + wb.getSheetName(0));
            String info;
            Sheet sheet = wb.getSheetAt(0);
            List list=new ArrayList();
            for (Row row : sheet) {
                 info="";
                for (Cell cell : row) {
                    if(cell.getCellType()==1){
                        info=info+cell.getStringCellValue()+",";
//                        System.out.print(cell.getStringCellValue());
                    }else if(cell.getCellType()==0){
//                        System.out.print((int)cell.getNumericCellValue());
                        info=info+(int)cell.getNumericCellValue()+",";
                    }
                        //System.out.print(cell.getStringCellValue() +"."+cell.getRowIndex() +"|");
                }
//                System.out.println(info);
                if(!info.contains("公司邮箱")){
                    String arr[]=info.split(",");
                    if(arr.length==8){
                        ManagerBean bean=new ManagerBean();
                        bean.setJobNum(arr[0]);
                        bean.setName(arr[1]);
                        bean.setSex(arr[2]);
                        bean.setDepartment(arr[3]);
                        bean.setJob(arr[4]);
                        bean.setEmail(arr[5]);
                        bean.setPhone(arr[6]);
                        bean.setDepartmentId(arr[7]);
                        list.add(bean);
//                        System.out.println(bean.toString());
                    }
                }
            }

        return list;
        }
//    }

    /**
     * 读取office 2007 xlsx
     * @param filePath
     */
    public static void loadXlsx(String filePath){
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        XSSFWorkbook xwb = null;
        try {
            xwb = new XSSFWorkbook(filePath);
        } catch (IOException e) {
            System.out.println("读取文件出错");
            e.printStackTrace();
        }
        // 读取第一章表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        // 定义 row、cell
        XSSFRow row;
        String cell="";
        // 循环输出表格中的内容
        for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
                // 通过 row.getCell(j).toString() 获取单元格内容，
                if (j==1&&i!=0) {
                    if(row.getCell(j).getCellType()==1){
                        cell=row.getCell(j).getStringCellValue();
                    }else if(row.getCell(j).getCellType()==0){
                        cell=""+(int)row.getCell(j).getNumericCellValue();
                    }
                }else {
                    cell = row.getCell(j).toString();
                }
            /* //获取字体和背景颜色
            String rgbShort=row.getCell(j).getCellStyle().getFont().getCTFont().getColorArray()[0].xmlText();
            rgbShort=ReadExcel.substringBetween(rgbShort, "rgb="",""/>");
            String rgbShort=row.getCell(j).getCellStyle().getFillBackgroundXSSFColor().getCTColor().toString();
            Color color=new Color(Color.BLUE.getRGB());
            System.out.print(cell +",index:"+rgbShort+" red:"+color.getRed()+" blue:"+color.getBlue()+"	");   */
                System.out.print(cell +"	");
            }
            System.out.println("");
        }
    }
    public static void r(String path,String fileName,String fileType) throws IOException{
        InputStream stream = new FileInputStream(path+fileName+"."+fileType);
        Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(stream);
        }
        else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(stream);
        }
        else {
            System.out.println("您输入的excel格式不正确");
        }
        System.out.println("包含sheet个数:"+wb.getNumberOfSheets());
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            System.out.println(  " ***************工作表名称：" + wb.getSheetName(i)
                    + "  ************");

        }

    }
}
