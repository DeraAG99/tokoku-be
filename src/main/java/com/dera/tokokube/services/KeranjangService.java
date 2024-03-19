package com.dera.tokokube.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dera.tokokube.entity.Keranjang;
import com.dera.tokokube.entity.Pengguna;
import com.dera.tokokube.entity.Produk;
import com.dera.tokokube.exceptions.BadRequestException;
import com.dera.tokokube.repository.KeranjangRepository;
import com.dera.tokokube.repository.PenggunaRepository;
import com.dera.tokokube.repository.ProdukRepository;

@Service
public class KeranjangService {

    @Autowired
    ProdukRepository produkRepository;

    @Autowired
    PenggunaRepository penggunaRepository;

    @Autowired
    KeranjangRepository keranjangRepository;

    private static final Logger logger = LoggerFactory.getLogger(KeranjangService.class);

    @Transactional
    public Keranjang addKeranjang(String username, String produkId, Double kuantitas)
            throws BadRequestException {
        // cek apakah produk ada di dalam database?
        // cek apakah sudah ada di dalam keranjang milik user?
        // jika sudah ada, maka update kuantitasnya dan hitung
        // jika belum ada maka buat baru

        Produk produk = produkRepository.findById(produkId)
                .orElseThrow(() -> new BadRequestException(
                        "Produk ID " + produkId + " tidak ditemukan"));
        Optional<Keranjang> optional = keranjangRepository.findByPenggunaIdAndProdukId(username, produkId);
        Keranjang keranjang;
        if (optional.isPresent()) {
            keranjang = optional.get();
            keranjang.setKuantitas(keranjang.getKuantitas() + kuantitas);
            keranjang.setJumlah(new BigDecimal(keranjang.getHarga().doubleValue() * keranjang.getKuantitas()));
            keranjangRepository.save(keranjang);
        } else {
            keranjang = new Keranjang();
            keranjang.setId(UUID.randomUUID().toString());
            keranjang.setProduk(produk);
            keranjang.setKuantitas(kuantitas);
            keranjang.setHarga(produk.getHarga());
            keranjang.setJumlah(new BigDecimal(keranjang.getHarga().doubleValue() * keranjang.getKuantitas()));
            keranjang.setPengguna(new Pengguna(username));
            keranjangRepository.save(keranjang);
        }
        return keranjang;

    }

    @Transactional
    public Keranjang updateKuantitas(String username, String produkId, Double kuantitas) throws BadRequestException {
        Keranjang keranjang = keranjangRepository.findByPenggunaIdAndProdukId(username, produkId)
                .orElseThrow(() -> new BadRequestException(
                        "Produk ID " + produkId + " tidak ditemukan dalam keranjang anda"));
        keranjang.setKuantitas(kuantitas);
        keranjang.setJumlah(new BigDecimal(keranjang.getHarga().doubleValue() * keranjang.getKuantitas()));
        keranjangRepository.save(keranjang);
        return keranjang;
    }

    @Transactional
    public void delete(String username, String produkId) throws BadRequestException {
        Optional<Keranjang> keranjang = keranjangRepository.findByPenggunaIdAndProdukId(username, produkId);
        if (!keranjang.isPresent()) {
            throw new BadRequestException(
                    "Produk ID " + produkId + " tidak ditemukan dalam keranjang anda");
        }
        keranjangRepository.delete(keranjang.get());
    }

    public List<Keranjang> findByPenggunaId(String username) {
        return keranjangRepository.findByPenggunaId(username);
    }
}
