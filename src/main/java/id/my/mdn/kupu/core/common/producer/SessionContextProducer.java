/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.common.producer;

import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;

/**
 *
 * @author Medina Computama <medina.computama@gmail.com>
 */
@Dependent
public class SessionContextProducer {
    
    @Produces
    @Resource
    private SessionContext ctx;
    
}
