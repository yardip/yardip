/*
 * Copyright 2014 Medinacom <hq.medinacom at gmail.com>.
 * All rights reserved

 * A lot of time, effort and money is spent designing and implementing the software.
 * All system design, text, graphics, the selection and arrangement thereof, and
 * all software compilations, underlying source code, software and all other material
 * on this software are copyright Medinacom <hq.medinacom at gmail.com> and any affiliates.
 * 
 * In simple terms, every element of this software is protected by copyright.
 * Unless you have our express written permission, you are not allowed
 * to copy partially and or completely, modify partially and or completely,
 * use partially and or completely and or reproduce any part of this  software
 * in any way, shape and or form.
 * 
 * Taking material from other source code and or document Medinacom <hq.medinacom at gmail.com> and affiliates has designed is
 * also prohibited. You can be prosecuted by the licensee as well as by us as licensor.
 * 
 * Any other use of materials of this software, including reproduction for purposes other
 * than that noted in the business agreement, modification, distribution, or republication,
 * without the prior written permission of Medinacom <hq.medinacom at gmail.com> is strictly prohibited.
 * 
 * The source code, partially and or completely, shall not be presented and or shown
 * and or performed to public and or other parties without the prior written permission
 * of Medinacom <hq.medinacom at gmail.com>

 */

package id.my.mdn.kupu.core.party.entity;

import id.my.mdn.kupu.core.base.util.PageUtil;

/**
 *
 * @author Medinacom <hq.medinacom at gmail.com>
 */
public enum GenderType {
    MALE,
    FEMALE;
    
    public String getLabel() {
        switch(this) {
            case MALE:
                return PageUtil.compileMessage("GenderType.MALE");
            case FEMALE:
                return PageUtil.compileMessage("GenderType.FEMALE");
            default:
                return null;
        }  
    }
}
