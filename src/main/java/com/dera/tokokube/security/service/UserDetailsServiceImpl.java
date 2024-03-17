package com.dera.tokokube.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dera.tokokube.entity.Pengguna;
import com.dera.tokokube.repository.PenggunaRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    // Inject repository
    @Autowired
    PenggunaRepository penggunaRepository;

    // Load by username dan build pengguna dengan user detail implementation
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Pengguna pengguna = penggunaRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " tidak ditemukan"));
        return UserDetailsImpl.build(pengguna);
    }

}
