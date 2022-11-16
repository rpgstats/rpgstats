package com.rpgstats.security;

import com.rpgstats.entity.User;
import com.rpgstats.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RpgStatsUserDetailsService implements UserDetailsService {
  UserRepository userRepository;

  public RpgStatsUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public RpgStatsUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userOptional = userRepository.findByUsername(username);
    User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
    return new RpgStatsUserDetail(user.getUsername(), user.getPassword(), user.getId(),user);
  }
}
