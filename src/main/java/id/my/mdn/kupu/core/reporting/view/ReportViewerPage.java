/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.view;

import id.my.mdn.kupu.core.base.view.ChildPage;
import java.io.Serializable;
import java.util.Map;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "reportViewerPage")
@ViewScoped
public class ReportViewerPage extends ChildPage implements Serializable {
    
     
    private String template;
    
     
    private String output;
    
     
    private Map<String, Object> reportParameters;

}
