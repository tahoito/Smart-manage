package com.example.taho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
public class HouseHoldAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseHoldAccountApplication.class, args);
    }

    // JdbcTemplate Bean の定義
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    // DataSource Bean の定義
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");  // MySQL のドライバー
        dataSource.setUrl("jdbc:mysql://localhost:3306/HouseHoldAccount");  // DB の URL
        dataSource.setUsername("root");  // DB のユーザー名
        dataSource.setPassword("168168");  // DB のパスワード
        return dataSource;
    }
}
