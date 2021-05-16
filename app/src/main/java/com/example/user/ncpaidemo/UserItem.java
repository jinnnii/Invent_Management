package com.example.user.ncpaidemo;

import java.io.Serializable;

public class UserItem implements Serializable {

        private String name;
        private String store;
        private String lCategory;
        private String sCategory;
        private String unit_price;
        private String price;
        private String count;
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

        public String getCount() { return count; }

        public int getnDay() { return nDay; }

        public int getImg() {
            return img;
        }

        public UserItem(String name, String lCategory, String sCategory){
            this.name = name;
            this.lCategory = lCategory;
            this.sCategory = sCategory;
        }
        public UserItem(String name, String store, String count, String unit_price, String price){
            this.name = name;
            this.store = store;
            this.count=count;
            this.unit_price = unit_price;
            this.price = price;
        }
    }


