
package id.my.mdn.kupu.core.base.view.widget;

import id.my.mdn.kupu.core.base.util.FilterTypes.FilterListener;

/**
 *
 * @author aphasan
 */
public interface IFilterable {
    
    void doFilter();
    
    void clearFilter();
    
    void addFilterListener(FilterListener filterListener);
    
}
