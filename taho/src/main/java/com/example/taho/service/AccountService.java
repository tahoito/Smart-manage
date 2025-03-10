package com.example.taho.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    public AccountService(AccountDAO dao){
        this.dao = dao;
    }

    // 全件検索
    public List<Account> findAll() {
        List<Account> list = dao.findAll();
        totalPrice = 0;
        for(Account account : list){
            totalPrice += account.getPrice();
        }return list;
    }

    //新規登録
    public Account insertAccount(Account account){
        dao.insertAccount(account);
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

    //年別処理を行う
    public List<Account> findAccountByYear(String year){
        String startDate = year + "-01-01";
        String endDate = year + "12-31";
        List<Account> list = dao.findAccountByYear(startDate,endDate);
        totalPrice = 0;
        for(Account account : list ){
            totalPrice += account.getPrice();
        }return list;

    }

    // AccountService での修正例
    public List<Account> findAccountByYearAndMonth(String year, String month) {
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        String startDate = year + "-" + month + "-" + "01";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, yearInt);
        calendar.set(Calendar.MONTH, monthInt - 1);
        int result = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String endDate = year + "-" + month + "-" + result;
        List<Account> list = dao.findAccountByYearAndMonth(startDate, endDate);
        totalPrice = 0;

    // 収入と支出の区別
    for (Account account : list) {
        if ("income".equals(account.getType())) {
            // 収入処理
            totalPrice += account.getPrice();
        } else if ("expense".equals(account.getType())) {
            // 支出処理
            totalPrice -= account.getPrice();
        }
    }
    return list;
}

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
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
    

}