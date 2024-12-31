/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SingletonEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.service;

import id.my.mdn.kupu.app.yardip.dao.TransaksiFacade;
import id.my.mdn.kupu.app.yardip.entity.JenisTransaksi;
import id.my.mdn.kupu.app.yardip.entity.Kas;
import id.my.mdn.kupu.app.yardip.entity.SaldoKas;
import id.my.mdn.kupu.app.yardip.entity.Transaksi;
import id.my.mdn.kupu.app.yardip.entity.TransaksiDetail;
import id.my.mdn.kupu.core.accounting.dao.AccountingPeriodFacade;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@LocalBean
public class TransaksiService {

    @Inject
    private TransaksiFacade dao;    

    @Inject
    private AccountingPeriodFacade periodFacade;

    public Result<String> create(Transaksi transaksi) {
        BusinessEntity businessEntity = transaksi.getBusinessEntity();

        AccountingPeriod period = periodFacade.findSingleByAttributes(List.of(FilterData.by("businessEntity", businessEntity),
                FilterData.by("date", transaksi.getTrxDate())
        )
        );

        BigDecimal sumDetail = BigDecimal.ZERO;
        int step = transaksi.getDetailTransaksi().size();

        Result<String> result = new Result<>(true, "Data telah disimpan!");

        synchronized (businessEntity.getId()) {

            for (TransaksiDetail detail : transaksi.getDetailTransaksi()) {

                BigDecimal detailAmount;

                JenisTransaksi jenisTransaksi = transaksi.getTrxType().getTrxType();

                if (!jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)) {
                    detailAmount = detail.getTmpAmount().multiply(jenisTransaksi.getSign());
                } else {
                    detailAmount = detail.getTmpAmount().multiply(detail.getStatusMutasi().getSign());
                }

                sumDetail = sumDetail.add(detailAmount);

                SaldoKas saldoKas = dao.calculateSaldoKas(businessEntity, period, detail.getKas());

                if (step == 1 && jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS) && (sumDetail.compareTo(BigDecimal.ZERO) != 0)) {
                    result = new Result(false,
                            "Periksa kas sumber dan tujuan!");
                    break;
                }

                if (saldoKas.getAmount().add(detailAmount).compareTo(BigDecimal.ZERO) < 0) {
                    Kas kas = detail.getKas();
                    result = new Result(false,
                            "Saldo "
                            + kas.getName()
                            + ((kas.getIdentifier() != null && !kas.getIdentifier().isEmpty()) ? ("-" + kas.getIdentifier()) : "")
                            + " tidak cukup !");
                    break;
                }

                step--;
            }

            if (result.isSuccess()) {
                dao.create(transaksi);
            }
        }

        return result;
    }

    public Result<String> edit(Transaksi transaksi) {
        BusinessEntity businessEntity = transaksi.getBusinessEntity();

        AccountingPeriod period = periodFacade.findSingleByAttributes(List.of(FilterData.by("businessEntity", businessEntity),
                FilterData.by("date", transaksi.getTrxDate())
        )
        );

        BigDecimal sumDetail = BigDecimal.ZERO;
        int step = transaksi.getDetailTransaksi().size();

        Result<String> result = new Result<>(true, "Data telah disimpan!");

        synchronized (businessEntity.getId()) {

            for (TransaksiDetail detail : transaksi.getDetailTransaksi()) {

                BigDecimal detailAmount = BigDecimal.ZERO;

                JenisTransaksi jenisTransaksi = transaksi.getTrxType().getTrxType();

                if (!jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)) {
                    detailAmount = detail.getTmpAmount().multiply(jenisTransaksi.getSign());
                } else {
                    if (detail.getTmpAmount().compareTo(BigDecimal.ZERO) != 0) {
                        detailAmount = detail.getTmpAmount().multiply(detail.getStatusMutasi().getSign());
                    }
                }

                sumDetail = sumDetail.add(detailAmount);

                SaldoKas saldoKas = dao.calculateSaldoKas(businessEntity, period, detail.getKas());

                if (step == 1 && jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS) && (sumDetail.compareTo(BigDecimal.ZERO) != 0)) {
                    result = new Result(false,
                            "Periksa kas sumber dan tujuan!");
                    break;
                }

                if (saldoKas.getAmount().add(detailAmount).compareTo(BigDecimal.ZERO) < 0) {
                    Kas kas = detail.getKas();
                    result = new Result(false,
                            "Saldo "
                            + kas.getName()
                            + ((kas.getIdentifier() != null && !kas.getIdentifier().isEmpty()) ? ("-" + kas.getIdentifier()) : "")
                            + " tidak cukup !");
                    break;
                }

                step--;
            }

            if (result.isSuccess()) {
                dao.edit(transaksi);
                
            }
        }

        return result;
    }
}
