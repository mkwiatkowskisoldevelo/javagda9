package com.sda.spring.java11.controller;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  private Integer count = 0;
  private List<String> list = new ArrayList<>();

  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok("hello!!!!");
  }

  @GetMapping("/hello2")
  public ResponseEntity<String> hello2() {
    return ResponseEntity.ok("hello2!!!!");
  }

  @GetMapping("/count")
  public ResponseEntity<Integer> count() {
    count++;
    if (count < 5) {
      return ResponseEntity.ok(count);
    }
    return ResponseEntity.badRequest().build();
  }

  @GetMapping("/hello3")
  @ResponseStatus(HttpStatus.OK)
  public String hello3() {
    return "hello3!!!!";
  }

  @PostMapping("/add")
  @ResponseStatus(HttpStatus.CREATED)
  public String add(@RequestBody String newString) {
    list.add(newString);
    return newString;
  }

  @GetMapping("/getAll")
  @ResponseStatus(HttpStatus.OK)
  public List<String> getAll() {
    return list;
  }

  @GetMapping("/get/{index}")
  @ResponseStatus(HttpStatus.OK)
  public String get(@PathVariable("index") Integer asdasd) {
    return list.get(asdasd);
  }

  @GetMapping("/find")
  @ResponseStatus(HttpStatus.OK)
  public List<String> filter(
      @RequestParam(value = "filter", required = false, defaultValue = "") String filter) {
    return list.stream()
        .filter(elem -> elem.contains(filter))
        .collect(toList());
  }
}
