/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.common.model;

import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@MappedSuperclass
public abstract class SerialSeed<T> implements Serializable {

    public abstract String getZeroSerial();

    public abstract T getLastSerial();
}
