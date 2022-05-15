package com.example.baigecode.business.service;

import com.example.baigecode.business.entity.BaigeUser;
import com.example.baigecode.business.entity.Submission;
import com.example.baigecode.persistance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public Optional<BaigeUser> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public Optional<BaigeUser> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public BaigeUser saveUser(BaigeUser baigeUser) {
        log.info("Saving user: {}", baigeUser.getUsername());
        baigeUser.setPassword(passwordEncoder.encode(baigeUser.getPassword()));
        return userRepo.save(baigeUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BaigeUser> user = userRepo.findByUsername(username);
        if(user.isEmpty()) {
            log.error("User with username {}, not found", username);
            throw new UsernameNotFoundException("Username not found");
        } else {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.get().getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), authorities);
        }
    }

    public int followUser(Long followerId, Long followingId) {
        Optional<BaigeUser> followerUser = userRepo.findById(followerId);
        Optional<BaigeUser> followingUser = userRepo.findById(followingId);
        if(followerUser.isEmpty() || followingUser.isEmpty()) {
            throw new UsernameNotFoundException("follower or following with these id's does not exist!");
        }
        List<BaigeUser> followingsOfFollower = followerUser.get().getFriends();
        for(BaigeUser followingOfFollower : followingsOfFollower) {
            if(Objects.equals(followingOfFollower.getId(), followingId)) {
                return 0;
            }
        }
        followingsOfFollower.add(followingUser.get());
        return 1;
    }

    public int followUser(String followerUsername, String followingUsername) {
        log.info("{} is trying to follow {}", followerUsername, followingUsername);
        if (Objects.equals(followerUsername, followingUsername)) {
            return -1;
        }
        Optional<BaigeUser> followerUser = userRepo.findByUsername(followerUsername);
        Optional<BaigeUser> followingUser = userRepo.findByUsername(followingUsername);
        if(followerUser.isEmpty() || followingUser.isEmpty()) {
            throw new UsernameNotFoundException("follower or following with these usernames does not exist!");
        }
        return followUser(followerUser.get().getId(), followingUser.get().getId());
    }
    public int unfollowUser(Long followerId, Long followingId) {
        Optional<BaigeUser> followerUser = userRepo.findById(followerId);
        Optional<BaigeUser> followingUser = userRepo.findById(followingId);
        if (followerUser.isEmpty() || followingUser.isEmpty()) {
            throw new UsernameNotFoundException("follower or following with these id's does not exist!");
        }
        List<BaigeUser> followingsOfFollower = followerUser.get().getFriends();
        for (BaigeUser followingOfFollower : followingsOfFollower) {
            if(Objects.equals(followingOfFollower.getId(), followingId)) {
                followingsOfFollower.remove(followingOfFollower);
                return 1;
            }
        }
        return 0;
    }

    public int unfollowUser(String followerUsername, String followingUsername) {
        log.info("{} is trying to unfollow {}", followerUsername, followingUsername);
        if (Objects.equals(followerUsername, followingUsername)) {
            return -1;
        }
        Optional<BaigeUser> followerUser = userRepo.findByUsername(followerUsername);
        Optional<BaigeUser> followingUser = userRepo.findByUsername(followingUsername);
        if(followerUser.isEmpty() || followingUser.isEmpty()) {
            throw new UsernameNotFoundException("follower or following with these usernames does not exist!");
        }
        return unfollowUser(followerUser.get().getId(), followingUser.get().getId());
    }


}
