/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.report;

import id.my.mdn.kupu.core.accounting.util.NumberSpeller;
import java.math.BigDecimal;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class BukuKasScriptlet extends JRDefaultScriptlet {

    @Override
    public void afterDetailEval() throws JRScriptletException {
        super.afterDetailEval();
        BigDecimal totalAmountDebit = (BigDecimal) getVariableValue("totalAmountDebit");
        BigDecimal totalAmountKredit = (BigDecimal) getVariableValue("totalAmountKredit");
        
        BigDecimal numTerbilang = totalAmountDebit.add(totalAmountKredit);
        String[] parsedTerbilang = NumberSpeller.parseMoney(numTerbilang);
        String terbilang = (parsedTerbilang[0].isBlank() ? "" : parsedTerbilang[0] + " RUPIAH") + (parsedTerbilang[1].isBlank() ? "" : (" " + parsedTerbilang[1] + " SEN"));
        setVariableValue("terbilang", terbilang);
    }
    
}
