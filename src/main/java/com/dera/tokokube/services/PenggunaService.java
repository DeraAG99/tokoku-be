package com.dera.tokokube.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dera.tokokube.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dera.tokokube.entity.Pengguna;
import com.dera.tokokube.exceptions.ResourceNotFoundException;
import com.dera.tokokube.repository.PenggunaRepository;

@Service
public class PenggunaService {

    @Autowired
    private PenggunaRepository penggunaRepository;

    public Pengguna findById(String id) {
        return penggunaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pengguna dengan " + id + " Tidak Ditemukan"));
    }

    public List<Pengguna> findAll() {
        return penggunaRepository.findAll();
    }

    public Pengguna create(Pengguna pengguna) throws BadRequestException {
        Map<String, String> errors = new HashMap<>();
        if (!StringUtils.hasText(pengguna.getId())) {
            errors.put("id", "Username harus di isi");
        }

        if (penggunaRepository.existsById(pengguna.getId())) {
            errors.put("id", "Username " + pengguna.getId() + " sudah terdaftar");
        }

        if (!StringUtils.hasText(pengguna.getEmail())) {
            errors.put("email", "Email harus diisi");
        }

        if (penggunaRepository.existsByEmail(pengguna.getEmail())) {
            errors.put("email", "Email " + pengguna.getEmail() + " sudah terdaftar");
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
        
        pengguna.setIsAktif(true);
        return penggunaRepository.save(pengguna);
    }

    public Pengguna edit(Pengguna pengguna) throws BadRequestException {

        if (!StringUtils.hasText(pengguna.getId())) {
            throw new BadRequestException("Username harus di isi");
        }

        if (!StringUtils.hasText(pengguna.getEmail())) {
            throw new BadRequestException("Email harus diisi");
        }

        return penggunaRepository.save(pengguna);
    }

    public void deleteById(String id) {
        penggunaRepository.deleteById(id);
    }
}
