package com.example.user.ncpaidemo;

public class BaseInfo {

    private String lCategory;
    private String sCategory;
    private int nDay;


    public String getlCategory() {return lCategory;}
    public String getsCategory() {return sCategory;}
    public int getnDay() {return nDay;}

    public BaseInfo() { }

    public BaseInfo(String lCategory, String sCategory, int nDay){
        this.lCategory = lCategory;
        this.sCategory = sCategory;
        this.nDay=nDay;
    }
}
