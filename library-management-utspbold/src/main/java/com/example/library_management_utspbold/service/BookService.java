package com.example.library_management_utspbold.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library_management_utspbold.model.Book;
import com.example.library_management_utspbold.repository.BookRepository;

import java.util.List;
import java.util.Objects;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Book getBookById(Long id) {
        Long safeId = Objects.requireNonNull(id, "Book ID must not be null");
        return bookRepository.findById(safeId).orElse(null);
    }
    
    public Book saveBook(Book book) {
        Book safeBook = Objects.requireNonNull(book, "Book entity must not be null");
        return bookRepository.save(safeBook);
    }
    
    public void deleteBookById(Long id) {
        Long safeId = Objects.requireNonNull(id, "Book ID must not be null");
        bookRepository.deleteById(safeId);
    }
    
    public long countBooks() {
        return bookRepository.count();
    }
    
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks();
        }
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(keyword, keyword, keyword);
    }
}
