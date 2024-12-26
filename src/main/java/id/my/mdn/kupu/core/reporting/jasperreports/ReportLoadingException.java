/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.jasperreports;

/**
 *
 * @author aphasan
 */
public class ReportLoadingException extends Exception {

    public ReportLoadingException() {
    }

    public ReportLoadingException(String message) {
        super(message);
    }

    public ReportLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportLoadingException(Throwable cause) {
        super(cause);
    }

    public ReportLoadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
