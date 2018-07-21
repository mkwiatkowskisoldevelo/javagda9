package com.sda.spring.java11.controller;

import com.sda.spring.java11.model.Book;
import com.sda.spring.java11.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

  @Autowired
  private BookService bookService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Book> search(
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "minPrice", defaultValue = "0") Double minPrice,
      @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
    return bookService.search(name, maxPrice, minPrice);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Book create(@RequestBody Book newBook) {
    return bookService.add(newBook);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Book getById(@PathVariable("id") Long id) {
    return bookService.get(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    bookService.delete(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Book update(
      @PathVariable Long id,
      @RequestBody Book book) {
    return bookService.update(id, book);
  }
}
