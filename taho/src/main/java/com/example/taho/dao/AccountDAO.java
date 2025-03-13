package com.example.taho.dao;

import java.text.SimpleDateFormat;
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

    // 任意のオブジェクトをint型に変換するユーティリティメソッド（private）
    private int convertToInt(Object value, String columnName) {
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                System.out.println("Error converting value for " + columnName + ": " + value);
                return 0; // 適切なデフォルト値を返す（必要に応じて変更）
            }
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 0; // デフォルト値
    }

    // 全件検索処理
    public List<Account> findByUsername(String username) {
        String sql = "SELECT id, date, type, item, price, username FROM account WHERE username = ? ORDER BY date, id";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, new Object[]{username});
        List<Account> list = new ArrayList<>();

        for (Map<String, Object> result : resultList) {
            Account account = new Account();

            // IDを安全に変換
            account.setId(convertToInt(result.get("id"), "id"));

            // 日付を文字列に変換
            Date date = (Date) result.get("date");
            if (date != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                account.setDate(dateFormat.format(date));
            } else {
                account.setDate(null); // 日付がNULLの場合に対応
            }

            // Typeを安全に変換
            account.setType(convertToInt(result.get("type"), "type"));

            // Priceを安全に変換
            account.setPrice(convertToInt(result.get("price"), "price"));

            // Itemを取得（文字列として取得）
            account.setItem((String) result.get("item"));

            account.setUsername((String) result.get("username"));

            // リストに追加
            list.add(account);
        }
        return list;
    }

    // 新規登録処理
    public void insertAccount(Account account) {
        jdbcTemplate.update("INSERT INTO account (date, type, item, price,username) VALUES (?, ?, ?, ?, ?)",
            account.getDate(), account.getType(), account.getItem(), account.getPrice(),account.getUsername());
    }

    //収入追加
    public void insertIncomeAccount(Account account) {
        jdbcTemplate.update("INSERT INTO account (date, type, item, price, username) VALUES (?, ?, ?, ?, ?)",
            account.getDate(), account.getType(), account.getItem(), account.getPrice(),account.getUsername());
    }

    public int getTotalIncome() {
        String sql = "SELECT SUM(price) FROM account WHERE type = '収入'";
        Integer totalIncome = jdbcTemplate.queryForObject(sql, Integer.class);
        return totalIncome != null ? totalIncome : 0; 
    }

    
    // ID検索処理
    public Account findAccountById(int id) {
        String sql = "SELECT id, date, type, item, price, username FROM account WHERE id = ?";
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

        Account account = new Account();
        account.setId(convertToInt(result.get("id"), "id"));

        // 日付を String に変換
        Date date = (Date) result.get("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        account.setDate(dateFormat.format(date));

        // Typeを適切に変換
        account.setType(convertToInt(result.get("type"), "type"));

        // Itemを取得
        account.setItem((String) result.get("item"));

        // Priceを安全に変換
        account.setPrice(convertToInt(result.get("price"), "price"));

        account.setUsername((String) result.get("username"));

        return account;
    }

    // 削除処理
    public void deleteAccountById(int id) {
        jdbcTemplate.update("DELETE FROM account WHERE id = ?", id);
    }

    // 更新処理
    public void updateAccount(Account account) {
        jdbcTemplate.update("UPDATE account SET date = ?, type = ?, item = ?, price = ? username = ? WHERE id = ?",
            account.getDate(), account.getType(), account.getItem(), account.getPrice(), account.getId(), account.getUsername());
    }

    public List<Account> searchAccounts(Integer year, Integer month, Integer type, String username) {
        String sql = "SELECT id, date, type, item, price, username FROM account WHERE username = ?";
        List<Object> params = new ArrayList<>();
        params.add(username); // 🔹 `username` を追加
    
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
            Date date = (Date) result.get("date");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            account.setDate(dateFormat.format(date));
            account.setType(convertToInt(result.get("type"), "type"));
            account.setPrice(convertToInt(result.get("price"), "price"));
            account.setItem((String) result.get("item"));
            account.setUsername((String) result.get("username")); // 🔹 `username` をセット
            list.add(account);
        }
        return list;
    }
    

}
