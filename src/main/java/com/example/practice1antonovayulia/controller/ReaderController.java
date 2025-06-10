package com.example.practice1antonovayulia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.practice1antonovayulia.model.Reader;
import com.example.practice1antonovayulia.repository.ReaderRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reader")
public class ReaderController {
    private final ReaderRepository repo;

    public ReaderController(ReaderRepository repo) {
        this.repo = repo;
    }

    // Створити нового читача
    @PostMapping
    public ResponseEntity<Reader> register(@RequestBody Reader reader) {
        Reader savedReader = repo.save(reader);
        return ResponseEntity.ok(savedReader);
    }

    // Отримати всіх читачів
    @GetMapping
    public ResponseEntity<List<Reader>> all() {
        List<Reader> readers = repo.findAll();
        return ResponseEntity.ok(readers);
    }

    // Отримати читача за ID
    @GetMapping("/{id}")
    public ResponseEntity<Reader> get(@PathVariable Long id) {
        Optional<Reader> reader = repo.findById(id);
        return reader.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Оновити читача за ID
    @PutMapping("/{id}")
    public ResponseEntity<Reader> update(@PathVariable Long id, @RequestBody Reader updatedReader) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedReader.setId(id);
        Reader savedReader = repo.save(updatedReader);
        return ResponseEntity.ok(savedReader);
    }

    // Видалити читача за ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}