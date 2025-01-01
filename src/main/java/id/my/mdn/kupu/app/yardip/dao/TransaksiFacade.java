/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.model.JenisTransaksi;
import static id.my.mdn.kupu.app.yardip.model.JenisTransaksi.MUTASI_KAS;
import static id.my.mdn.kupu.app.yardip.model.JenisTransaksi.TRANSFER_SALDO;
import id.my.mdn.kupu.app.yardip.model.Kas;
import id.my.mdn.kupu.app.yardip.model.SaldoKas;
import id.my.mdn.kupu.app.yardip.model.Transaksi;
import id.my.mdn.kupu.app.yardip.model.TransaksiDetail;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class TransaksiFacade extends AbstractFacade<Transaksi> {

    @Inject
    private EntityManager em;

    public TransaksiFacade() {
        super(Transaksi.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... froms) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        switch (filterName) {
            case "businessEntity":
                return cb.equal(froms[0].get("businessEntity"), filterValue);
            case "jenisTransaksi":
                return cb.equal(froms[0].get("trxType").get("trxType"), (JenisTransaksi) filterValue);
            case "fromDate":
                return cb.greaterThanOrEqualTo(froms[0].<LocalDate>get("trxDate"), (LocalDate) filterValue);
            case "thruDate":
                return cb.lessThanOrEqualTo(froms[0].<LocalDate>get("trxDate"), (LocalDate) filterValue);
            default:
                return null;
        }
    }

    @Inject
    private TransaksiDetailFacade detailFacade;

    private void updateDetail(Transaksi transaksi) {
        List<TransaksiDetail> validDetails = new ArrayList<>();
        List<TransaksiDetail> invalidDetails = new ArrayList<>();

        for (TransaksiDetail detail : transaksi.getDetailTransaksi()) {
            if (detail.getTmpAmount().compareTo(BigDecimal.ZERO) > 0) {
                validDetails.add(detail);
            } else {
                invalidDetails.add(detail);
            }
        }

        for (TransaksiDetail detail : invalidDetails) {
            detail = detailFacade.find(detail.getId());
            if (detail != null) {
                detailFacade.remove(detail);
            }
        }

        for (TransaksiDetail detail : validDetails) {
            TransaksiDetail foundDetail = detailFacade.find(detail.getId());
            if (foundDetail != null) {
                foundDetail.setStatusMutasi(detail.getStatusMutasi());
                foundDetail.setAmount(detail.getTmpAmount());
                detailFacade.edit(detail);
            } else {
                detailFacade.create(detail);
            }
        }

        transaksi.setDetailTransaksi(validDetails);
    }

    private void updateAmount(Transaksi transaksi) {
        BigDecimal amount = BigDecimal.ZERO;
        for (TransaksiDetail detail : transaksi.getDetailTransaksi()) {
            JenisTransaksi jenisTransaksi = transaksi.getTrxType().getTrxType();
            if (!jenisTransaksi.equals(JenisTransaksi.MUTASI_KAS)) {
                detail.setAmount(detail.getTmpAmount().multiply(jenisTransaksi.getSign()));
            } else {
                detail.setAmount(detail.getTmpAmount().multiply(detail.getStatusMutasi().getSign()));
            }
            amount = amount.add(detail.getAmount());
        }
        transaksi.setAmount(amount);
    }

    @Override
    public Result<String> edit(Transaksi entity) {
        updateDetail(entity);
        updateAmount(entity);
        return super.edit(entity);
    }

    @Inject
    private BuktiKasSerialFacade serialFacade;

    @Override
    public Result<String> create(Transaksi entity) {
        updateAmount(entity);
        JenisTransaksi trxType = entity.getTrxType().getTrxType();
        if (trxType.equals(MUTASI_KAS)) {
            entity.setTrxDocId("PU");
        } else if (trxType.equals(TRANSFER_SALDO)) {
            entity.setTrxDocId(null);            
            entity.setCreated(LocalDateTime.of(entity.getTrxDate(), LocalTime.of(0, 0, 0, 0)));
        } else {
            String serial = serialFacade.getSerial(entity);
            entity.setTrxDocId(serial);
        }
        return super.create(entity);
    }

    @Inject
    private KasFacade kasFacade;

    public Transaksi copyFrom(Transaksi src) {

        Transaksi trx = new Transaksi();
        trx.setBusinessEntity(src.getBusinessEntity());
        trx.setTrxDate(LocalDate.now());
        trx.setTrxType(src.getTrxType());
        trx.setAmount(BigDecimal.ZERO);
        trx.setUraian(src.getUraian());
        trx.setTrxDocs(new ArrayList<>());

        List<TransaksiDetail> srcDetails = detailFacade
                .findAll(List.of(new FilterData("transaksi", src)));

        List<TransaksiDetail> trxDetails = kasFacade.findAll(
                List.of(new FilterData("owner", src.getBusinessEntity())),
                List.of(new IValueList.SorterData("name"))
        ).stream().map(kas -> {
            TransaksiDetail detail = new TransaksiDetail(trx, kas, BigDecimal.ZERO);
            for (TransaksiDetail srcDetail : srcDetails) {
                if (srcDetail.getKas().equals(kas)) {
                    detail.setStatusMutasi(srcDetail.getStatusMutasi());
                    detail.setAmount(srcDetail.getAmount());
                    break;
                }
            }
            return detail;
        })
                .collect(Collectors.toList());

        trx.setDetailTransaksi(trxDetails);

        return trx;
    }

    private static final String SALDO_KAS_SINGLE_QUERY
            = """
            SELECT KAS1.NAME AS NAME, KAS1.IDENTIFIER AS IDENTIFIER, TRXD1.AMOUNT AS AMOUNT
            FROM ( SELECT TRXD0.KAS_ID, SUM(TRXD0.AMOUNT) AS AMOUNT
                FROM (SELECT TRX0.ID
                    FROM YARDIP_TRANSAKSI TRX0
                    WHERE TRX0.BUSINESSENTITY_ID = ?
                    AND TRX0.TRXDATE >= ? AND TRX0.TRXDATE <= ?) AS TRX1
                JOIN YARDIP_TRANSAKSIDETAIL TRXD0
                ON TRX1.ID = TRXD0.TRANSAKSI_ID
                GROUP BY TRXD0.KAS_ID
            ) AS TRXD1
            JOIN (
                SELECT KAS0.ID, KAS0.NAME, KAS0.IDENTIFIER
                FROM YARDIP_KAS AS KAS0
                WHERE KAS0.OWNER_ID = ?
                AND (KAS0.FROMDATE <= ? AND ( KAS0.THRUDATE >= KAS0.FROMDATE OR KAS0.THRUDATE IS NULL ))
                AND KAS0.ID = ?
            ) AS KAS1
            ON TRXD1.KAS_ID = KAS1.ID
            ORDER BY KAS1.NAME, KAS1.ID            
            """;

    public SaldoKas calculateSaldoKas(BusinessEntity entity, AccountingPeriod period, Kas kas) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);
        String strFromDate = fmt.format(period.getFromDate());
        String strThruDate = fmt.format(period.getThruDate());

        Query query = getEntityManager().createNativeQuery(SALDO_KAS_SINGLE_QUERY, "SaldoKas");
        query.setParameter(1, entity.getId());
        query.setParameter(2, strFromDate);
        query.setParameter(3, strThruDate);
        query.setParameter(4, entity.getId());
        query.setParameter(5, strThruDate);
        query.setParameter(6, kas.getId());

        try {
            return (SaldoKas) query.getSingleResult();
        } catch (Exception ex) {
            return new SaldoKas(0L, kas.getName(), kas.getIdentifier(), BigDecimal.ZERO);
        }
    }

}
