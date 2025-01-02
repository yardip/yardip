/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.model;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public enum JenisLaporanTransaksi {
    RUTIN("Rutin", "Administrator Entitas", "Administrator Yardip"),
    SERAH_TERIMA("Serah Terima", "Administrator Entitas", "Administrator Yardip"),
    REKAP_PENERIMAAN("Rekapitulasi Penerimaan", "Administrator Yardip"),
    REKAP_PENGELUARAN("Rekapitulasi Pengeluaran", "Administrator Yardip"),
    PROGRES_PENERIMAAN("Realisasi Penerimaan", "Administrator Yardip"),
    PROGRES_PENGELUARAN("Realisasi Pengeluaran", "Administrator Yardip"),
    REKAPITULASI_LABA("Rekapitulasi Penerimaan dan Pengeluaran", "Administrator Yardip"),
    REKAPITULASI("Rekapitulasi Laporan Keuangan", "Administrator Yardip");
    
    private final String label;
    
    private String[] roles;

    private JenisLaporanTransaksi(String label, String... roles) {
        this.label = label;
        this.roles = roles;
    }

    public String getLabel() {
        return label;
    }

    public String[] getRoles() {
        return roles;
    }
}
