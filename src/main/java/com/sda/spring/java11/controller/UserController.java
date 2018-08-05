package com.sda.spring.java11.controller;

import com.sda.spring.java11.dto.UserDto;
import com.sda.spring.java11.model.User;
import com.sda.spring.java11.repository.UserRepository;
import com.sda.spring.java11.security.AuthorizationHelper;
import com.sda.spring.java11.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private AuthorizationHelper authorizationHelper;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto create(@RequestBody @Valid User user, BindingResult bindingResult) {
    User saveUser = userService.save(user, bindingResult);
    return new UserDto(saveUser);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/users/logout")
  @ResponseStatus(HttpStatus.OK)
  public String logout(OAuth2Authentication auth) {
    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
    String token = details.getTokenValue();
    tokenStore.removeAccessToken(new DefaultOAuth2AccessToken(token));

    return "User logged out successfully";
  }
}