package com.example.library_management_utspbold.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.library_management_utspbold.model.Book;
import com.example.library_management_utspbold.service.BookService;
import com.example.library_management_utspbold.service.LoanTransactionService;
import com.example.library_management_utspbold.service.MemberService;




@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private LoanTransactionService loanTransactionService;
    @Autowired
    private MemberService memberService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String listBooks(Model model, Authentication authentication) {
        model.addAttribute("books", bookService.getAllBooks());
        // Tambahkan riwayat peminjaman user jika login sebagai USER
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            String username = authentication.getName();
            var memberOpt = memberService.getAllMembers().stream()
                .filter(m -> m.getUser() != null && m.getUser().getUsername().equals(username))
                .findFirst();
            if (memberOpt.isPresent()) {
                var userLoans = loanTransactionService.getAllLoanTransactions().stream()
                    .filter(l -> l.getMember() != null && l.getMember().getId().equals(memberOpt.get().getId()))
                    .toList();
                model.addAttribute("userLoans", userLoans);
            } else {
                model.addAttribute("userLoans", java.util.Collections.emptyList());
            }
        }
        return "book/list";
    }
    
    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/form";
    }
    
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book/form";
    }
    
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }
    
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }
    
    @GetMapping("/view/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            model.addAttribute("error", "Buku tidak ditemukan.");
            return "redirect:/";
        }
        model.addAttribute("book", book);
        return "book/detail";
    }
}

