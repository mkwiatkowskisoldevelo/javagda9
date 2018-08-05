package com.sda.spring.java11.dto;

import com.sda.spring.java11.model.User;
import lombok.Data;

@Data
public class UserDto {

  private String username;
  private Long id;

  public UserDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }
}
