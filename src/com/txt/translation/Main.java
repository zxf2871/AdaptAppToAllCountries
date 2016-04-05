package com.txt.translation;

/**
 * Created by xinfu.zhao on 2016/1/22.
 */


import com.txt.translation.bean.Language;
import com.txt.translation.utils.FileUtil;
import com.txt.translation.utils.Util;

import java.util.List;
import java.util.Map;

public class Main {
    public static int addCount = 0;
    public static int updateCount = 0;
    public static void main(String args[]) {
        List<Language> languages =  Util.getLanguage(System.getProperty("user.dir")+"\\countrycode.xls",System.getProperty("user.dir")+"\\translation.xls");
        for(Language language:languages) {
                     FileUtil.makeValuesFile(language);
        }
        System.out.println("addFile: " + addCount+"   updateFile: "+updateCount);

    }
}
