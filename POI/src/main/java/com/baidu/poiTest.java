package com.baidu;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileOutputStream;

public class poiTest {

    //构建路径
    String PATH = "D:/myworkspace/idea-projects/POI";

    @Test
    public void testWirte03() throws Exception {
        //1.创建一个工作簿
        Workbook workbook = new HSSFWorkbook();

        //2.创建一个工作表
        Sheet sheet = workbook.createSheet("班级统计表");

        //3.创建一个行 (1.1)
        Row row1 = sheet.createRow(0);

        //4.创建一个单元格
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("班级");
        //第一行(1.2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(2005);

        //第二行(2.1)
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        //(2.2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);

        //生成一张表(IO 流) 03 版本使用xls结尾
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "班级统计表03.xls");

        //输出
        workbook.write(fileOutputStream);

        //关闭流
        fileOutputStream.close();

        System.out.println("班级统计表03.xls 生成完毕!");

    }

    @Test
    public void testWirte07() throws Exception {
        //1.创建一个工作簿
        Workbook workbook = new XSSFWorkbook();

        //2.创建一个工作表
        Sheet sheet = workbook.createSheet("班级统计表");

        //3.创建一个行 (1.1)
        Row row1 = sheet.createRow(0);

        //4.创建一个单元格
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("班级");
        //第一行(1.2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(2005);

        //第二行(2.1)
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        //(2.2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);

        //生成一张表(IO 流) 07 版本使用xlsx结尾
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "班级统计表07.xlsx");

        //输出
        workbook.write(fileOutputStream);

        //关闭流
        fileOutputStream.close();

        System.out.println("班级统计表07.xlsx 生成完毕!");

    }

    @Test
    public void testWirte03BigData() throws Exception {
        long begin = System.currentTimeMillis();

        //创建一个工作薄
        Workbook workbook = new HSSFWorkbook();
        //创建一张工作表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNumber = 0; rowNumber < 65536; rowNumber++) {
            Row row = sheet.createRow(rowNumber);

            for (int cellNumber = 0; cellNumber < 10; cellNumber++) {
                Cell cell = row.createCell(cellNumber);
                cell.setCellValue(cellNumber);

            }
        }
        System.out.println("over");

        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "testWirte03BigData.xls");

        workbook.write(fileOutputStream);

        fileOutputStream.close();

        long end = System.currentTimeMillis();

        System.out.println((double) (end-begin)/1000);
    }

    //耗时较长 优化 缓存
    @Test
    public void testWirte07BigData() throws Exception {
        long begin = System.currentTimeMillis();

        //创建一个工作薄
        Workbook workbook = new XSSFWorkbook();
        //创建一张工作表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNumber = 0; rowNumber < 100000; rowNumber++) {
            Row row = sheet.createRow(rowNumber);

            for (int cellNumber = 0; cellNumber < 10; cellNumber++) {
                Cell cell = row.createCell(cellNumber);
                cell.setCellValue(cellNumber);

            }
        }
        System.out.println("over");

        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "testWirte07BigData.xlsx");

        workbook.write(fileOutputStream);

        fileOutputStream.close();

        long end = System.currentTimeMillis();

        System.out.println((double) (end-begin)/1000);


    }

    @Test
    public void testWirte07BigDataS() throws Exception {
        long begin = System.currentTimeMillis();

        //创建一个工作薄
        Workbook workbook = new SXSSFWorkbook();
        //创建一张工作表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNumber = 0; rowNumber < 100000; rowNumber++) {
            Row row = sheet.createRow(rowNumber);

            for (int cellNumber = 0; cellNumber < 10; cellNumber++) {
                Cell cell = row.createCell(cellNumber);
                cell.setCellValue(cellNumber);

            }
        }
        System.out.println("over");

        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "testWirte07BigDataS.xlsx");

        workbook.write(fileOutputStream);

        fileOutputStream.close();

        long end = System.currentTimeMillis();

        System.out.println((double) (end-begin)/1000);

        //清除临时文件
        ((SXSSFWorkbook)workbook).dispose();
    }
}