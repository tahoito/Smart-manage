package com.example.taho.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;


    @NotNull(message = "Date must not be null")

    private int type;

    private String item;

    private int price;

    private int count = 0;

    private String username;


    // デフォルトコンストラクタ
    public Account() {}

    // コンストラクタ（主にinsert用）
    public Account(Date date, int type, String item, int price, String username) {
        this.date = date;
        this.type = type;
        this.item = item;
        this.price = price;
        this.username = username;
    }

    // --- ゲッター・セッター ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getDate() {
        return date != null ? date : new Date(); // 今の時刻を返すように
    }


    public void setDate(Date date) {
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // デバッグ・表示用
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", type=" + type +
                ", item='" + item + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", username='" + username + '\'' +
                '}';
    }
}
