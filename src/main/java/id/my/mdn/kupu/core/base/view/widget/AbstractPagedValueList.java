package id.my.mdn.kupu.core.base.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.Pager.PagerListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aphasan
 * @param <E>
 */
public abstract class AbstractPagedValueList<E> extends AbstractValueList<E>
implements IPageable {

    @FunctionalInterface
    public static interface DefaultCount {
        Long get();
    }

    protected Pager pager;
    
    protected DefaultCount defaultCount = () -> null;

    public AbstractPagedValueList() {
        super();
        pager = new Pager(this::onPaging);
    }

    protected abstract List<E> getPagedFetchedItemsInternal(
            int first, int pageSize,
            Map<String, Object> parameters, 
            List<FilterData> filters, 
            List<SorterData> sorters,
            DefaultList<E> defaultList,
            DefaultChecker defaultChecker);

    protected abstract long getItemsCountInternal(
            Map<String, Object> parameters, 
            List<FilterData> filters, 
            DefaultCount defaultCount, 
            DefaultChecker defaultChecker);

    @Override
    public List<E> getFetchedItems() {
        if (!isValid()) {
            fetchedItems = getFetchedItemsInternal(
                    getParameters(), 
                    filter.getValues(), 
                    getSorters(), 
                    defaultList, 
                    defaultChecker);

            pager.setItemsCount(getItemsCountInternal(getParameters(), filter.getValues(), defaultCount, defaultChecker));
            pager.updatePages();

            validate();
        }

        return fetchedItems;
    }

    @Override
    protected final List<E> getFetchedItemsInternal(
            Map<String, Object> parameters,
            List<FilterData> filters,
            List<SorterData> sorters,
            DefaultList<E> defaultList,
            DefaultChecker defaultChecker
    ) {
        return getPagedFetchedItemsInternal(
                (pager.getOffset().intValue() - 1) * pager.getPageSize().intValue(),
                pager.getPageSize().intValue(), parameters,
                filters,
                getSorters(),
                defaultList, defaultChecker);
    }

    @Override
    public Map<String, List<String>> getStates() {

        Map<String, List<String>> params = new HashMap<>();

        params.putAll(super.getStates());
        params.putAll(pager.getStates());

        return params;
    }
    
    public void onPaging() {
        invalidate();
    }

    @Override
    public void addPagerListener(PagerListener e) {
        pager.addListener(e);
    }

    @Override
    protected void resetInternal() {
        super.resetInternal();
        pager.reset();
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
    
    public void setDefaultCount(DefaultCount defaultCount) {
        this.defaultCount = defaultCount;
    }

}
