/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author aphasan
 */
public final class EntityUtil {

    public static String[] parseCompositeId(String compositeId, String separator) {
        return compositeId.split(separator);
    }

    public static String[] parseCompositeId(String compositeId) {
        return parseCompositeId(compositeId, "_");
    }

    public static String createStringId(char separator, Object... components) {
        StringBuilder builder = new StringBuilder();
        int i = components.length - 1;
        for (Object component : components) {
            String key = component != null ? component.toString() : "0";
            builder.append(key);
            if (i > 0) {
                builder.append(separator);
            }
            i--;
        }
        return builder.toString();
    }
    
    public static String createStringId(Object... components) {
        return createStringId('_', components);
    }
    
    public static String stringKeyFromLocalDate(LocalDate localDate, String pattern) {
        if(localDate == null) return null;
        DateTimeFormatter LOCALDATE_KEY_FORMATTER = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(LOCALDATE_KEY_FORMATTER);
    }
    
    public static LocalDate stringKeyToLocalDate(String key, String pattern) {
        DateTimeFormatter LOCALDATE_KEY_FORMATTER = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(key, LOCALDATE_KEY_FORMATTER);
    }
    
    public static String stringKeyFromLocalDateTime(LocalDateTime localDateTime, String pattern) {
        if(localDateTime == null) return null;
        DateTimeFormatter LOCALDATETIME_KEY_FORMATTER = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(LOCALDATETIME_KEY_FORMATTER);
    }
    
    public static LocalDateTime stringKeyToLocalDateTime(String key, String pattern) {
        DateTimeFormatter LOCALDATETIME_KEY_FORMATTER = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(key, LOCALDATETIME_KEY_FORMATTER);
    }

}
