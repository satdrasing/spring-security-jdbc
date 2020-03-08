package com.satendra.productservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    final private UserDetailsManager userDetailsManager;

    final PasswordEncoder passwordEncoder;

    @PostMapping()
    public ResponseEntity<Void> createUser(@RequestBody UserForm userForm, HttpServletRequest request){

        UserDetails userDetails =  User.withUsername(userForm.getUsername())
               .password(passwordEncoder.encode(userForm.getPassword()))
               .roles(userForm.getRole()).build();

      userDetailsManager.createUser(userDetails);
        URI createdUri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/user/{id}")
                .buildAndExpand(userForm.getUsername())
                .toUri();
        return created(createdUri).build();
    }
}
