/*
 * Copyright 2015 Arief Prihasanto <ariefp5758 at gmail.com>.
 * All rights reserved

 * A lot of time, effort and money is spent designing and implementing the software.
 * All system design, text, graphics, the selection and arrangement thereof, and
 * all software compilations, underlying source code, software and all other material
 * on this software are copyright Arief Prihasanto <ariefp5758 at gmail.com> and any affiliates.
 * 
 * In simple terms, every element of this software is protected by copyright.
 * Unless you have our express written permission, you are not allowed
 * to copy partially and or completely, modify partially and or completely,
 * use partially and or completely and or reproduce any part of this  software
 * in any way, shape and or form.
 * 
 * Taking material from other source code and or document Arief Prihasanto <ariefp5758 at gmail.com> and affiliates has designed is
 * also prohibited. You can be prosecuted by the licensee as well as by us as licensor.
 * 
 * Any other use of materials of this software, including reproduction for purposes other
 * than that noted in the business agreement, modification, distribution, or republication,
 * without the prior written permission of Arief Prihasanto <ariefp5758 at gmail.com> is strictly prohibited.
 * 
 * The source code, partially and or completely, shall not be presented and or shown
 * and or performed to public and or other parties without the prior written permission
 * of Arief Prihasanto <ariefp5758 at gmail.com>

 */
package id.my.mdn.kupu.core.security.util;

import jakarta.security.enterprise.identitystore.PasswordHash;
import java.util.Random;

/**
 *
 * @author Arief Prihasanto <ariefp5758 at gmail.com>
 */
public class PasswordUtil {

    public static class PasswordHashSHA256 implements PasswordHash {

        @Override
        public String generate(char[] chars) {
            return PasswordUtil.generate(chars);
        }

        @Override
        public boolean verify(char[] chars, String hashedPassword) {
            return generate(chars).equals(hashedPassword);
        }
    }

    public static String generate(String plain) {
        return generate(plain.toCharArray());
    }

    private static String generate(char[] chars) {
        return DigestUtility.getDigest(chars, "SHA-256");
    }

    public static String generateRandomPassword() {
        char[][] chars = new char[][]{
            new char[]{'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z'},
            new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z'},
            new char[]{'0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9'},
            new char[]{'@', '#', '$', '&'}
        };

        StringBuilder sb = new StringBuilder();
        
        int lastSelector = -1;

        for (int i = 0; i < 8; i++) {
            int selector = new Random().nextInt(3);
            int position = new Random().nextInt(chars[selector].length);
            if(selector == lastSelector && selector == 0) {
                selector = 1;
            }
            if(selector == lastSelector && selector == 1) {
                selector = 0;
            }
            char element = chars[selector][position];
            sb.append(element);
            lastSelector = selector;
        }
        
        int salt = new Random().nextInt(4);
        int saltPos = new Random().nextInt(8);
        
        sb.setCharAt(saltPos, chars[3][salt]);
        
        return sb.toString();
    }
    
    public static String generateUserSuffix() {
        char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6','7', '8', '9'};
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            int position = new Random().nextInt(chars.length);
            char element = chars[position];
            sb.append(element);
        }
        
        return sb.toString();
    }
}
