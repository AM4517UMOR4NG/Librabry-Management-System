package com.example.library_management_utspbold.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library_management_utspbold.model.LoanTransaction;
import com.example.library_management_utspbold.service.LoanTransactionService;

@Controller
@PreAuthorize("isAuthenticated()")
public class LoanHistoryController {

    @Autowired
    private LoanTransactionService loanTransactionService;

    @GetMapping("/loans/history")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String viewLoanHistory(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        // Get paginated loan history for the current user
        Page<LoanTransaction> loanPage = loanTransactionService.getLoanHistoryByUsername(
            userDetails.getUsername(),
            PageRequest.of(page, size)
        );

        model.addAttribute("loans", loanPage);
        return "loan/history";
    }
}