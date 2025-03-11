package com.example.taho.entity;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.validation.constraints.NotNull;


public class Account {
    // フィールド
    private int id;
    @NotNull(message = "Date must not be null")
    private String date;
    private int type;
    private String item;
    private int price;
    private int count;

    // 引数なしのデフォルトコンストラクタ
    public Account() {
    }

    // 既存の引数付きコンストラクタ
    public Account(String date, int type, String item, int price) {
        this.date = date;
        this.type = type;
        this.item = item;
        this.price = price;
    }

    // getterとsetter
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return (date != null && !date.isEmpty()) ? date : new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }


    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
    return "Account{id=" + id + ", date='" + date + "', type=" + type + ", item='" + item + "', price=" + price + "}";
    }


}
