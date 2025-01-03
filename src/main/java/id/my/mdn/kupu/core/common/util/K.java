/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public final class K {

    public static final class KLong {

        public static final Long valueOf(String value) {
            if (StringUtils.isNumeric(value)) {
                return Long.valueOf(value);
            } else {
                return null;
            }
        }
    }

    public static final class KInteger {

        public static final Integer valueOf(String value) {
            if (StringUtils.isNumeric(value)) {
                return Integer.valueOf(value);
            } else {
                return null;
            }
        }
    }

    public static final class KBoolean {

        public static final Boolean valueOf(String value) {
            return Boolean.valueOf(value);
        }
    }

    public static final class KEnum {

        public static final <K extends Enum<K>> K valueOf(Class<K> enumClass, String v) {
            try {
                return K.valueOf(enumClass, v);
            } catch (Exception ex) {
                return null;
            }
        }
    }
}
