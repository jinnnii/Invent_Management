package com.example.user.ncpaidemo;

public class UserItem {

        private String name;
        private String store;
        private String lCategory;
        private String sCategory;
        private int count;
        private int nDay;
        private int price;
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

        public int getCount() {
            return count;
        }

        public int getnDay() {
            return nDay;
        }

        public int getPrice() {
            return price;
        }

        public int getImg() {
            return img;
        }

        public UserItem(String name, String lCategory, String sCategory){
            this.name = name;
            this.lCategory = lCategory;
            this.sCategory = sCategory;
        }
    }


