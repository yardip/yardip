/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.util;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 * @param <Boolean>
 * @param <R>
 */
public final class Result<R> {
    public Boolean success;    
    public R payload;    

    public Result(Boolean success, R payload) {
        this.success = success;
        this.payload = payload;
    }
    
    public void copy(Result<R> source) {
        this.success = source.success;
        this.payload = source.payload;
    }
    
    public boolean isSuccess() {
        return success.equals(Boolean.TRUE);
    }
    
    public boolean isNotSuccess() {
        return !success.equals(Boolean.TRUE);
    }
    
}
