package com.dera.tokokube.services;

import java.util.List;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dera.tokokube.entity.Produk;
import com.dera.tokokube.exceptions.ResourceNotFoundException;
import com.dera.tokokube.repository.KategoriRepository;
import com.dera.tokokube.repository.ProdukRepository;

@Service
public class ProdukService {

    @Autowired
    KategoriRepository kategoriRepository;

    @Autowired
    private ProdukRepository produkRepository;

    public List<Produk> findAll() {
        return produkRepository.findAll();
    }

    public Produk findById(String id) {
        return produkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produk ID " + id + " tidak ditemukan"));
    }

    public Produk create(Produk produk) throws BadRequestException {
        // Validasi request
        if (!StringUtils.hasText(produk.getNama())) {
            throw new BadRequestException("Nama produk tidak boleh kosong");
        }

        if (produk.getKategori() == null) {
            throw new BadRequestException("Kategori produk tidak boleh kosong");
        }

        if (!StringUtils.hasText(produk.getKategori().getId())) {
            throw new BadRequestException("Kategori ID tidak boleh kosong");
        }

        if (produk.getStok() < 1 || produk.getStok() == null) {
            throw new BadRequestException("Stok tidak boleh kosong");
        }

        if (produk.getHarga() == null || produk.getHarga().signum() < 1) {
            throw new BadRequestException("Harga tidak boleh kosong");
        }

        kategoriRepository.findById(produk.getKategori().getId())
                .orElseThrow(() -> new BadRequestException(
                        "Kategori ID " + produk.getKategori().getId() + " tidak ditemukan di database"));

        // Create produk
        produk.setId(UUID.randomUUID().toString());
        return produkRepository.save(produk);
    }

    public Produk edit(Produk produk) throws BadRequestException {
        // Validasi request

        if (!StringUtils.hasText(produk.getId())) {
            throw new BadRequestException("ID produk tidak boleh kosong");
        }

        if (!StringUtils.hasText(produk.getNama())) {
            throw new BadRequestException("Nama produk tidak boleh kosong");
        }

        if (produk.getKategori() == null) {
            throw new BadRequestException("Kategori produk tidak boleh kosong");
        }

        if (!StringUtils.hasText(produk.getKategori().getId())) {
            throw new BadRequestException("Kategori ID tidak boleh kosong");
        }

        if (produk.getStok() < 1 || produk.getStok() == null) {
            throw new BadRequestException("Stok tidak boleh kosong");
        }

        if (produk.getHarga() == null || produk.getHarga().signum() < 1) {
            throw new BadRequestException("Harga tidak boleh kosong");
        }

        kategoriRepository.findById(produk.getKategori().getId())
                .orElseThrow(() -> new BadRequestException(
                        "Kategori ID " + produk.getKategori().getId() + " tidak ditemukan di database"));
        return produkRepository.save(produk);
    }

    public Produk ubahGambar(String id, String gambar) {
        Produk produk = findById(id);
        produk.setGambar(gambar);
        return produkRepository.save(produk);
    }

    public void deleteById(String id) {
        produkRepository.deleteById(id);
    }

}
