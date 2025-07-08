package com.example.library_management_utspbold.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String returnBook(@PathVariable Long id) {
        LoanTransaction loan = loanTransactionService.getLoanTransactionById(id);
        if (loan != null && !loan.isReturned()) {
            loan.setReturned(true);
            loan.setReturnDate(new Date()); // Set tanggal pengembalian sebagai tanggal sekarang
            loanTransactionService.saveLoanTransaction(loan); // Simpan perubahan status
        }
        return "redirect:/loans"; // Redirect kembali ke daftar transaksi pinjaman
    }
}
