package com.example.library_management_utspbold.service;

import com.example.library_management_utspbold.model.Member;
import com.example.library_management_utspbold.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public void saveMember(Member member) {
        System.out.println("Saving member: " + member);
        try {
            memberRepository.save(member);
            System.out.println("Member saved successfully!");
        } catch (Exception e) {
            System.err.println("Error saving member: " + e.getMessage());
            throw e; // Re-throw untuk ditangani di controller
        }
    }

    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    public long countMembers() {
        return memberRepository.count();
    }
}

