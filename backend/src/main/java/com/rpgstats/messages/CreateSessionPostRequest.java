package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateSessionPostRequest {
  @NotNull private String name;
  @NotNull private String description;

  @NotNull
  @Min(1)
  private Integer maxNumberOfPlayers;

  @NotNull private Boolean creatorAsPlayer;
  @NotNull private Integer systemId;
}
