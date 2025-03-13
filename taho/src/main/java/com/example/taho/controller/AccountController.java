package com.example.taho.controller;

import java.net.Authenticator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.taho.entity.Account;
import com.example.taho.service.AccountService;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


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

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		List<Account> list = service.findByUsername(username);
		int totalIncome = accountService.getTotalIncome(); // 総収入
		int totalPrice = list.stream().mapToInt(Account::getPrice).sum();
    	int balance = totalIncome - totalPrice; // 残額を計算

		Map<String, Integer> expensesByCategory = service.getExpenseByCategory(username);
		System.out.println(list);
		model.addAttribute("list", list);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("getTotalIncome",totalIncome);
		model.addAttribute("expenseData", expensesByCategory);
		model.addAttribute("balance", balance);
		return "account/index";
	}


	@Autowired
    private AccountService accountService;
    
	@PostMapping("/add")
		public String addAccount(Account account) {
    	accountService.addAccount(account); // 新しいデータを追加
    	return "redirect:/account/"; // 一覧ページをリロードして新しいデータを表示
	}


	// 新規登録画面へ遷移
	@GetMapping("/account/insert")
	public String goInsert() {
		return "account/insert";
	}

	// 新規登録画面へ遷移
	@PostMapping("/account/insert")
	public String insert(Model model, Account account) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    account.setUsername(username);
    service.insertAccount(account);

    model.addAttribute("account", account);
    return "account/insertComplete";
	}

	@GetMapping("/account/insertIncome")
	public String goInsertIncome() {
		return "account/insertIncome";
	}

	@PostMapping("/account/insertIncome")
	public String insertIncome(Model model, Account account){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

	account.setUsername(username);
    service.insertIncomeAccount(account);

	model.addAttribute("account", account);
    return "account/insertIncomeComplete";
	}

	// 収入の削除処理
	@PostMapping("/account/deleteIncome")
	public String deleteIncome(Model model, @RequestParam int id) {
    	// 削除前に収入データを取得
    	Account account = service.getAccountById(id);
    	if (account != null && account.getType() >= 10) { // 🔹 typeが収入(10以上)の場合のみ削除
        	model.addAttribute("account", account);
        	service.deleteAccountById(id);
    	}
    	return "redirect:/account/"; 
	}

	// 収入更新画面へ遷移
@GetMapping("/account/updateIncomeInput")
public String updateIncomeInput(Model model, @RequestParam int id) {
    Account account = service.findAccountById(id);
    if (account != null && account.getType() >= 10) { 
        model.addAttribute("account", account);
        return "account/updateIncomeInput";
    }
    return "redirect:/account/"; // 該当しない場合はリダイレクト
}

	// 収入の更新処理
	@PostMapping("/account/updateIncome")
	public String updateIncome(Model model, Account account) {
    	if (account.getType() >= 10) { 
        	service.updateAccount(account);
        	model.addAttribute("account", account);
    	}
    	return "account/updateIncomeComplete";
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

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    List<Account> list = service.searchAccounts(year, month, type, username);
    
    int totalPrice = list.stream().mapToInt(Account::getPrice).sum();

    model.addAttribute("list", list);
    model.addAttribute("totalPrice", totalPrice);

    return "account/search";
}

	



}