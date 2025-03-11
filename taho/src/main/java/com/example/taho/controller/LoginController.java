package com.example.taho.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    // ログインページを表示
    @GetMapping("/login")
    public String showLoginForm() {
        return "registration/login";  // login.htmlページを返す
    }

    // ユーザーログイン処理
    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        try {
            // ユーザー認証を行う
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 認証が成功した場合、SecurityContextに設定
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/account";  // ホームページにリダイレクト
        } catch (Exception e) {
            model.addAttribute("error", "ユーザー名またはパスワードが間違っています");
            return "/registration/login";  // ログイン失敗時、再度ログインページを表示
        }
    }
}