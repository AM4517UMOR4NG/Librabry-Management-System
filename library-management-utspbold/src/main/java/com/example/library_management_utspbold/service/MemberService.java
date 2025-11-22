package com.example.library_management_utspbold.service;

import com.example.library_management_utspbold.model.Member;
import com.example.library_management_utspbold.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        Long safeId = Objects.requireNonNull(id, "Member ID must not be null");
        return memberRepository.findById(safeId).orElse(null);
    }

    public void saveMember(Member member) {
        Member safeMember = Objects.requireNonNull(member, "Member entity must not be null");
        System.out.println("Saving member: " + safeMember);
        try {
            memberRepository.save(safeMember);
            System.out.println("Member saved successfully!");
        } catch (Exception e) {
            System.err.println("Error saving member: " + e.getMessage());
            throw e; // Re-throw untuk ditangani di controller
        }
    }

    public void deleteMemberById(Long id) {
        Long safeId = Objects.requireNonNull(id, "Member ID must not be null");
        memberRepository.deleteById(safeId);
    }

    public long countMembers() {
        return memberRepository.count();
    }
}

