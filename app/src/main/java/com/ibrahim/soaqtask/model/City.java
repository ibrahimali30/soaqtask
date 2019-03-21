package com.ibrahim.soaqtask.model;

public class City {
    private int id;
    private String titleAr;
    private String titleEn;
    private int countryId;

    public City(int id, String titleAr, String titleEn, int countryId) {
        this.id = id;
        this.titleAr = titleAr;
        this.titleEn = titleEn;
        this.countryId = countryId;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public String getTitleEn() {
        return titleEn;
    }
}
