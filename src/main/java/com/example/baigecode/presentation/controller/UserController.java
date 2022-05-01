package com.example.baigecode.presentation.controller;

import com.example.baigecode.business.entity.BaigeUser;
import com.example.baigecode.business.entity.Role;
import com.example.baigecode.business.service.RoleService;
import com.example.baigecode.business.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/save")
    public void saveUser() {
        Role role = roleService.saveRole(new Role(0L, "ROLE_USER"));
        userService.saveUser(new BaigeUser(null, "Sanjik", "password", null, 76.7, 1337, 78, 159, 20, 79, 60, "https://assets.leetcode.com/users/avatars/avatar_1646582026.png", List.of(role)));
    }
}
