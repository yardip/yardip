package id.my.mdn.kupu.core.base.view.widget;

import java.io.Serializable;

/**
 *
 * @author aphasan
 */
public abstract class FilterContent implements Serializable {
    
    private Filter context;

    public void setContainer(Filter container) {
        this.context = container;
    }

    protected final void notifyContext(Object content) {
        context.onContentChanged(content);
    }
}
