/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.exception;

/**
 *
 * @author aphasan
 */
public class ReportCompilationException extends Exception {

    public ReportCompilationException() {
    }

    public ReportCompilationException(String message) {
        super(message);
    }

    public ReportCompilationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportCompilationException(Throwable cause) {
        super(cause);
    }

    public ReportCompilationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
