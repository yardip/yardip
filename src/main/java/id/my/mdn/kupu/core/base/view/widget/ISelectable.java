
package id.my.mdn.kupu.core.base.view.widget;

import id.my.mdn.kupu.core.base.view.widget.Selector.SelectorListener;
import java.util.List;

/**
 *
 * @author aphasan
 * @param <E>
 */
public interface ISelectable<E> {
    
    E getSelection();
    
    void setSelection(E e);
    
    List<E> getSelections();
    
    void setSelections(List<E> e);
    
    void addSelectListener(SelectorListener selectListener);
//    
//    void notifySelectListener();
//    
//    void notifyUnselectListener();

}
