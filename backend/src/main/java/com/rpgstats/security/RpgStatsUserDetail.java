package com.rpgstats.security;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class RpgStatsUserDetail extends User {
  private final Integer userId;

  private final com.rpgstats.entity.User user;

  public RpgStatsUserDetail(
      String username, String password, Integer userId, com.rpgstats.entity.User user) {
    super(username, password, List.of());
    this.userId = userId;
    this.user = user;
  }
}
