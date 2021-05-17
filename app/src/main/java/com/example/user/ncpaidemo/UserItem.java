package com.example.user.ncpaidemo;

import android.text.Editable;

import java.io.Serializable;

public class UserItem implements Serializable {

        private String name;
        private String store;
        private String lCategory;
        private String sCategory;
        private String unit_price;
        private String price;
        private String unit;
        private int count;
        private int amount;
        private int nDay;
        private int img;


        public String getName() {
            return name;
        }

        public String getStore() {
            return store;
        }

        public String getlCategory() {
            return lCategory;
        }

        public String getsCategory() {
            return sCategory;
        }

        public String getUnitPrice() { return unit_price; }

        public String getPrice() { return price; }

        public int getCount() { return count; }

        public String getUnit() { return unit; }

        public int getAmount() { return amount; }

        public int getnDay() { return nDay; }



        public void setName(String name){
            this.name=name;
        }

        public void setUnit_price(String price){
            this.unit_price=unit_price;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setnDay(int nDay) {
            this.nDay = nDay;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

    public UserItem(){
            this.name = "";
            this.store="";
            this.lCategory="";
            this.sCategory="";
            this.unit_price="";
            this.price="";
            this.nDay=0;
            this.count=0;
        }

        public UserItem(String name, String lCategory, String sCategory){
            this.name = name;
            this.lCategory = lCategory;
            this.sCategory = sCategory;
        }
        public UserItem(String name, String store, String count, String unit_price, String price){
            this.name = name;
            this.store = store;
            this.unit_price = unit_price;
            this.price = price;

            if(count==null){
                this.count=0;
            }
            else { this.count=Integer.parseInt(count); };

        }

        public UserItem(String lCategory, String sCategory, int nDay){
            this.lCategory = lCategory;
            this.sCategory = sCategory;
            this.nDay= nDay;
        }

        public void setBase(String lCategory, String sCategory, int nDay){
            this.lCategory = lCategory;
            this.sCategory=sCategory;
            this.nDay=nDay;
        }

        public void print(){
            System.out.print("name : " + name+" | ");
            System.out.print("Store : "+store+" | ");
            System.out.print("lCategory : " + lCategory+" | ");
            System.out.print("sCategory : " + sCategory+" | ");
            System.out.print("count : " + count+" | ");
            System.out.print("nDay : " + nDay+" | ");
            System.out.print("amount : " + amount+" ");
            System.out.print( unit+" | ");
            System.out.print("price : " + price+" | ");
            System.out.println("unit_Price : " + unit_price);

        }

    }


