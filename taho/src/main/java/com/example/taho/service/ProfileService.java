package com.example.taho.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taho.dao.UserProfileRepository; // ← 修正
import com.example.taho.entity.UserProfile;

@Service
public class ProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public ProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile getProfileByUsername(String username) {
        return userProfileRepository.findByUsername(username);
    }

    public void updateProfile(UserProfile profile) {
        userProfileRepository.save(profile);
    }

    public void createProfile(UserProfile profile) {
        userProfileRepository.save(profile);
    }

}