/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view;

import id.my.mdn.kupu.core.base.util.RequestedView;
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
public abstract class SingleChooserPage<E> extends ChildPage {

    private int what;

    protected abstract E returns();

    public void finish() {

        Converter converter = ConverterUtil.findConverter(CDI.current(), getConverter().getSimpleName());

        E choosen = returns();

        if (choosen == null) {
            up();
        }

        String ret = converter.getAsString(null, null, choosen);

        RequestedView parent = getBackstack().pop();

        if (ret != null) {
            parent.addParam("h").withValues(Base64.getEncoder().encodeToString(parent.toString().getBytes()))
                    .addParam("r").withValues(ret)
                    .addParam("w").withValues(what)
                    .addParam("c").withValues(getConverter().getSimpleName());
        }

        parent.open();
    }

    protected abstract Class<? extends Converter<E>> getConverter();

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
