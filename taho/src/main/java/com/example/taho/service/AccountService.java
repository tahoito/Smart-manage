package com.example.taho.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taho.dao.AccountDAO;
import com.example.taho.entity.Account;

@Service
public class AccountService{
    //DataAccountObject:データーベース操作のこと！
    private final AccountDAO dao;

    private int totalPrice;
    private int totalIncome;
    public AccountService(AccountDAO dao){
        this.dao = dao;
    }

    // 全件検索
    public List<Account> findByUsername(String username) {
        List<Account> list = dao.findByUsername(username);
        totalPrice = list.stream().mapToInt(Account::getPrice).sum();
        return list;
    }

    //新規登録
    public Account insertAccount(Account account){
        dao.insertAccount(account);
        return account;
    }


    public Account insertIncomeAccount(Account account){
        dao.insertIncomeAccount(account);
        return account;
    }

    //1件検索
    public Account findAccountById(int id){
        return dao.findAccountById(id);
    }

    //1件削除
    public void deleteAccountById(int id){
        dao.deleteAccountById(id);
    }

    //1件更新
    public void updateAccount(Account account){
        dao.updateAccount(account);
    }

    public List<Account> searchAccounts(Integer year, Integer month, Integer type, String username) {
        return dao.searchAccounts(year, month, type, username);
    }
    

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalIncome() { // 収入の合計を取得
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) { // 収入の合計を設定
        this.totalIncome = totalIncome;
    }
    

    // 仮にデータを保存するためのリストを使います（実際にはデータベースを使用します）
    private List<Account> accountList = new ArrayList<>();

    // addAccountメソッドを追加
    public void addAccount(Account account) {
        // ここでアカウントをリストに追加します（データベースの場合は適切な保存処理に置き換えます）
        accountList.add(account);
    }

    // 必要であれば、アカウントのリストを取得するメソッドも追加できます
    public List<Account> getAllAccounts() {
        return accountList;
    }

    public Account getAccountById(int id) {
        return dao.findAccountById(id); // DAOのメソッドを呼び出す
    }


    public Map<String, Integer> getExpenseByCategory(String username) {
        List<Account> expenses = dao.findByUsername(username);
    
        // カテゴリーIDを名前に変換するマップ
        Map<Integer, String> categoryMap = Map.of(
            1, "食費",
            2, "日用品",
            3, "交通費",
            4, "趣味",
            5, "遊び代",
            6, "勉強",
            7, "ファッション",
            8, "美容",
            9, "その他" // `*` は数字として扱えないから 9 にする
        );
    
        return expenses.stream()
            .collect(Collectors.groupingBy(
                account -> categoryMap.getOrDefault(account.getType(), "その他"), // `int` → `String` 変換
                Collectors.summingInt(Account::getPrice) // 金額を合計
            ));    
    }
        

}