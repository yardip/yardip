
package id.my.mdn.kupu.core.base.view;

import id.my.mdn.kupu.core.base.util.RequestedView;

/**
 *
 * @author aphasan
 */
public abstract class ChildPage extends Page {
    
    public void load() {}

    public void up() {
        getBackstack().pop().open();
    }

    public RequestedView lazyUp() {
        return getBackstack().pop();
    }

}
