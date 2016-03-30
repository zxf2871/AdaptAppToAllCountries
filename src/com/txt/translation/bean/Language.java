package com.txt.translation.bean;

/**
 * Created by xinfu.zhao on 2016/1/29.
 */
public class Language {
    private String countryCode;
    private String translationContens[][];

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String[][] getTranslationContens() {
        return translationContens;
    }

    public void setTranslationContens(String[][] translationContens) {
        this.translationContens = translationContens;
    }
}
