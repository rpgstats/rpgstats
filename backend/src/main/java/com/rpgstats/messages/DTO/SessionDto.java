package com.rpgstats.messages.DTO;

import lombok.Data;

import java.time.Instant;

@Data
public class SessionDto {
  private Integer id;

  private String name;

  private String description;

  private Integer maxNumberOfPlayers;

  private Boolean creatorAsPlayer;

  private Instant createdAt;

  private Integer gameSystemId;

  private Integer creatorId;
}
