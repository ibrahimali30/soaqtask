package com.ibrahim.soaqtask.model;

public class Country {
    private int id;
    private String titleEn;
    private String titleAr;

    public Country(int id, String titleEn, String titleAr) {
        this.id = id;
        this.titleEn = titleEn;
        this.titleAr = titleAr;
    }

    public int getId() {
        return id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public String getTitleAr() {
        return titleAr;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", titleEn='" + titleEn + '\'' +
                ", titleAr='" + titleAr + '\'' +
                '}';
    }
}
