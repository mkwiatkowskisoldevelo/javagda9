package com.sda.spring.java11.service;

import static java.util.stream.Collectors.toList;

import com.sda.spring.java11.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private List<Book> books = new ArrayList<>();

  public List<Book> search(String name, Double maxPrice, Double minPrice) {
    return books.stream()
        .filter(book ->
            book.getName().toLowerCase().contains(name.toLowerCase()) &&
                book.getPrice() > minPrice &&
                book.getPrice() < (maxPrice == null ? Double.MAX_VALUE : maxPrice))
        .collect(toList());
  }

  public Book add(Book book) {
    books.add(book);
    return book;
  }

  public Book get(Long id) {
      return books.stream()
          .filter(book -> book.getId().equals(id))
          .findFirst()
          .get();
  }

  public void delete(Long id) {
    Book bookToBeDeleted = books.stream()
        .filter(book -> book.getId().equals(id))
        .findFirst()
        .get();
    if (bookToBeDeleted != null) {
      books.remove(bookToBeDeleted);
    }
  }

  public Book update(Long id, Book book) {
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
