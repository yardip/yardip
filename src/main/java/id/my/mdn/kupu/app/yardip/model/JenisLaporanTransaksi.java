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
    RUTIN("Rutin"),
    SERAH_TERIMA("Serah Terima"),
    REKAP_PENERIMAAN("Rekapitulasi Penerimaan"),
    REKAP_PENGELUARAN("Rekapitulasi Pengeluaran"),
    PROGRES_PENERIMAAN("Realisasi Penerimaan"),
    PROGRES_PENGELUARAN("Realisasi Pengeluaran"),
    REKAPITULASI_LABA("Rekapitulasi Penerimaan dan Pengeluaran"),
    REKAPITULASI("Rekapitulasi Laporan Keuangan");
    
    private final String label;

    private JenisLaporanTransaksi(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
