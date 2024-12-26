/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.common.service;

/**
 *
 * @author aphasan
 */
public interface SerialGenerator {

    public String getNext(String generatorName, String zeroSerial);
}
