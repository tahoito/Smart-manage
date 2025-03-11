package com.example.taho.controller;

import com.example.taho.service.AuthService;
import com.example.taho.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SigninController {

    @Autowired
    private AuthService authService;

    // サインインページを表示
    @GetMapping("/signup")
    public String showSignupForm() {
        return "registration/signup";  // signup.html を正しく返す
    }

    // ユーザー登録処理
    @PostMapping("/signup")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        try {
            User newUser = authService.registerUser(username, password);  // ユーザー登録処理
            model.addAttribute("message", "ユーザー登録が成功しました！ログインしてください。");
            return "redirect:/login";  // 成功したらログインページへリダイレクト
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());  // エラーメッセージを表示
            return "registration/signup";  // 失敗した場合は `signup.html` を再表示
        }
    }
    
}
