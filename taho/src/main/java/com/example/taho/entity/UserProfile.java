package com.example.taho.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class UserProfile {
    @Entity

    public class UserProfile {
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String username; // ← このフィールド名と一致してる必要あり！
        private int userage;
        private String usergender;
        private int targetSaving;
        private int targetExpensing;
        private int targetThings;
        private int targetPlace;
        private String targetDate;

    }

    
}
