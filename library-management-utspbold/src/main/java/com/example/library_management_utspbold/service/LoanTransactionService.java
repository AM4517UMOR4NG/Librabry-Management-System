package com.example.library_management_utspbold.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.library_management_utspbold.model.LoanTransaction;
import com.example.library_management_utspbold.repository.LoanTransactionRepository;

@Service
public class LoanTransactionService {

    @Autowired
    private LoanTransactionRepository loanTransactionRepository;

    public List<LoanTransaction> getAllLoanTransactions() {
        return loanTransactionRepository.findAll();
    }

    public LoanTransaction getLoanTransactionById(Long id) {
        Long safeId = Objects.requireNonNull(id, "LoanTransaction ID must not be null");
        return loanTransactionRepository.findById(safeId).orElse(null);
    }

    public LoanTransaction saveLoanTransaction(LoanTransaction loanTransaction) {
        LoanTransaction safeLoan = Objects.requireNonNull(loanTransaction, "LoanTransaction must not be null");
        if (safeLoan.getBook() == null || safeLoan.getMember() == null) {
            throw new IllegalArgumentException("Harus terisi,baik itu Member maupun Loan");
        }
        return loanTransactionRepository.save(safeLoan);
    }

    public void deleteLoanTransactionById(Long id) {
        Long safeId = Objects.requireNonNull(id, "LoanTransaction ID must not be null");
        loanTransactionRepository.deleteById(safeId);
    }

    public long countLoans() {
        return loanTransactionRepository.count();
    }

    public Page<LoanTransaction> getLoanHistoryByUsername(String username, Pageable pageable) {
        return loanTransactionRepository.findByMemberUserUsername(username, pageable);
    }
}
