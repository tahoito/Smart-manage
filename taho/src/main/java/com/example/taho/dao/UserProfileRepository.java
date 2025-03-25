package com.example.taho.dao;

import com.example.taho.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    // 🔽 username でプロフィールを検索するメソッド
    UserProfile findByUsername(String username);
}
