package com.example.taho.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private int userage;
    private String usergender;
    private int targetSaving;
    private int targetExpensing;
    private String targetThings;  // ← 例：パソコンとか
    private String targetPlace;   // ← 例：ヨーロッパ旅行とか
    private String targetDate;

    // --- Getter & Setter（省略してるけど必要！） ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserage() {
        return userage;
    }

    public void setUserage(int userage) {
        this.userage = userage;
    }

    public String getUsergender() {
        return usergender;
    }

    public void setUsergender(String usergender) {
        this.usergender = usergender;
    }

    public int getTargetSaving() {
        return targetSaving;
    }

    public void setTargetSaving(int targetSaving) {
        this.targetSaving = targetSaving;
    }

    public int getTargetExpensing() {
        return targetExpensing;
    }

    public void setTargetExpensing(int targetExpensing) {
        this.targetExpensing = targetExpensing;
    }

    public String getTargetThings() {
        return targetThings;
    }

    public void setTargetThings(String targetThings) {
        this.targetThings = targetThings;
    }

    public String getTargetPlace() {
        return targetPlace;
    }

    public void setTargetPlace(String targetPlace) {
        this.targetPlace = targetPlace;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }
}