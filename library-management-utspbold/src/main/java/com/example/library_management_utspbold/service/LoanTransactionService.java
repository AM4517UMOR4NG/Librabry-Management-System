package com.example.library_management_utspbold.service;

import java.util.List;

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
        return loanTransactionRepository.findById(id).orElse(null);
    }

    public LoanTransaction saveLoanTransaction(LoanTransaction loanTransaction) {
        if (loanTransaction.getBook() == null || loanTransaction.getMember() == null) {
            throw new IllegalArgumentException("Harus terisi,baik itu Member maupun Loan");
        }
        return loanTransactionRepository.save(loanTransaction);
    }

    public void deleteLoanTransactionById(Long id) {
        loanTransactionRepository.deleteById(id);
    }

    public long countLoans() {
        return loanTransactionRepository.count();
    }

    public Page<LoanTransaction> getLoanHistoryByUsername(String username, Pageable pageable) {
        return loanTransactionRepository.findByMemberUserUsername(username, pageable);
    }
}
