package com.example.library_management_utspbold.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.library_management_utspbold.model.LoanTransaction;

public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, Long> {
    @Query("SELECT l FROM LoanTransaction l JOIN l.member m JOIN m.user u WHERE u.username = :username ORDER BY l.loanDate DESC")
    Page<LoanTransaction> findByMemberUserUsername(@Param("username") String username, Pageable pageable);
}
