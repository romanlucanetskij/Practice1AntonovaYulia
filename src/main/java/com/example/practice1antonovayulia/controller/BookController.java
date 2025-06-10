package com.example.practice1antonovayulia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.practice1antonovayulia.model.Book;
import com.example.practice1antonovayulia.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }

    // Створити нову книгу
    @PostMapping
    public ResponseEntity<Book> add(@RequestBody Book book) {
        Book savedBook = repo.save(book);
        return ResponseEntity.ok(savedBook);
    }

    // Отримати всі книги
    @GetMapping
    public ResponseEntity<List<Book>> all() {
        List<Book> books = repo.findAll();
        return ResponseEntity.ok(books);
    }

    // Отримати книгу за ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        Optional<Book> book = repo.findById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Оновити книгу за ID
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book updatedBook) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedBook.setId(id);
        Book savedBook = repo.save(updatedBook);
        return ResponseEntity.ok(savedBook);
    }

    // Видалити книгу за ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Отримати книги за authorId
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Book>> byAuthor(@PathVariable Long authorId) {
        List<Book> books = repo.findByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }

    // Отримати книги за genreId
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<Book>> byGenre(@PathVariable Long genreId) {
        List<Book> books = repo.findByGenreId(genreId);
        return ResponseEntity.ok(books);
    }
}
