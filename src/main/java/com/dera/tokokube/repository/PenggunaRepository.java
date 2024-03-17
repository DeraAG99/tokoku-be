package com.dera.tokokube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dera.tokokube.entity.Pengguna;

public interface PenggunaRepository extends JpaRepository<Pengguna, String> {

    boolean existsByEmail(String email);

}
