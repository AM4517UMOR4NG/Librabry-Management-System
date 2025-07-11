package com.example.library_management_utspbold.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library_management_utspbold.model.Member;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUser_Username(String username);
}
