package com.avitam.bankloanapplication.core.service.impl;


import com.avitam.bankloanapplication.model.entity.Customer;
import com.avitam.bankloanapplication.model.entity.User;
import com.avitam.bankloanapplication.repository.CustomerRepository;
import com.avitam.bankloanapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String inputString) {
        Customer user = customerRepository.findByEmail(inputString);
        Customer user1=customerRepository.findByPhone(inputString);
        if ((user == null)&&(user1==null) ) throw new UsernameNotFoundException(inputString);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(user != null){
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
        }
            return new org.springframework.security.core.userdetails.User(user1.getPhone(), user1.getPassword(), grantedAuthorities);
    }
}
