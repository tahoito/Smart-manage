package com.example.taho.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.taho.entity.Account;
import com.example.taho.service.AccountService;

/**
 * コントローラークラス
 */

@Controller
public class AccountController {

	private final AccountService service;


	public AccountController(AccountService service) {
		this.service = service;
	}

	// 全件表示を行う
	@GetMapping("/account")
	public String account(Model model) {

		List<Account> list = service.findAll();
		int totalPrice = service.getTotalPrice();
		System.out.println(list);
		model.addAttribute("list", list);
		model.addAttribute("totalPrice", totalPrice);
		return "account/index";
	}

	@Autowired
    private AccountService accountService;
    
	@PostMapping("/add")
		public String addAccount(Account account) {
    	accountService.addAccount(account); // 新しいデータを追加
    	return "account/redirect:/"; // 一覧ページをリロードして新しいデータを表示
	}


	// 新規登録画面へ遷移
	@GetMapping("/account/insert")
	public String goInsert() {

		return "account/insert";
	}

	// 新規登録画面へ遷移
	@PostMapping("/account/insert")
	public String insert(Model model, Account account) {

		account = service.insertAccount(account);
		model.addAttribute("account", account);
		return "account/insertComplete";
	}

	// 削除処理を行う
	@PostMapping("/account/delete")
		public String delete(Model model, @RequestParam int id) {
    	// 削除前にアカウント情報を取得する
    	Account account = service.getAccountById(id);
    	if (account != null) {
        	model.addAttribute("account", account); // 削除したアカウント情報をModelに追加
    	}
    	service.deleteAccountById(id);
    	return "redirect:/account/"; 
	}

	@GetMapping("/account/updateInput")
	public String updateInput(Model model, @RequestParam int id) {
		Account account = service.findAccountById(id);
		model.addAttribute("account", account);
		return "account/updateInput";
	}
	
	// 更新処理を行う
	@PostMapping("/account/update")
	public String update(Model model, Account account) {
    	service.updateAccount(account); // まず更新処理を実行
    	model.addAttribute("account", account); // 更新したデータをセット
    	return "account/updateComplete"; // updateComplete.html に遷移
	}

	@GetMapping("/account/search")
	public String search(@RequestParam(required = false) Integer year,
                     	@RequestParam(required = false) Integer month,
                     	@RequestParam(required = false) Integer type,
                     	Model model) {
    	List<Account> list = service.searchAccounts(year, month, type);
    	int totalPrice = list.stream().mapToInt(Account::getPrice).sum(); // 合計金額計算
    	model.addAttribute("list", list);
    	model.addAttribute("totalPrice", totalPrice);
    	return "account/search";
	}




}