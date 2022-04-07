package com.example.baigecode.business.service;

import com.example.baigecode.persistance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository userRepo;
}
