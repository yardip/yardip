/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.report;

import java.math.BigDecimal;
import java.math.MathContext;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class ProgramKerjaScriptlet extends JRDefaultScriptlet {

    @Override
    public void afterDetailEval() throws JRScriptletException {
        super.afterDetailEval();
        
        BigDecimal totalTarget = (BigDecimal) getVariableValue("totalTarget");
        BigDecimal totalRealisasi = (BigDecimal) getVariableValue("totalRealisasi");
        BigDecimal totalCapaian = totalRealisasi.divide(totalTarget, new MathContext(5))
                .multiply(new BigDecimal(100)).round(new MathContext(3));
        setVariableValue("totalCapaian", totalCapaian);
    }
    
}
