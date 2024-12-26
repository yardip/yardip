/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.common.service;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.common.model.IntegerSerialSeed;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Singleton
public class IntegerSerialGeneratorFacade extends AbstractFacade<IntegerSerialSeed> {

    @Inject
    private EntityManager em;

    public IntegerSerialGeneratorFacade() {
        super(IntegerSerialSeed.class);
    }

    protected IntegerSerialSeed getGenerator(String header, String zeroSerial) {
        IntegerSerialSeed serialGenerator = find(header);
        
        if(serialGenerator == null) {
            serialGenerator = new IntegerSerialSeed();
            serialGenerator.setId(header);
            serialGenerator.setZeroSerial(zeroSerial);
            serialGenerator.setLastSerial(0);
            create(serialGenerator);
        }
        
        return serialGenerator;
    }
    
    public String getNext(Class cls, String zeroSerial) {
        return getNext(cls.getSimpleName(), zeroSerial);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Lock(LockType.WRITE)
    public String getNext(String generatorName, String zeroSerial) {
        IntegerSerialSeed serialSeed = getGenerator(generatorName, zeroSerial);
        serialSeed.setLastSerial(serialSeed.getLastSerial() + 1);
        edit(serialSeed);
        
        String seed = String.valueOf(serialSeed.getLastSerial());
        
        String serial = serialSeed.getZeroSerial().concat(seed).substring(seed.length());
        
        return serial;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
