/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.security.util;

import java.util.Base64;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class EncryptUtil {
    
    public static String encrypt(String string) {
        String base64 = Base64.getEncoder().encodeToString(string.getBytes());
        return base64;
    }
    
}
