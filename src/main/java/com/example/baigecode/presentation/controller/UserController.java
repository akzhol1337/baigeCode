package com.example.baigecode.presentation.controller;

import com.example.baigecode.business.entity.BaigeUser;
import com.example.baigecode.business.entity.Role;
import com.example.baigecode.business.service.RoleService;
import com.example.baigecode.business.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/save")
    public void saveUser() {
        Role role = roleService.getRoleById(0L);
        userService.saveUser(new BaigeUser(null, "Sanjik", "pass", null, 50.7, 1000, 20, 159, 20, 79, 60, "https://stickerly.pstatic.net/sticker_pack/jrgoJQSRsMqUXm7xXV6DQ/HAUK7I/31/2b9db878-a7bc-4fea-8ca7-c1ed406bc411.png", List.of(), List.of()));
    }

    @PostMapping("/follow/{followerUsername}/{followingUsername}")
    public ResponseEntity<?> followUser(@PathVariable String followerUsername, @PathVariable String followingUsername, Principal principal) {
        String message = "";
        if(principal == null) {
            message = "Not logged in";
        } else if(!Objects.equals(principal.getName(), followerUsername)){
            message = "Not enough authorities";
        } else {
            message = "Can't follow yourself";
            int status = userService.followUser(followerUsername, followingUsername);
            if (status == 0) {
                message = "Already followed";
            } else if(status == 1){
                message = "Success";
            }
        }
        return ResponseEntity.ok(Map.of("Message", message));
    }

    @PostMapping("/unfollow/{followerUsername}/{followingUsername}")
    public ResponseEntity<?> unfollowUser(@PathVariable String followerUsername, @PathVariable String followingUsername, Principal principal) {
        String message = null;
        if(principal == null) {
            message = "Not logged in";
        } else if(!Objects.equals(principal.getName(), followerUsername)){
            message = "Not enough authorities";
        } else {
            message = "Can't unfollow yourself";
            int status = userService.unfollowUser(followerUsername, followingUsername);
            if (status == 0) {
                message = "Not followed";
            } else if(status == 1){
                message = "Success";
            }
        }
        return ResponseEntity.ok(Map.of("Message", message));
    }
}
