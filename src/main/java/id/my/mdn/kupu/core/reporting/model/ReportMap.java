/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;



/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "REPORTING_REPORTMAP")
public class ReportMap implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name = "Reporting_ReportMap", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Reporting_ReportMap", strategy = GenerationType.TABLE)
     
    private Long id;
    
     
    private String moduleName;
    
     
    private String name;
    
     
    private ReportTemplate reportTemplate;

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
}
