package com.example.taho.dao;

import com.example.taho.entity.UserProfileDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileDAO, Long> {

    // 🔽 username でプロフィールを検索するメソッド
    UserProfileDAO findByUsername(String username);
}
