/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.util;

/**
 *
 * @author aphasan
 */
public final class QueryParameter {
    
    private final String name;
    
    private final Object value;

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public QueryParameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
