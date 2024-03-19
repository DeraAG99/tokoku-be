package com.dera.tokokube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dera.tokokube.entity.Keranjang;
import com.dera.tokokube.model.KeranjangRequest;
import com.dera.tokokube.security.service.UserDetailsImpl;
import com.dera.tokokube.services.KeranjangService;
import com.dera.tokokube.exceptions.BadRequestException;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class KeranjangController {

    @Autowired
    private KeranjangService keranjangService;

    @GetMapping("/keranjangs")
    public List<Keranjang> findByPenggunaId(@AuthenticationPrincipal UserDetailsImpl user) {
        return keranjangService.findByPenggunaId(user.getUsername());
    }

    @PostMapping("/keranjangs")
    public Keranjang create(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody KeranjangRequest request)
            throws BadRequestException {
        return keranjangService.addKeranjang(user.getUsername(), request.getProdukId(), request.getKuantitas());
    }

    @PatchMapping("/keranjangs/{produkId}")
    public Keranjang update(@AuthenticationPrincipal UserDetailsImpl user, @PathVariable("produkId") String produkId,
            @RequestParam("kuantitas") Double kuantitas) throws BadRequestException {
        return keranjangService.updateKuantitas(user.getUsername(), produkId, kuantitas);
    }

    @DeleteMapping("/keranjangs/{produkId}")
    public void delete(@AuthenticationPrincipal UserDetailsImpl user, @PathVariable("produkId") String produkId)
            throws BadRequestException {
        keranjangService.delete(user.getUsername(), produkId);
    }
}
