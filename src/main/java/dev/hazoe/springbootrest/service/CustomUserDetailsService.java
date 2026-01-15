package dev.hazoe.springbootrest.service;

import dev.hazoe.springbootrest.model.User;
import dev.hazoe.springbootrest.model.UserPrincipal;
import dev.hazoe.springbootrest.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepo userRepo;
    @Autowired
    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        User foundUser = userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
        return new UserPrincipal(foundUser);
    }
}
