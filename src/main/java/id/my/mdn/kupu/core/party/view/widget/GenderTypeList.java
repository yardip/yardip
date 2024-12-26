/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.view.widget;

import id.my.mdn.kupu.core.base.view.widget.IValueList;
import id.my.mdn.kupu.core.party.entity.GenderType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "genderTypeList")
@Dependent
public class GenderTypeList implements IValueList<GenderType> {

    @Override
    public List<GenderType> getFetchedItems() {
        return Arrays.asList(GenderType.values());
    }
    
    
}
