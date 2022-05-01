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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final SubmissionService submissionService;

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

    public List<Submission> getUserSubmissions(Long id) {
        return submissionService.getUserSubmissions(id);
    }
}
