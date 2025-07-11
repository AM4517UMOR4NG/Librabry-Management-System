package com.example.library_management_utspbold.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.library_management_utspbold.model.User;
import com.example.library_management_utspbold.model.Member;
import com.example.library_management_utspbold.model.Role;
import com.example.library_management_utspbold.repository.UserRepository;
import com.example.library_management_utspbold.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import com.example.library_management_utspbold.service.BookService;
import com.example.library_management_utspbold.service.MemberService;
import com.example.library_management_utspbold.service.LoanTransactionService;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
    @Autowired
    private BookService bookService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LoanTransactionService loanTransactionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(Model model, @org.springframework.web.bind.annotation.ModelAttribute("error") String error, @org.springframework.web.bind.annotation.ModelAttribute("success") String success) {
        if (error != null && !error.isEmpty()) model.addAttribute("error", error);
        if (success != null && !success.isEmpty()) model.addAttribute("success", success);
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

    
    
    @GetMapping("/register")
    public String showRegisterForm(Model model, @org.springframework.web.bind.annotation.ModelAttribute("error") String error, @org.springframework.web.bind.annotation.ModelAttribute("success") String success) {
        if (error != null && !error.trim().isEmpty()) model.addAttribute("error", error);
        if (success != null && !success.trim().isEmpty()) model.addAttribute("success", success);
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(
        @org.springframework.web.bind.annotation.RequestParam String username,
        @org.springframework.web.bind.annotation.RequestParam String name,
        @org.springframework.web.bind.annotation.RequestParam String email,
        @org.springframework.web.bind.annotation.RequestParam String password,
        RedirectAttributes redirectAttributes) {
        if (userRepository.findByUsername(username).isPresent()) {
            redirectAttributes.getFlashAttributes().clear();
            redirectAttributes.addFlashAttribute("error", "Username sudah digunakan.");
            return "redirect:/register";
        }
        if (memberService.getAllMembers().stream().anyMatch(m -> m.getEmail() != null && m.getEmail().equalsIgnoreCase(email))) {
            redirectAttributes.getFlashAttributes().clear();
            redirectAttributes.addFlashAttribute("error", "Email sudah digunakan oleh member lain.");
            return "redirect:/register";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(userRole.map(java.util.Set::of).orElseGet(java.util.Set::of));
        userRepository.save(user);
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setMembershipDate(new java.util.Date());
        member.setUser(user);
        memberService.saveMember(member);
        redirectAttributes.getFlashAttributes().clear();
        redirectAttributes.addFlashAttribute("success", "Registrasi berhasil! Silakan login.");
        return "redirect:/login";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }

    @GetMapping("/admin/users")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/user-list";
    }

    @GetMapping("/admin/users/delete/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "User berhasil dihapus.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal menghapus user.");
        }
        return "redirect:/admin/users";
    }
}