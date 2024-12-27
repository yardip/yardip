/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.accounting.entity;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public enum GLAccountType {
    
    ASSET(1, -1),
    LIABILITY(-1, 1),
    OWNERS_EQUITY(-1, 1),
    OWNERS_DRAWING(1, -1),
    REVENUE(-1, 1),
    EXPENSE(1, -1);
    
    private final int drUnit;
    private final int crUnit;
    
    private GLAccountType(int drUnit, int crUnit) {
        this.drUnit = drUnit;
        this.crUnit = crUnit;
    }

    public int getDrUnit() {
        return drUnit;
    }

    public int getCrUnit() {
        return crUnit;
    }
    
}
