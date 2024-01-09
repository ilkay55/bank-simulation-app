package com.ilkay.service.impl;

import com.ilkay.entity.User;
import com.ilkay.repository.UserRepository;
import com.ilkay.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TASK
        //we need to get our own user from database. how ?
        User user = userRepository.findByUsername(username);
        //return some exception if user doesn't exist
        if(user==null){
            throw new UsernameNotFoundException("This user does not exist");
        }
        //return user information as a UserDetails

        return new UserPrincipal(user);
    }