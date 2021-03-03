package com.baidu;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class poiReadTest {

    //构建路径
    String PATH = "D:/myworkspace/idea-projects/POI";

    @Test
    public void testRead03() throws IOException {

        //获取文件流
        FileInputStream inputStream = new FileInputStream( PATH + "班级统计表03.xls");

        // 1.创建一个工作簿 excel中的操作都能在这里操作
        Workbook workbook = new HSSFWorkbook(inputStream);
        // 2.得到表
        Sheet sheet = workbook.getSheetAt(0);
        // 3.得到行
        Row row = sheet.getRow(0);
        // 4.得到列
        Cell cell = row.getCell(1);

        //读取值的时候,需要注意值的类型
        //获取字符串类型
        //System.out.println(cell.getStringCellValue());
        System.err.println(cell.getNumericCellValue());
        inputStream.close();
    }

    @Test
    public void testRead07() throws IOException {

        //获取文件流
        FileInputStream inputStream = new FileInputStream( PATH + "班级统计表07.xlsx");

        // 1.创建一个工作簿 excel中的操作都能在这里操作
        Workbook workbook = new XSSFWorkbook(inputStream);
        // 2.得到表
        Sheet sheet = workbook.getSheetAt(0);
        // 3.得到行
        Row row = sheet.getRow(0);
        // 4.得到列
        Cell cell = row.getCell(1);

        //读取值的时候,需要注意值的类型
        //获取字符串类型
        //System.out.println(cell.getStringCellValue());
        System.err.println(cell.getNumericCellValue());
        inputStream.close();
    }

    @Test
    public void testCellType(){

    }
}






















