package com.example.library_management_utspbold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.library_management_utspbold.model.*;
import com.example.library_management_utspbold.service.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.library_management_utspbold.repository.RoleRepository;
import com.example.library_management_utspbold.repository.UserRepository;

@SpringBootApplication
public class LibraryManagementUtspboldApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementUtspboldApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner demoData(BookService bookService, MemberService memberService, LoanTransactionService loanService, UserService userService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Hapus semua data lama (urutan: loan -> member -> user)
            loanService.getAllLoanTransactions().forEach(loan -> loanService.deleteLoanTransactionById(loan.getId()));
            memberService.getAllMembers().forEach(member -> memberService.deleteMemberById(member.getId()));
            userRepository.deleteAll();
            // Seed roles
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
            Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
            // Seed admin
            User admin = new User();
            admin.setUsername("Alogo");
            admin.setPassword(passwordEncoder.encode("Alogo.24"));
            admin.setEnabled(true);
            admin.setRoles(java.util.Set.of(adminRole));
            userRepository.save(admin);
            // Seed user
            User user = new User();
            user.setUsername("Ronaldo");
            user.setPassword(passwordEncoder.encode("Ronaldo.7"));
            user.setEnabled(true);
            user.setRoles(java.util.Set.of(userRole));
            userRepository.save(user);
            // Add sample books
            Book book1 = new Book("Bersyukur Kepada TUHAN :)", "H. White. M", "978-0732883565", 2024, 5);
            Book book2 = new Book("Footbal Manager", "M.Lion", "978-5654310789", 2023, 3);
            bookService.saveBook(book1);
            bookService.saveBook(book2);
            // Add sample members
            Member member1 = new Member("Alogo", "ask@gmail.com", new java.util.Date());
            member1.setUser(admin);
            Member member2 = new Member("Lionel", "iduB@gmail.com", new java.util.Date());
            member2.setUser(user);
            memberService.saveMember(member1);
            memberService.saveMember(member2);
            // Add sample loan transactions
            java.util.Calendar cal = java.util.Calendar.getInstance();
            java.util.Date loanDate = cal.getTime();
            cal.add(java.util.Calendar.DATE, 14); // Due date is 14 days from loan date
            java.util.Date dueDate = cal.getTime();
            LoanTransaction loan1 = new LoanTransaction(book1, member1, loanDate, dueDate, null);
            LoanTransaction loan2 = new LoanTransaction(book2, member2, loanDate, dueDate, null);
            loanService.saveLoanTransaction(loan1);
            loanService.saveLoanTransaction(loan2);
        };
    }
}