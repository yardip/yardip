/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class RangkumanTransaksi implements Serializable {

    public static class KasAmount {

        private final BigDecimal debit;

        private final BigDecimal credit;

        public KasAmount(BigDecimal debit, BigDecimal credit) {
            this.debit = debit;
            this.credit = credit;
        }

        public BigDecimal getDebit() {
            return debit;
        }

        public BigDecimal getCredit() {
            return credit;
        }
    }

    private final String id;

    private LocalDate trxDate;

    private String uraian;

    private String trxDocId;

    private BigDecimal amountDebit;

    private BigDecimal amountCredit;

    private List<KasAmount> listKasAmount;
    
    public List<BigDecimal> getKasValues() {
        List<BigDecimal> kasValues = new ArrayList<>();
        
        for(KasAmount kasAmount : listKasAmount) {
            kasValues.add(kasAmount.getDebit());
            kasValues.add(kasAmount.getCredit());
        }
        
        return kasValues;
    }

    public RangkumanTransaksi(String id) {
        this.id = id;
    }

    public RangkumanTransaksi(String id, LocalDate trxDate, String uraian,
            String trxDocId, BigDecimal amount, BigDecimal... listKas) {
        this.id = id;
        this.trxDate = trxDate;
        this.uraian = uraian;
        this.trxDocId = trxDocId;

        if (amount == null) {
            this.amountDebit = BigDecimal.ZERO;
            this.amountCredit = BigDecimal.ZERO;
        } else if (amount.compareTo(BigDecimal.ZERO) > 0) {
            this.amountDebit = amount;
            this.amountCredit = BigDecimal.ZERO;
        } else if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            this.amountDebit = BigDecimal.ZERO;
            this.amountCredit = amount;
        }

        this.listKasAmount = new ArrayList<>();

        for (BigDecimal kasAmount : listKas) {
            if (kasAmount.compareTo(BigDecimal.ZERO) > 0) {
                listKasAmount.add(new KasAmount(kasAmount, BigDecimal.ZERO));
            } else {
                listKasAmount.add(new KasAmount(BigDecimal.ZERO, kasAmount));
            }

        }
    }

    public RangkumanTransaksi(Object[] data) {
        this(
                (String) data[0],
                ((Date) data[1]).toLocalDate(),
                (String) data[2],
                (String) data[3],
                (BigDecimal) data[4],
                Arrays.copyOfRange(data, 5, data.length, BigDecimal[].class)
        );
    }

    public List<KasAmount> getListKasAmount() {
        return listKasAmount;
    }

    public void setTrxDate(LocalDate trxDate) {
        this.trxDate = trxDate;
    }

    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    public void setTrxDocId(String trxDocId) {
        this.trxDocId = trxDocId;
    }

    public void setAmountDebit(BigDecimal amountDebit) {
        this.amountDebit = amountDebit;
    }

    public void setAmountCredit(BigDecimal amountCredit) {
        this.amountCredit = amountCredit;
    }

    public void setListKasAmount(List<KasAmount> listKasAmount) {
        this.listKasAmount = listKasAmount;
    }

    public String getId() {
        return id;
    }

    public String getTrxDate() {
        if (trxDate == null) {
            return "";
        }
        return trxDate.format(java.time.format.DateTimeFormatter.ofPattern("dd"));
    }

    public String getUraian() {
        return uraian;
    }

    public String getTrxDocId() {
        return trxDocId;
    }

    public BigDecimal getAmountDebit() {
        return amountDebit;
    }

    public BigDecimal getAmountCredit() {
        return amountCredit;
    }

    public BigDecimal getKasNDebit(int n) {
        return listKasAmount.get(n).getDebit();
    }

    public BigDecimal getKasNCredit(int n) {
        return listKasAmount.get(n).getCredit();
    }

    @Override
    public String toString() {
        return id;
    }

}
