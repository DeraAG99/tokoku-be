package com.dera.tokokube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dera.tokokube.entity.Pesanan;
import com.dera.tokokube.model.PesananRequest;
import com.dera.tokokube.model.PesananResponse;
import com.dera.tokokube.security.service.UserDetailsImpl;
import com.dera.tokokube.services.PesananService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class PesananController {
    @Autowired
    PesananService pesananService;

    @PostMapping("/pesanans")
    @PreAuthorize("hasAuthority('user')")
    public PesananResponse create(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody PesananRequest reqeust) {
        return pesananService.create(user.getUsername(), reqeust);
    }

    @PatchMapping("/pesanans/{pesananId}/cancel")
    @PreAuthorize("hasAuthority('user')")
    public Pesanan cancelPesananUser(@AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("pesananId") String pesananId) {
        return pesananService.cancelPesanan(pesananId, user.getUsername());
    }

    @PatchMapping("/pesanans/{pesananId}/terima")
    @PreAuthorize("hasAuthority('user')")
    public Pesanan terima(@AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("pesananId") String pesananId) {
        return pesananService.terimaPesanan(pesananId, user.getUsername());
    }

    @PatchMapping("/pesanans/{pesananId}/konfirmasi")
    @PreAuthorize("hasAuthority('admin')")
    public Pesanan konfirmasi(@AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("pesananId") String pesananId) {
        return pesananService.konfirmasiPembayaran(pesananId, user.getUsername());
    }

    @PatchMapping("/pesanans/{pesananId}/packing")
    @PreAuthorize("hasAuthority('admin')")
    public Pesanan packing(@AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("pesananId") String pesananId) {
        return pesananService.packing(pesananId, user.getUsername());
    }

    @PatchMapping("/pesanans/{pesananId}/kirim")
    @PreAuthorize("hasAuthority('admin')")
    public Pesanan kirim(@AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("pesananId") String pesananId) {
        return pesananService.kirim(pesananId, user.getUsername());
    }

    @GetMapping("/pesanans/admin")
    @PreAuthorize("hasAuthority('admin')")
    public List<Pesanan> findAllPesananAdmin(@AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(name = "filterText", defaultValue = "", required = false) String filterText,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {
        return pesananService.search(filterText, page, limit);
    }

    @GetMapping("/pesanans")
    @PreAuthorize("hasAuthority('user')")
    public List<Pesanan> findAllPesananUser(@AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {
        return pesananService.findAllPesananUser(user.getUsername(), page, limit);
    }
}