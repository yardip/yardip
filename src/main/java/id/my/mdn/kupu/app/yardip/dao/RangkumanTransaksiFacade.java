/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.dao;

import id.my.mdn.kupu.app.yardip.model.JenisTransaksi;
import static id.my.mdn.kupu.app.yardip.model.JenisTransaksi.TRANSFER_SALDO;
import id.my.mdn.kupu.app.yardip.model.Kas;
import id.my.mdn.kupu.app.yardip.model.PosTransaksi;
import id.my.mdn.kupu.app.yardip.model.RangkumanTransaksi;
import id.my.mdn.kupu.app.yardip.model.RangkumanTransaksi.KasAmount;
import id.my.mdn.kupu.app.yardip.model.SaldoKas;
import id.my.mdn.kupu.app.yardip.model.Transaksi;
import id.my.mdn.kupu.app.yardip.model.TransaksiDetail;
import id.my.mdn.kupu.core.accounting.dao.AccountingPeriodFacade;
import id.my.mdn.kupu.core.accounting.entity.AccountingPeriod;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
@LocalBean
public class RangkumanTransaksiFacade extends AbstractSqlFacade<Object[]> {

    private static final String BASIC_QUERY
            = """
              SELECT TRX0.ID, TRX0.TRXDATE, TRX0.URAIAN, TRX0.TRXDOCID,
                     TRX0.AMOUNT<?>
              FROM <?> AS TRX0
              JOIN (
                  SELECT TRX1.ID<?>
                  FROM <?> AS TRX1
                  JOIN (
                      SELECT TRXD0.TRANSAKSI_ID, KAS1.ID AS KAS_ID, KAS1.KAS_NAME, TRXD0.AMOUNT
                      FROM (
                          SELECT KAS0.ID, KAS0.NAME AS KAS_NAME
                          FROM YARDIP_KAS AS KAS0
                          WHERE KAS0.OWNER_ID = ?
                          AND (KAS0.FROMDATE <= ? AND ( KAS0.THRUDATE >= KAS0.FROMDATE OR KAS0.THRUDATE IS NULL ))
                      ) AS KAS1
                      JOIN YARDIP_TRANSAKSIDETAIL AS TRXD0
                      ON KAS1.ID = TRXD0.KAS_ID
                  ) AS TRXD1
                  ON TRX1.ID = TRXD1.TRANSAKSI_ID
                  WHERE TRX1.BUSINESSENTITY_ID = ? AND TRX1.TRXDATE >= ? AND TRX1.TRXDATE <= ?
                  GROUP BY TRX1.ID
              ) AS TRXD2
              ON TRX0.ID = TRXD2.ID
              """;

    private static final String SELECTOR__STR
            = """
              TRXD2.KAS<?>
              """;

    private static final String TRXTYPE_FILTER
            = """
              (
                  SELECT TRX00.ID,TRX00.BUSINESSENTITY_ID,TRX00.TRXDATE,TRX00.URAIAN,TRX00.TRXDOCID,TRX00.AMOUNT,TRX00.CREATED
                  FROM YARDIP_TRANSAKSI TRX00
                  JOIN YARDIP_POSTRANSAKSI PTRX00
                  ON TRX00.TRXTYPE_ID = PTRX00.ID
                  WHERE PTRX00.TRXTYPE = ?
              )
              """;

    private static final String TOTAL_BASIC_QUERY
            = """
              SELECT SUM (CASE WHEN TRXD3.AMOUNT > 0 THEN TRXD3.AMOUNT ELSE 0 END) AS AMOUNT_DEBIT,
                     SUM (CASE WHEN TRXD3.AMOUNT <= 0 THEN TRXD3.AMOUNT ELSE 0 END) AS AMOUNT_CREDIT
                     <?>
              FROM ( SELECT TRX0.ID, TRX0.TRXDATE, TRX0.URAIAN, TRX0.TRXDOCID,
                            TRX0.AMOUNT<?>
                  FROM <?> AS TRX0
                  JOIN (
                      SELECT TRX1.ID<?>
                      FROM <?> AS TRX1
                      JOIN (
                          SELECT TRXD0.TRANSAKSI_ID, KAS1.ID AS KAS_ID, KAS1.KAS_NAME, TRXD0.AMOUNT
                          FROM (
                              SELECT KAS0.ID, KAS0.NAME AS KAS_NAME
                              FROM YARDIP_KAS AS KAS0
                              WHERE KAS0.OWNER_ID = ?
                              AND (KAS0.FROMDATE <= ? AND ( KAS0.THRUDATE >= KAS0.FROMDATE OR KAS0.THRUDATE IS NULL ))
                          ) AS KAS1
                          JOIN YARDIP_TRANSAKSIDETAIL AS TRXD0
                          ON KAS1.ID = TRXD0.KAS_ID
                      ) AS TRXD1
                      ON TRX1.ID = TRXD1.TRANSAKSI_ID
                      WHERE TRX1.BUSINESSENTITY_ID = ? AND TRX1.TRXDATE >= ? AND TRX1.TRXDATE <= ?
                      GROUP BY TRX1.ID
                  ) AS TRXD2
                  ON TRX0.ID = TRXD2.ID
              ) AS TRXD3
              """;

    private static final String CASE_STR
            = """
              SUM (CASE WHEN TRXD1.KAS_NAME = '<?>' THEN TRXD1.AMOUNT ELSE 0 END) AS KAS<?>
              """;

    private static final String TOTAL_CASE_STR
            = """
              SUM (CASE WHEN TRXD3.KAS<?> > 0 THEN TRXD3.KAS<?> ELSE 0 END) AS KAS<?>_DEBIT,
              SUM (CASE WHEN TRXD3.KAS<?> <= 0 THEN TRXD3.KAS<?> ELSE 0 END) AS KAS<?>_CREDIT
              """;

    @Inject
    private AccountingPeriodFacade periodFacade;

    @Inject
    private PosTransaksiFacade posFacade;

    @Inject
    private EntityManager em;

    public RangkumanTransaksiFacade() {
        super(Object[].class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected String getFindAllQuery() {
        return BASIC_QUERY;
    }

    public String generateQuery(List<String> listKasName, JenisTransaksi trxType) {
        StringBuilder selectorStrBuilder = new StringBuilder();
        StringBuilder caseStrBuilder = new StringBuilder();

        for (int i = 0; i < listKasName.size();) {
            String aSelectorStr = replaceParameterizedPositions(SELECTOR__STR, String.valueOf(i));
            selectorStrBuilder.append(aSelectorStr);

            String aCaseStr = replaceParameterizedPositions(CASE_STR,
                    listKasName.get(i),
                    String.valueOf(i)
            );
            caseStrBuilder.append(aCaseStr);

            if (++i < listKasName.size()) {
                selectorStrBuilder.append(",");
                caseStrBuilder.append(",");
            }
        }

        String trxTypeStr = trxType != null ? TRXTYPE_FILTER : "YARDIP_TRANSAKSI";
        String selectorStr = selectorStrBuilder.isEmpty() ? "" : selectorStrBuilder.insert(0, ",").toString();
        String caseStr = caseStrBuilder.isEmpty() ? "" : caseStrBuilder.insert(0, ",").toString();

        return replaceParameterizedPositions(BASIC_QUERY, selectorStr, trxTypeStr, caseStr, trxTypeStr);
    }

    public String generateTotalQuery(List<String> listKasName, JenisTransaksi trxType) {
        StringBuilder selectorStrBuilder = new StringBuilder();
        StringBuilder caseStrBuilder = new StringBuilder();
        StringBuilder totalCaseStrBuilder = new StringBuilder();

        for (int i = 0; i < listKasName.size();) {
            String aSelectorStr = replaceParameterizedPositions(SELECTOR__STR, String.valueOf(i));
            selectorStrBuilder.append(aSelectorStr);

            String strIdx = String.valueOf(i);

            String aCaseStr = replaceParameterizedPositions(
                    CASE_STR, listKasName.get(i), strIdx
            );
            caseStrBuilder.append(aCaseStr);

            String aTotalCaseSelector = replaceParameterizedPositions(TOTAL_CASE_STR,
                    strIdx, strIdx, strIdx,
                    strIdx, strIdx, strIdx
            );

            totalCaseStrBuilder.append(aTotalCaseSelector);

            if (++i < listKasName.size()) {
                selectorStrBuilder.append(",");
                caseStrBuilder.append(",");
                totalCaseStrBuilder.append(",");
            }
        }

        String trxTypeStr = trxType != null ? TRXTYPE_FILTER : "YARDIP_TRANSAKSI";
        String selectorStr = selectorStrBuilder.isEmpty() ? "" : selectorStrBuilder.insert(0, ",").toString();
        String caseStr = caseStrBuilder.isEmpty() ? "" : caseStrBuilder.insert(0, ",").toString();
        String totalCaseStr = totalCaseStrBuilder.isEmpty() ? "" : totalCaseStrBuilder.insert(0, ",").toString();

        return replaceParameterizedPositions(TOTAL_BASIC_QUERY,
                totalCaseStr, selectorStr, trxTypeStr,
                caseStr, trxTypeStr);
    }

    public RangkumanTransaksi caculateTotal(BusinessEntity entity, AccountingPeriod period, JenisTransaksi trxType) {

        List<String> listKasName = kasFacade.findDistinctKasByName(entity, period);

        String strQuery = generateTotalQuery(listKasName, trxType);

        Query query = em.createNativeQuery(strQuery);

        if (trxType != null) {
            setParameters(query, Map.of("entity", entity, "period", period, "trxType", trxType));
        } else {
            setParameters(query, Map.of("entity", entity, "period", period));
        }

        List<Object[]> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return null;
        }

        Object[] result = resultList.get(0);

        RangkumanTransaksi rangkumanTransaksi = new RangkumanTransaksi("0");
        rangkumanTransaksi.setUraian("Total");
        rangkumanTransaksi.setAmountDebit((BigDecimal) result[0]);
        rangkumanTransaksi.setAmountCredit((BigDecimal) result[1]);

        List<KasAmount> listKasAmount = new ArrayList<>();

        for (int i = 2; i < result.length;) {
            listKasAmount.add(new KasAmount((BigDecimal) result[i++], (BigDecimal) result[i++]));
        }

        rangkumanTransaksi.setListKasAmount(listKasAmount);

        return rangkumanTransaksi;
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        JenisTransaksi jenisTransaksi = (JenisTransaksi) parameters.get("trxType");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);
        AccountingPeriod period = (AccountingPeriod) parameters.get("period");
        BusinessEntity entity = ((BusinessEntity) parameters.get("entity"));

        if (jenisTransaksi != null) {
            q.setParameter(1, jenisTransaksi.name());
            q.setParameter(2, jenisTransaksi.name());
            q.setParameter(3, entity.getId());
            q.setParameter(4, fmt.format(period.getFromDate()));
            q.setParameter(5, entity.getId());
            q.setParameter(6, fmt.format(period.getFromDate()));
            q.setParameter(7, fmt.format(period.getThruDate()));
        } else {
            q.setParameter(1, entity.getId());
            q.setParameter(2, fmt.format(period.getFromDate()));
            q.setParameter(3, entity.getId());
            q.setParameter(4, fmt.format(period.getFromDate()));
            q.setParameter(5, fmt.format(period.getThruDate()));
        }
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        switch (filterData.name) {
            case "id":
                return "TRX0." + filterData.name + " = '" + filterData.value + "'";
            default:
                return null;
        }
    }

    @Override
    protected String translateOrderField(String fieldName) {
        return "trx0." + fieldName;
    }

    private static final String BASE_SALDO_CALC
            = """
            SELECT SUM(TRX0.AMOUNT) AS AMOUNT, <?>
            FROM YARDIP_TRANSAKSI AS TRX0
            JOIN (
                SELECT TRX1.ID, <?>
                FROM YARDIP_TRANSAKSI AS TRX1
                JOIN (
                    SELECT TRXD0.TRANSAKSI_ID, KAS1.ID AS KAS_ID, KAS1.KAS_NAME, TRXD0.AMOUNT
                    FROM (
                        SELECT KAS0.ID, KAS0.NAME AS KAS_NAME
                        FROM YARDIP_KAS AS KAS0
                        WHERE KAS0.OWNER_ID = ?
                        AND (KAS0.FROMDATE <= ? AND ( KAS0.THRUDATE >= KAS0.FROMDATE OR KAS0.THRUDATE IS NULL ))
                    ) AS KAS1
                    JOIN YARDIP_TRANSAKSIDETAIL AS TRXD0
                    ON KAS1.ID = TRXD0.KAS_ID
                ) AS TRXD1
                ON TRX1.ID = TRXD1.TRANSAKSI_ID
                WHERE TRX1.BUSINESSENTITY_ID = ? AND TRX1.TRXDATE >= ? AND TRX1.TRXDATE <= ?
                GROUP BY TRX1.ID
            ) AS TRXD2
            ON TRX0.ID = TRXD2.ID
            GROUP BY TRX0.BUSINESSENTITY_ID
            """;

    private static final String SUM_SALDO_STR
            = """
              SUM(TRXD2.KAS<?>)
              """;

    private static final String CASE_SALDO_STR
            = """
              SUM (CASE WHEN TRXD1.KAS_ID = <?> THEN TRXD1.AMOUNT ELSE 0 END) AS KAS<?>
              """;

    public String generateSumQuery(List<Kas> listKas) {

        StringBuilder selectorStrBuilder = new StringBuilder();
        StringBuilder caseStrBuilder = new StringBuilder();

        for (int i = 0; i < listKas.size();) {
            String aSelectorStr = replaceParameterizedPositions(
                    SUM_SALDO_STR,
                    String.valueOf(listKas.get(i).getId().toString()
                    ));
            selectorStrBuilder.append(aSelectorStr);

            String aCaseStr = replaceParameterizedPositions(CASE_SALDO_STR,
                    listKas.get(i).getId().toString(),
                    listKas.get(i).getId().toString()
            );
            caseStrBuilder.append(aCaseStr);

            if (++i < listKas.size()) {
                selectorStrBuilder.append(",");
                caseStrBuilder.append(",");
            }
        }

        String selectorStr = selectorStrBuilder.toString();
        String caseStr = caseStrBuilder.toString();

        return replaceParameterizedPositions(BASE_SALDO_CALC, selectorStr, caseStr);
    }

    @Inject
    private KasFacade kasFacade;

    @Inject
    private TransaksiFacade transaksiFacade;

    @Inject
    private TransaksiDetailFacade transaksiDetailFacade;

    public void calculateTransferSaldo(BusinessEntity entity, AccountingPeriod currentPeriod) {

        List<Kas> listCurrentKas = kasFacade.findAll(
                List.of(
                        FilterData.by("owner", entity),
                        FilterData.by("date", currentPeriod.getThruDate())
                ),
                List.of(new SorterData("id"))
        );

        Transaksi transferSaldo = transaksiFacade.findSingleByAttributes(List.of(
                FilterData.by("businessEntity", entity),
                FilterData.by("jenisTransaksi", JenisTransaksi.TRANSFER_SALDO),
                FilterData.by("fromDate", currentPeriod.getFromDate()),
                FilterData.by("thruDate", currentPeriod.getThruDate())
        ));

//        Transaksi transferSaldo;
        List<TransaksiDetail> transferSaldoDetails;

        boolean isNew = false;

        if (transferSaldo == null) {
            isNew = true;
            transferSaldo = new Transaksi();

            transferSaldo.setBusinessEntity(entity);
            transferSaldo.setTrxDate(currentPeriod.getFromDate());

            PosTransaksi posTransferSaldo = posFacade.findSingleByAttributes(
                    List.of(
                            FilterData.by("entity", entity),
                            FilterData.by("trxType", TRANSFER_SALDO)
                    )
            );

            transferSaldo.setTrxType(posTransferSaldo);

            transferSaldo.setUraian("Sisa bulan lalu");
            transferSaldo.setAmount(BigDecimal.ZERO);

            transferSaldoDetails = new ArrayList<>();

            for (Kas kas : listCurrentKas) {
                TransaksiDetail detail = new TransaksiDetail();
                detail.setTransaksi(transferSaldo);
                detail.setKas(kas);
                detail.setAmount(BigDecimal.ZERO);

                transferSaldoDetails.add(detail);
            }

            transferSaldo.setDetailTransaksi(transferSaldoDetails);
        } else {
            transferSaldoDetails = transaksiDetailFacade.findAll(
                    List.of(new FilterData("transaksi", transferSaldo)),
                    List.of(new SorterData("kas"))
            );
        }

        AccountingPeriod prevPeriod = periodFacade.getPreviousPeriod(entity, currentPeriod);

        if (prevPeriod == null) {
            return;
        }

        List<Kas> listPreviousKas = kasFacade.findAll(
                List.of(
                        FilterData.by("owner", entity),
                        FilterData.by("date", prevPeriod.getThruDate())
                ),
                List.of(new SorterData("id"))
        );

        // Compare previous vs current kas
//        List<Integer> missingKasIdx = new ArrayList<>();
//        for (int i = 0; i < listPreviousKas.size(); i++) {
//            Kas prevKas = listPreviousKas.get(i);
//            if (!listCurrentKas.contains(prevKas)) {
//                missingKasIdx.add(i);
//            }
//        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("entity", entity);
        parameters.put("period", prevPeriod);

        List<Object[]> sumsOfPreviousKas = findAll(() -> generateSumQuery(listPreviousKas),
                0, 0, parameters,
                null, null, null, null);

        if (sumsOfPreviousKas != null && !sumsOfPreviousKas.isEmpty() && sumsOfPreviousKas.size() == 1) {
            Object[] sums = sumsOfPreviousKas.get(0);

            transferSaldo.setAmount((BigDecimal) sums[0]);

            for (int i = 1; i < sums.length; i++) {
                if ((i - 1) < transferSaldoDetails.size()) {
                    transferSaldoDetails.get(i - 1).setTmpAmount((BigDecimal) sums[i]);
                } else {
                    transferSaldoDetails.add(new TransaksiDetail(transferSaldo, listPreviousKas.get(i - 1), (BigDecimal) sums[i]));
                }
            }
        } else {
            return;
        }

        if (isNew) {
            transaksiFacade.create(transferSaldo);
        } else {
            transaksiFacade.edit(transferSaldo);
            transaksiDetailFacade.edit(transferSaldoDetails);
        }
    }

    private static final String SALDO_KAS_QUERY
            = """
            SELECT KAS1.ID, KAS1.NAME AS NAME, KAS1.IDENTIFIER AS IDENTIFIER, TRXD1.AMOUNT AS AMOUNT
            FROM ( SELECT TRXD0.KAS_ID, SUM(TRXD0.AMOUNT) AS AMOUNT
                FROM (SELECT TRX0.ID
                    FROM YARDIP_TRANSAKSI TRX0
                    WHERE TRX0.BUSINESSENTITY_ID = <?>
                    AND TRX0.TRXDATE >= '<?>' AND TRX0.TRXDATE <= '<?>') AS TRX1
                JOIN YARDIP_TRANSAKSIDETAIL TRXD0
                ON TRX1.ID = TRXD0.TRANSAKSI_ID
                GROUP BY TRXD0.KAS_ID
            ) AS TRXD1
            JOIN (
                SELECT KAS0.ID, KAS0.NAME, KAS0.IDENTIFIER
                FROM YARDIP_KAS AS KAS0
                WHERE KAS0.OWNER_ID = <?>
                AND (KAS0.FROMDATE <= '<?>' AND ( KAS0.THRUDATE >= KAS0.FROMDATE OR KAS0.THRUDATE IS NULL ))
            ) AS KAS1
            ON TRXD1.KAS_ID = KAS1.ID
            ORDER BY KAS1.NAME, KAS1.ID           
            """;

    private static final String SALDO_KAS_SINGLE_QUERY
            = """
            SELECT KAS1.NAME AS NAME, KAS1.IDENTIFIER AS IDENTIFIER, TRXD1.AMOUNT AS AMOUNT
            FROM ( SELECT TRXD0.KAS_ID, SUM(TRXD0.AMOUNT) AS AMOUNT
                FROM (SELECT TRX0.ID
                    FROM YARDIP_TRANSAKSI TRX0
                    WHERE TRX0.BUSINESSENTITY_ID = <?>
                    AND TRX0.TRXDATE >= '<?>' AND TRX0.TRXDATE <= '<?>') AS TRX1
                JOIN YARDIP_TRANSAKSIDETAIL TRXD0
                ON TRX1.ID = TRXD0.TRANSAKSI_ID
                GROUP BY TRXD0.KAS_ID
            ) AS TRXD1
            JOIN (
                SELECT KAS0.ID, KAS0.NAME, KAS0.IDENTIFIER
                FROM YARDIP_KAS AS KAS0
                WHERE KAS0.OWNER_ID = <?>
                AND (KAS0.FROMDATE <= '<?>' AND ( KAS0.THRUDATE >= KAS0.FROMDATE OR KAS0.THRUDATE IS NULL ))
                AND KAS0.ID = <?>
            ) AS KAS1
            ON TRXD1.KAS_ID = KAS1.ID
            ORDER BY KAS1.NAME, KAS1.ID           
            """;

    public List<SaldoKas> calculateSaldoKas(BusinessEntity entity, AccountingPeriod period) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);
        String strFromDate = fmt.format(period.getFromDate());
        String strThruDate = fmt.format(period.getThruDate());

        String queryString = replaceParameterizedPositions(
                SALDO_KAS_QUERY,
                String.valueOf(entity.getId()),
                strFromDate, strThruDate,
                String.valueOf(entity.getId()), strThruDate
        );

        Query query = getEntityManager().createNativeQuery(queryString, "SaldoKas");

        return query.getResultList();
    }

    public List<SaldoKas> calculateSaldoKas(BusinessEntity entity, AccountingPeriod period, Kas kas) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);
        String strFromDate = fmt.format(period.getFromDate());
        String strThruDate = fmt.format(period.getThruDate());

        String queryString = replaceParameterizedPositions(
                SALDO_KAS_SINGLE_QUERY,
                String.valueOf(entity.getId()),
                strFromDate, strThruDate,
                String.valueOf(entity.getId()), strThruDate,
                String.valueOf(kas.getId())
        );

        Query query = getEntityManager().createNativeQuery(queryString, "SaldoKas");

        return query.getResultList();
    }

    public List<SaldoKas> calculateDistinctSaldoKas(BusinessEntity entity, AccountingPeriod period) {
        Map<String, Integer> kasIndex = new HashMap<>();
        List<SaldoKas> listDistinctSaldoKas = new ArrayList<>();

        List<SaldoKas> listSaldoKas = calculateSaldoKas(entity, period);

        listSaldoKas.stream().forEach(saldo -> {
            if (!kasIndex.containsKey(saldo.getName())) {
                int idx = listDistinctSaldoKas.size();
                kasIndex.put(saldo.getName(), idx);
                listDistinctSaldoKas.add(new SaldoKas(saldo.getId(), saldo.getName(), null, saldo.getAmount()));
            } else {
                SaldoKas saldoKas = listDistinctSaldoKas.get(kasIndex.get(saldo.getName()));
                saldoKas.setAmount(saldoKas.getAmount().add(saldo.getAmount()));
            }

        });

        return listSaldoKas;
    }

    public void remove(RangkumanTransaksi entity) {
        Transaksi trx = transaksiFacade.find(entity.getId());
        transaksiFacade.remove(trx);
    }

}
