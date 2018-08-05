package com.sda.spring.java11.security;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationEndpointConfigurer {

    @Autowired
    private AuthorizationEndpoint authorizationEndpoint;

    @PostConstruct
    public void init() {
        authorizationEndpoint.setUserApprovalPage("forward:/oauth/confirm_access");
    }
}
