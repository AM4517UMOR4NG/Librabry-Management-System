package com.example.library_management_utspbold.model;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date membershipDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<LoanTransaction> loanTransactions;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public Member(){
    }

    public Member(String name, String email, Date membershipDate){
        this.name = name;
        this.email = email;
        this.membershipDate = membershipDate;
    }

    public Member(String name, String email, String phone, String address, Date membershipDate){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.membershipDate = membershipDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(Date membershipDate) {
        this.membershipDate = membershipDate;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}