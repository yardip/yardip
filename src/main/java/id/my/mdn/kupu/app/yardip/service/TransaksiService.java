/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SingletonEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.service;

import id.my.mdn.kupu.app.yardip.dao.TransaksiFacade;
import id.my.mdn.kupu.app.yardip.model.JenisTransaksi;
import id.my.mdn.kupu.app.yardip.model.Kas;
import id.my.mdn.kupu.app.yardip.model.SaldoKas;
import id.my.mdn.kupu.app.yardip.model.StatusMutasiKas;
import id.my.mdn.kupu.app.yardip.model.Transaksi;
import id.my.mdn.kupu.app.yardip.model.TransaksiDetail;
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
        List<TransaksiDetail> details = transaksi.getDetailTransaksi();

        if (details == null) {
            return new Result<>(false, "Data tidak lengkap!");
        }

        int step = details.size();

        if (step == 0) {
            return new Result<>(false, "Data tidak lengkap!");
        }

        BusinessEntity businessEntity = transaksi.getBusinessEntity();

        AccountingPeriod period = periodFacade.findSingleByAttributes(List.of(FilterData.by("businessEntity", businessEntity),
                FilterData.by("date", transaksi.getTrxDate())
        )
        );

        BigDecimal sumDetail = BigDecimal.ZERO;

        Result<String> result = new Result<>(true, "Data telah disimpan!");

        synchronized (businessEntity.getId()) {

            for (TransaksiDetail detail : details) {

                JenisTransaksi jenisTransaksi = transaksi.getTrxType().getTrxType();

                if (jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)
                        && detail.getStatusMutasi() == null) {
                    result = new Result(false,
                            "Periksa kembali sumber dan tujuan mutasi!");
                    break;
                }

                BigDecimal detailAmount;

                if (!jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)) {
                    detailAmount = detail.getTmpAmount().multiply(jenisTransaksi.getSign());
                } else {
                    detailAmount = detail.getTmpAmount().multiply(detail.getStatusMutasi().getSign());
                }

                sumDetail = sumDetail.add(detailAmount);

                SaldoKas saldoKas = dao.calculateSaldoKas(businessEntity, period, detail.getKas());

                if (step == 1 && jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)
                        && (sumDetail.compareTo(BigDecimal.ZERO) != 0)) {
                    result = new Result(false,
                            "Periksa kembali besaran sumber dan tujuan mutasi!");
                    break;
                }

                if (jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)
                        && detail.getStatusMutasi().equals(StatusMutasiKas.DESTINATION)) {
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
        List<TransaksiDetail> details = transaksi.getDetailTransaksi();

        if (details == null) {
            return new Result<>(false, "Data tidak lengkap!");
        }

        int step = details.size();

        if (step == 0) {
            return new Result<>(false, "Data tidak lengkap!");
        }

        BusinessEntity businessEntity = transaksi.getBusinessEntity();

        AccountingPeriod period = periodFacade.findSingleByAttributes(List.of(FilterData.by("businessEntity", businessEntity),
                FilterData.by("date", transaksi.getTrxDate())
        )
        );

        BigDecimal sumDetail = BigDecimal.ZERO;

        Result<String> result = new Result<>(true, "Data telah disimpan!");

        synchronized (businessEntity.getId()) {

            for (TransaksiDetail detail : transaksi.getDetailTransaksi()) {

                JenisTransaksi jenisTransaksi = transaksi.getTrxType().getTrxType();

                if (jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)
                        && (detail.getTmpAmount().compareTo(BigDecimal.ZERO) != 0)
                        && detail.getStatusMutasi() == null) {
                    result = new Result(false,
                            "Periksa kembali sumber dan tujuan mutasi!");
                    break;
                }

                BigDecimal detailAmount;

                if (!jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)) {
                    detailAmount = detail.getTmpAmount().multiply(jenisTransaksi.getSign());
                } else {
                    if (detail.getTmpAmount().compareTo(BigDecimal.ZERO) != 0) {
                        detailAmount = detail.getTmpAmount().multiply(detail.getStatusMutasi().getSign());
                    } else {
                        detailAmount = detail.getTmpAmount();
                    }
                }

                sumDetail = sumDetail.add(detailAmount);

                SaldoKas saldoKas = dao.calculateSaldoKas(businessEntity, period, detail.getKas());

                if (step == 1 && jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)
                        && (sumDetail.compareTo(BigDecimal.ZERO) != 0)) {
                    result = new Result(false,
                            "Periksa kembali besaran sumber dan tujuan mutasi!");
                    break;
                }

                if (jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)
                        && ((detail.getTmpAmount().compareTo(BigDecimal.ZERO) == 0)
                        || detail.getStatusMutasi().equals(StatusMutasiKas.DESTINATION))) {
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
