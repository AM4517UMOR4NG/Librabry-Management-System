package com.example.library_management_utspbold.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.library_management_utspbold.model.LoanTransaction;
import com.example.library_management_utspbold.service.BookService;
import com.example.library_management_utspbold.service.LoanTransactionService;
import com.example.library_management_utspbold.service.MemberService;

@Controller
@RequestMapping("/loans")
public class LoanTransactionController {

    @Autowired
    private LoanTransactionService loanTransactionService;

    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String listLoans(Model model) {
        model.addAttribute("loans", loanTransactionService.getAllLoanTransactions());
        return "loan/list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String showAddForm(Model model) {
        model.addAttribute("loan", new LoanTransaction());
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("members", memberService.getAllMembers());
        return "loan/form";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model) {
        LoanTransaction loan = loanTransactionService.getLoanTransactionById(id);
        model.addAttribute("loan", loan);
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("members", memberService.getAllMembers());
        return "loan/form";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String saveLoan(@Validated @ModelAttribute LoanTransaction loan, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (loan.getBorrowDate() == null) {
                loan.setBorrowDate(new Date());
            }
            if (loan.getReturnDate() == null) {
                loan.setReturnDate(new Date());
            }
        }
        if (loan.getId() == null) {
            loan.setReturned(false);
            loan.setReturnDate(null);
        }
        loanTransactionService.saveLoanTransaction(loan);
        return "redirect:/loans";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String deleteLoan(@PathVariable Long id) {
        loanTransactionService.deleteLoanTransactionById(id);
        return "redirect:/loans";
    }

    // Method untuk mengembalikan buku
    @GetMapping("/return/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String returnBook(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        LoanTransaction loan = loanTransactionService.getLoanTransactionById(id);
        if (loan == null) {
            redirectAttributes.addFlashAttribute("error", "Transaksi peminjaman tidak ditemukan.");
            return "redirect:/loans";
        }
        // Jika user, pastikan hanya bisa return miliknya sendiri
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))
            && !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            String username = authentication.getName();
            if (loan.getMember() == null || loan.getMember().getUser() == null || !loan.getMember().getUser().getUsername().equals(username)) {
                redirectAttributes.addFlashAttribute("error", "Anda tidak berhak mengembalikan buku ini.");
                return "redirect:/loans";
            }
        }
        if (!loan.isReturned()) {
            loan.setReturned(true);
            loan.setReturnDate(new java.util.Date());
            loanTransactionService.saveLoanTransaction(loan);
            redirectAttributes.addFlashAttribute("success", "Buku berhasil dikembalikan!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Buku sudah dikembalikan sebelumnya.");
        }
        return "redirect:/loans";
    }

    @PostMapping("/borrow/{bookId}")
    @PreAuthorize("hasRole('USER') and !hasRole('ADMIN')")
    public String borrowBook(@PathVariable Long bookId, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        var memberOpt = memberService.getAllMembers().stream()
            .filter(m -> m.getUser() != null && m.getUser().getUsername().equals(username))
            .findFirst();
        if (memberOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Akun Anda belum terdaftar sebagai member perpustakaan.");
            return "redirect:/books/view/" + bookId;
        }
        var book = bookService.getBookById(bookId);
        if (book == null || book.getQuantity() == null || book.getQuantity() <= 0) {
            redirectAttributes.addFlashAttribute("error", "Buku tidak tersedia.");
            return "redirect:/books/view/" + bookId;
        }
        // Kurangi stok buku
        book.setQuantity(book.getQuantity() - 1);
        bookService.saveBook(book);
        // Buat transaksi peminjaman
        LoanTransaction loan = new LoanTransaction();
        loan.setBook(book);
        loan.setMember(memberOpt.get());
        loan.setLoanDate(new Date());
        loan.setDueDate(new Date(System.currentTimeMillis() + 14L * 24 * 60 * 60 * 1000)); // 14 hari
        loan.setReturned(false);
        loan.setReturnDate(null);
        loanTransactionService.saveLoanTransaction(loan);
        redirectAttributes.addFlashAttribute("success", "Peminjaman buku berhasil!");
        return "redirect:/loans";
    }
}
