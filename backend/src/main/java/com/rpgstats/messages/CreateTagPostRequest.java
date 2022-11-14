package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateTagPostRequest {
  @NotNull private String name;
}
