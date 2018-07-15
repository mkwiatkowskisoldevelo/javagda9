package com.sda.spring.java11.controller;

import static java.util.stream.Collectors.toList;

import com.sda.spring.java11.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

  private List<Book> books = new ArrayList<>();

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Book> search(
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "minPrice", defaultValue = "0") Double minPrice,
      @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
    return books.stream()
        .filter(book ->
            book.getName().toLowerCase().contains(name.toLowerCase()) &&
                book.getPrice() > minPrice &&
                book.getPrice() < (maxPrice == null ? Double.MAX_VALUE : maxPrice))
        .collect(toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Book create(@RequestBody Book newBook) {
    books.add(newBook);
    return newBook;
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Book getById(@PathVariable("id") Long id) {
    return books.stream()
        .filter(book -> book.getId().equals(id))
        .findFirst()
        .get();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    Book bookToBeDeleted = books.stream()
        .filter(book -> book.getId().equals(id))
        .findFirst()
        .get();
    if (bookToBeDeleted != null) {
      books.remove(bookToBeDeleted);
    }
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Book update(
      @PathVariable Long id,
      @RequestBody Book book) {
    Optional<Book> bookToBeUpdated = books.stream()
        .filter(item -> item.getId().equals(id))
        .findFirst();
    if (bookToBeUpdated.isPresent()) {
      books.remove(bookToBeUpdated.get());
      book.setId(id);
      books.add(book);
      return book;
    } else {
      throw new RuntimeException(
          String.format("Book with id %s not found", id));
    }
  }
}
