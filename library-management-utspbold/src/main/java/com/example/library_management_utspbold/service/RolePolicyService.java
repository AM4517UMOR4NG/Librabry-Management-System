package com.example.library_management_utspbold.service;

import com.example.library_management_utspbold.dto.RolePolicy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provides a single source of truth for duties/permissions of each role.
 */
@Service
public class RolePolicyService {

    private final Map<String, RolePolicy> policies;

    public RolePolicyService() {
        policies = buildPolicies().stream()
                .collect(Collectors.toUnmodifiableMap(RolePolicy::roleName, rp -> rp));
    }

    public List<RolePolicy> getAllPolicies() {
        return List.copyOf(policies.values());
    }

    public RolePolicy getPolicy(String roleName) {
        return policies.get(roleName);
    }

    private static List<RolePolicy> buildPolicies() {
        return List.of(
                new RolePolicy(
                        "ADMIN",
                        "Full control over system configuration and master data",
                        List.of(
                                "Mengelola user & role (buat/hapus/ubah akun staff)",
                                "CRUD penuh untuk katalog buku",
                                "Atur kebijakan sistem (durasi pinjam, tarif denda, cut-off)",
                                "Lihat + unduh semua laporan & statistik",
                                "Konfigurasi integrasi (email, payment, branch)",
                                "Akses audit log / manajemen backup",
                                "Menghapus/soft-delete data (keputusan final)"
                        ),
                        List.of("BOOK:CRUD", "USER:CRUD", "REPORT:ALL", "CONFIG:UPDATE"),
                        List.of("Gunakan ROLE_ADMIN prefix sesuai Spring Security")
                ),
                new RolePolicy(
                        "LIBRARIAN",
                        "Staf operasional harian perpustakaan",
                        List.of(
                                "Proses peminjaman & pengembalian",
                                "Kelola reservasi (approve/fulfill/cancel)",
                                "Validasi kondisi fisik buku; catat kehilangan/kerusakan",
                                "Input & update stok/copy buku",
                                "Lakukan inventory & penempatan rak",
                                "Catat pembayaran denda",
                                "Bantu anggota (reset password, ajarkan penggunaan katalog)"
                        ),
                        List.of("LOAN:CREATE", "LOAN:RETURN", "RESERVATION:MANAGE", "BOOK:UPDATE", "TRANSACTION:CREATE"),
                        List.of("ROLE_LIBRARIAN cocok untuk @PreAuthorize hasAnyRole('ADMIN','LIBRARIAN')")
                ),
                new RolePolicy(
                        "MEMBER",
                        "Pengguna/peminjam buku",
                        List.of(
                                "Cari & lihat katalog buku",
                                "Ajukan peminjaman / kembalikan buku",
                                "Reservasi buku dan melihat posisi antrian",
                                "Lihat riwayat peminjaman & status denda sendiri",
                                "Update profil sendiri",
                                "Melakukan pembayaran denda (inisiasi)"
                        ),
                        List.of("BOOK:READ", "LOAN:CREATE_OWN", "RESERVATION:CREATE", "PROFILE:UPDATE"),
                        List.of("Gunakan SpEL @PreAuthorize untuk memastikan user hanya mengakses data miliknya")
                ),
                new RolePolicy(
                        "GUEST",
                        "Pengunjung publik hanya lihat katalog",
                        List.of("Lihat katalog tanpa login"),
                        List.of("BOOK:READ"),
                        List.of("Implementasi opsional via ROLE_GUEST")
                ),
                new RolePolicy(
                        "ACCOUNTANT",
                        "Akses laporan keuangan dan transaksi denda",
                        List.of("Review transaksi denda", "Kelola refund"),
                        List.of("REPORT:FINANCE", "TRANSACTION:READ"),
                        List.of()
                ),
                new RolePolicy(
                        "AUDITOR",
                        "Read-only untuk audit log & laporan",
                        List.of("Audit kepatuhan"),
                        List.of("REPORT:READ", "AUDIT:READ"),
                        List.of()
                ),
                new RolePolicy(
                        "SUPER_ADMIN",
                        "Pemilik sistem multi-tenant, Full elevated control",
                        List.of("Kelola tenant", "Override kebijakan global"),
                        List.of("SYSTEM:ALL"),
                        List.of("Gunakan hanya untuk kebutuhan khusus")
                )
        );
    }
}
