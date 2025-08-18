package com.ecom.project.security.services;

import com.ecom.project.model.User;
import com.ecom.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImp  implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user =  userRepository.findByName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found with User Name :" + username));

        return UserDetailsImp.build(user);
    }


}
