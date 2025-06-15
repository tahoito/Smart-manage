package com.example.taho.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.taho.entity.Account;

@Repository
public class AccountDAO {
    private final JdbcTemplate jdbcTemplate;

    public AccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 任意のオブジェクトをint型に変換するユーティリティメソッド
    private int convertToInt(Object value, String columnName) {
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                System.out.println("Error converting value for " + columnName + ": " + value);
                return 0;
            }
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 0;
    }

    // 全件検索
    public List<Account> findByUsername(String username) {
        String sql = "SELECT id, date, type, item, price, username FROM account WHERE username = ? ORDER BY date, id";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, username);
        List<Account> list = new ArrayList<>();

        for (Map<String, Object> result : resultList) {
            Account account = new Account();

            account.setId(convertToInt(result.get("id"), "id"));
            account.setDate((Date) result.get("date")); // ← そのままDate型で渡す
            account.setType(convertToInt(result.get("type"), "type"));
            account.setPrice(convertToInt(result.get("price"), "price"));
            account.setItem((String) result.get("item"));
            account.setUsername((String) result.get("username"));

            list.add(account);
        }
        return list;
    }

    // 新規登録（支出）
    public void insertAccount(Account account) {
        String sql = "INSERT INTO account (date, type, item, price, username, count) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
            sql,
            account.getDate(),
            account.getType(),
            account.getItem(),
            account.getPrice(),
            account.getUsername(),
            account.getCount()
        );
    }

    // 新規登録（収入）
    public void insertIncomeAccount(Account account) {
        String sql = "INSERT INTO account (date, type, item, price, username) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
            sql,
            account.getDate(),
            account.getType(),
            account.getItem(),
            account.getPrice(),
            account.getUsername()
        );
    }

    // 収入合計の取得
    public int getTotalIncome() {
        String sql = "SELECT SUM(price) FROM account WHERE type >= 10";
        Integer totalIncome = jdbcTemplate.queryForObject(sql, Integer.class);
        return (totalIncome != null) ? totalIncome : 0;
    }

    // ID検索
    public Account findAccountById(int id) {
        String sql = "SELECT id, date, type, item, price, username FROM account WHERE id = ?";
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

        Account account = new Account();
        account.setId(convertToInt(result.get("id"), "id"));
        account.setDate((Date) result.get("date"));
        account.setType(convertToInt(result.get("type"), "type"));
        account.setItem((String) result.get("item"));
        account.setPrice(convertToInt(result.get("price"), "price"));
        account.setUsername((String) result.get("username"));

        return account;
    }

    // 削除
    public void deleteAccountById(int id) {
        jdbcTemplate.update("DELETE FROM account WHERE id = ?", id);
    }

    // 更新
    public void updateAccount(Account account) {
        String sql = "UPDATE account SET date = ?, type = ?, item = ?, price = ?, username = ? WHERE id = ?";
        jdbcTemplate.update(
            sql,
            account.getDate(),
            account.getType(),
            account.getItem(),
            account.getPrice(),
            account.getUsername(),
            account.getId()
        );
    }

    // 検索（年・月・タイプ指定）
    public List<Account> searchAccounts(Integer year, Integer month, Integer type, String username) {
        String sql = "SELECT id, date, type, item, price, username FROM account WHERE username = ? AND type < 10";
        List<Object> params = new ArrayList<>();
        params.add(username);

        if (year != null) {
            sql += " AND YEAR(date) = ?";
            params.add(year);
        }
        if (month != null) {
            sql += " AND MONTH(date) = ?";
            params.add(month);
        }
        if (type != null) {
            sql += " AND type = ?";
            params.add(type);
        }

        sql += " ORDER BY date, id";

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, params.toArray());
        List<Account> list = new ArrayList<>();

        for (Map<String, Object> result : resultList) {
            Account account = new Account();
            account.setId(convertToInt(result.get("id"), "id"));
            account.setDate((Date) result.get("date"));
            account.setType(convertToInt(result.get("type"), "type"));
            account.setPrice(convertToInt(result.get("price"), "price"));
            account.setItem((String) result.get("item"));
            account.setUsername((String) result.get("username"));
            list.add(account);
        }

        return list;
    }
}
