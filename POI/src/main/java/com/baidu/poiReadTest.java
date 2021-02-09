package com.baidu;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class poiReadTest {

    //构建路径
    String PATH = "D:/myworkspace/idea-projects/POI";

    public void testWirte03() throws IOException {

        //获取文件流
        FileOutputStream inputStream = new FileOutputStream( PATH + "POI班级统计表03.xls");

        //1.创建一个工作簿 excel中的操作都能在这里操作
        Workbook workbook = new HSSFWorkbook();

        //2.创建一个工作表 表中的设置
        Sheet sheet = workbook.createSheet("班级统计表");



    }
}
