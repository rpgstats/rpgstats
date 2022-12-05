package com.rpgstats.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateSessionPutRequest {
  @NotNull private String name;
  @NotNull private String description;

  @NotNull
  @Min(1)
  private Integer maxNumberOfPlayers;

  @NotNull private Boolean creatorAsPlayer;
}
