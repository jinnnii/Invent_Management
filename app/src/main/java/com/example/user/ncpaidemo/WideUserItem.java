package com.example.user.ncpaidemo;

public class WideUserItem {
    private String lCategory;
    private String sCategory;
    private String unit;
    private int nowAmount;
    private int maxAmount;
    private int maxCount;
    private int maxDay;

    public String getlCategory() {
        return lCategory;
    }

    public void setlCategory(String lCategory) {
        this.lCategory = lCategory;
    }

    public String getsCategory() {
        return sCategory;
    }

    public void setsCategory(String sCategory) {
        this.sCategory = sCategory;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getNowAmount() {
        return nowAmount;
    }

    public void setNowAmount(int nowAmount) {
        this.nowAmount = nowAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getMaxDay() {
        return maxDay;
    }

    public void setMaxDay(int maxDay) {
        this.maxDay = maxDay;
    }

    public void print() {
        System.out.print("\n lCategory : " + lCategory + " | ");
        System.out.print("sCategory : " + sCategory + " | ");
        System.out.print("Maxcount : " + maxCount + " | ");
        System.out.print("MAxDay : " + maxDay + " | ");
        System.out.print("NowAmount : " + nowAmount + " ");
        System.out.print(" | maxAmount : " + maxAmount + " ");
        System.out.println(unit + " | ");

    }
}
