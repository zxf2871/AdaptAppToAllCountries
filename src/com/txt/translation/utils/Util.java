package com.txt.translation.utils;

import com.txt.translation.bean.Language;
import jxl.*;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinfu.zhao on 2016/1/29.
 */
public class Util {
    public static List<Language> getLanguage(String countryCodeFilePath, String valuesFilePath){
        List<Language> list = new ArrayList<>();
        Map<String,String> codes = readCountryCodeFromXLSExcel(countryCodeFilePath);
        Map<String,String[][]> values = readTranslationFromXLSExcel(valuesFilePath);


        for (String key : codes.keySet()) {
            Language language = new Language();
            language.setCountryCode(key);
            language.setTranslationContens(values.get(codes.get(key)));
            list.add(language);
        }

        return list;
    }
    /**
     * 获取国家代码
     * @param fielPath
     * @return
     */
    public static Map<String,String> readCountryCodeFromXLSExcel(String fielPath){
        Map map = new HashMap<String,String>();
        try {
            //打开文件
            Workbook book = Workbook.getWorkbook(new File(fielPath));
            //获得第一个表的工作对象，“0”表示第一个表
            Sheet sheet = book.getSheet(0);
            for(int i=0;i<sheet.getColumns();i++){
                String key = sheet.getCell(i,1).getContents().trim();
                String value = sheet.getCell(i,0).getContents().toLowerCase();
                map.put(key,value);
            }
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取各个国家支持的翻译信息
     * @param fielPath
     * @return
     */
    public static Map<String,String[][]> readTranslationFromXLSExcel(String fielPath){
        Map map = new HashMap<String,String[]>();
        try {
            //打开文件
            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("ISO-8859-1");
            Workbook book = Workbook.getWorkbook(new File(fielPath),workbookSettings);
            Sheet sheet = book.getSheet(0);
            int rowCount = sheet.getRows();
            String stringName = sheet.getCell(0, 1).getContents();
            for(int i=0;i<sheet.getColumns();i++){
                String key = sheet.getCell(i,0).getContents().toLowerCase();
                String values[][] = new String[rowCount-1][2];
                for(int j=0;j<rowCount-1;j++) {
                    values[j][0] = sheet.getCell(0, j + 1).getContents();
                    values[j][1] = handleExceptionString(sheet.getCell(i, j + 1).getContents().toString());
                    System.out.println(values[j][1].toString());
                }
                map.put(key,values);
            }
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 处理异常字符
     * @param s
     * @return
     */
    public static String handleExceptionString(String s){

        //处理单引号引起的异常
        if(s.contains("\'")){
            return s.replace("\'","\\'");
        }
        return s;
    }
}
