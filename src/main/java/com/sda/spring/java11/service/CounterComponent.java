package com.sda.spring.java11.service;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CounterComponent {

  private int counter = 0;

  {
    System.out.println("CREATE");
  }

  public int increment() {
    return ++counter;
  }
}
