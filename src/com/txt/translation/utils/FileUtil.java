package com.txt.translation.utils;

import com.txt.translation.bean.Language;
import com.txt.translation.Main;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.List;

/**
 * Created by xinfu.zhao on 2016/1/22.
 */
public class FileUtil {

    /**
     * 写入或者增加翻译的值到vaulse各个文件夹中
     *
     * @param language
     */
    public static void makeValuesFile(Language language) {
        String[][] languageValues = language.getTranslationContens();
        String targetPath = System.getProperty("user.dir") + "\\res_values";
        File targetPath1 = new File(targetPath);
        if (!targetPath1.isDirectory()) {
            targetPath1.mkdir();
        }

        File writename1 = new File(targetPath + "\\values-" + language.getCountryCode() + ""); // 相对路径，如果没有则要建立一个新的output。txt文件
        //创建文件夹
        if (!writename1.isDirectory()) {
            writename1.mkdir();
        }
        File writename = new File(targetPath + "\\values-" + language.getCountryCode() + "\\strings.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件
        if (!writename.isFile()) {
            try {
                Main.addCount++;
                System.out.println("add File:" + writename);
                writename.createNewFile();
                BufferedWriter out = null;
                out = new BufferedWriter(new FileWriter(writename));
                StringBuffer sb = new StringBuffer();
                sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
                sb.append("<resources>\n");

                for (int i = 0; i < languageValues.length; i++) {
                    sb.append("   \t<string name=\"" + language.getTranslationContens()[i][0] + "\">" + language.getTranslationContens()[i][1] + "</string>\n");
                }
                sb.append("</resources>\n");
                out.write(sb.toString()); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
                out.close(); // 最后记得关闭文件
            } catch (IOException e) {
                System.out.print(writename);
                e.printStackTrace();
            }
        } else {
            Main.updateCount++;
            // 使用SAXBuilder解析器解析xml文件
            SAXBuilder sb = new SAXBuilder();
            Document doc = null;
            try {
                doc = sb.build(writename);
                Element root = doc.getRootElement();
                List<Element> list = root.getChildren("string");

                for (int i = 0; i < languageValues.length; i++) {
                    String stringName = language.getTranslationContens()[i][0];
                    String stringValue = language.getTranslationContens()[i][1];

                    boolean isExits = false;
                    Element exitsElement = null;
                    for (Element el : list) {
                        if (el.getAttribute("name").getValue().equals(stringName)) {
                            exitsElement = el;
                            isExits = true;
                            System.out.println("value: " + stringName + " has exits");
                            break;
                        }

                    }
                    if (!isExits) {
                        //增加一个
                        Element stringNode = new Element("string");
                        stringNode.setAttribute("name", stringName);
                        stringNode.setText(stringValue);
                        root.addContent(stringNode);
                        System.out.println("update File with adding element:" + writename);

                    }else{
                        //修改一个
                        exitsElement.setAttribute("name", stringName);
                        exitsElement.setText(stringValue);
                        System.out.println("update File with updating element:" + writename);

                    }
                    saveXML(doc,writename.getPath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void saveXML(Document doc,String file) {
        // 将doc对象输出到文件
        try {
            // 创建xml文件输出流
            XMLOutputter xmlopt = new XMLOutputter();

            // 创建文件输出流
            FileWriter writer = new FileWriter(file);

            // 指定文档格式
            Format fm = Format.getPrettyFormat();
            // fm.setEncoding("GB2312");
            xmlopt.setFormat(fm);

            // 将doc写入到指定的文件中
            xmlopt.output(doc, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
