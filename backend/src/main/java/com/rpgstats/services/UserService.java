package com.rpgstats.services;

import com.rpgstats.entity.User;
import com.rpgstats.exceptions.ConflictDataException;
import com.rpgstats.repositories.UserRepository;
import com.rpgstats.security.messages.SignupRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
  UserRepository userRepository;
  PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User getUserById(Integer id) {
    return userRepository.findById(id).orElseThrow();
  }

  public User getUserByName(String name) {
    return userRepository.findByUsername(name).orElseThrow();
  }

  @Transactional
  public void register(SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      throw new ConflictDataException("Username already in use");
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new ConflictDataException("Email already in use");
    }
    User user = new User();
    user.setUsername(signUpRequest.getUsername());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    userRepository.save(user);
  }
}
