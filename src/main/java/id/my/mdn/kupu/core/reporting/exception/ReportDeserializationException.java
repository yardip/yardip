/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.exception;

/**
 *
 * @author aphasan
 */
public class ReportDeserializationException extends Exception {

    public ReportDeserializationException() {
    }

    public ReportDeserializationException(String message) {
        super(message);
    }

    public ReportDeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportDeserializationException(Throwable cause) {
        super(cause);
    }

    public ReportDeserializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
