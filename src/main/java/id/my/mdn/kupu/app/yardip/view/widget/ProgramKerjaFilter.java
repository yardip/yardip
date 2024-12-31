/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.view.widget;

import id.my.mdn.kupu.app.yardip.entity.JenisTransaksi;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Named(value = "programKerjaFilter")
@Dependent
public class ProgramKerjaFilter extends FilterContent implements Serializable {
    
    @Bookmark(name = "tp")
    private JenisTransaksi jenisTransaksi;

    public JenisTransaksi getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(JenisTransaksi jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }
}
