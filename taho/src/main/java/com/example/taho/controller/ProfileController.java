package com.example.taho.controller;

import com.example.taho.entity.UserProfile;
import com.example.taho.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RequestMapping("/menu")
@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // プロフィール表示
    @GetMapping
    public String showProfile(Model model, Principal principal) {
        String username = principal.getName();
        UserProfile profile = profileService.getProfileByUsername(username);
        model.addAttribute("profile", profile);
        return "menu/profile";  // 例: templates/menu/profile.html
    }

    // プロフィール編集画面へ
    @GetMapping("/edit")
    public String editProfile(Model model, Principal principal) {
        String username = principal.getName();
        UserProfile profile = profileService.getProfileByUsername(username);
        model.addAttribute("profile", profile);
        return "menu/profile_edit";  // 例: templates/menu/profile_edit.html
    }

    // プロフィールの更新
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute UserProfile profile, Principal principal) {
        String username = principal.getName();
        profile.setUsername(username);  // 念のため再設定
        profileService.updateProfile(profile);
        return "redirect:/menu";
    }
}
