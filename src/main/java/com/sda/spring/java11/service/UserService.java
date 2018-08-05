package com.sda.spring.java11.service;

import com.sda.spring.java11.exception.BindingResultException;
import com.sda.spring.java11.exception.NotFoundException;
import com.sda.spring.java11.model.User;
import com.sda.spring.java11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User save(User user, BindingResult bindingResult) {
        validate(user, null, bindingResult);
        return save(user);
    }

    private void validate(User user, String currentUsername, BindingResult bindingResult) {
        if (!user.getUsername().equals(currentUsername)
                && userRepository.findByUsername(user.getUsername()) != null) {
            bindingResult.addError(
                    new FieldError("user", "field",
                            String.format("User with username %s already exists", user.getUsername())));
        }
        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
    }

    private User save(User user) {
        String newPassword = user.getPassword();

        if (StringUtils.hasText(newPassword)) {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            user.setPassword(encoder.encode(newPassword));
        }
        return userRepository.save(user);
    }
}
