package com.example.library_management_utspbold.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library_management_utspbold.service.BookService;
import com.example.library_management_utspbold.service.MemberService;
import com.example.library_management_utspbold.service.LoanTransactionService;

@Controller
public class MainController {
    @Autowired
    private BookService bookService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LoanTransactionService loanTransactionService;

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
    @GetMapping("/")
    public String home(Model model, Authentication authentication, @RequestParam(value = "search", required = false) String search) {
        // Add stats for admin
        model.addAttribute("bookCount", bookService.countBooks());
        model.addAttribute("memberCount", memberService.countMembers());
        model.addAttribute("loanCount", loanTransactionService.countLoans());
        // Add username and roles for user features
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("authorities", authentication.getAuthorities());
        }
        // Add book list for homepage search
        model.addAttribute("books", bookService.searchBooks(search));
        model.addAttribute("search", search);
        return "index";
    }
    
    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        // Add more user info if needed
        return "profile";
    }

    @GetMapping("/loan-history")
    public String userLoanHistory(Model model, Authentication authentication) {
        // You can implement logic to show only the user's own loans
        model.addAttribute("username", authentication.getName());
        return "loan-history";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}