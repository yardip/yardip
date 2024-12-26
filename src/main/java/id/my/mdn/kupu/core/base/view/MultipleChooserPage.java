/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view;

import id.my.mdn.kupu.core.base.util.RequestedView;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.base.view.util.ConverterUtil;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.convert.Converter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aphasan
 * @param <E>
 */
public abstract class MultipleChooserPage<E> extends ChildPage {

    private int what;

    protected abstract List<E> returns();

    public void finish() {

        Converter converter = ConverterUtil.findConverter(CDI.current(), getConverter().getSimpleName());

        List<E> choosens = returns();

        if (choosens == null) {
            up();
        }

        String returns = converter.getAsString(null, null, choosens);

        RequestedView parent = getBackstack().pop();

        if (returns != null) {
            parent.addParam("h").withValues(Base64.getEncoder().encodeToString(parent.toString().getBytes()))
                    .addParam("r").withValues(returns)
                    .addParam("w").withValues(what)
                    .addParam("c").withValues(getConverter().getSimpleName());
        }

        parent.open();
    }

    protected abstract Class<? extends SelectionsConverter<E>> getConverter();

    @Override
    public Map<String, List<String>> getStates() {
        Map<String, List<String>> states = super.getStates();
        states.put("what", Arrays.asList(Integer.toString(getWhat())));
        return states;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }
}
