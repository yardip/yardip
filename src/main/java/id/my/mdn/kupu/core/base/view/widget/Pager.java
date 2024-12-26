package id.my.mdn.kupu.core.base.view.widget;

import jakarta.faces.event.AjaxBehaviorEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author aphasan
 */
public class Pager implements IBookmarkable, Serializable {

    private static final Logger LOG = Logger.getLogger(Pager.class.getCanonicalName());

    @FunctionalInterface
    public static interface PagerListener {

        void onPaging();
    }

    private Long itemsCount;

    private Long pageSize = 20L;  // Default page size

    private Long offset = 1L;   // On first page

    private Long pages = 0L;

    private Boolean forward = true;

    private PagerListener context;

    private final List<PagerListener> pagerListeners = new ArrayList<>();

    private List<Long> pageSizeOptions = List.of(1L, 10L,
            20L, 30L, 40L, 50L, 60L, 70L, 80L, 90L, 100L);   
    
    private String name = "pager";

    private String pageSizeLabel = "psz";

    private String offsetLabel = "ofs";

    public Pager() {
    }

    public Pager(PagerListener context) {
        this.context = context;
    }

    public void updatePages() {
        if(itemsCount == null) pages = 0L;
        else pages = itemsCount / pageSize + ((itemsCount % pageSize) > 0 ? 1 : 0);
    }

    public void onPaging(AjaxBehaviorEvent evt) {
        notifyListener();
        if (context != null) {
            context.onPaging();
        }
    }

    public void next() {
        if (!isAtEnd()) {
            offset = offset + 1L;
            onPaging(null);
        }
    }

    public void previous() {
        if (!isAtStart()) {
            offset = offset - 1L;
            onPaging(null);
        }
    }

    public void first() {
        offset = 1L;
        onPaging(null);
    }

    public void last() {
        offset = pages;
        onPaging(null);
    }

    public void reset() {
        offset = 1L;
        updatePages();
        onPaging(null);
    }

    public boolean isAtStart() {
        return offset.equals(1L);
    }

    public boolean isAtEnd() {
        return offset.equals(pages);
    }

    @Override
    public Map<String, List<String>> getStates() {

        Map<String, List<String>> params = new HashMap<>();

        params.put(pageSizeLabel, Arrays.asList(String.valueOf(pageSize)));

        params.put(offsetLabel, Arrays.asList(String.valueOf(offset)));

        return params;
    }

    public void addListener(PagerListener e) {
        this.pagerListeners.add(e);
    }

    private void notifyListener() {
        pagerListeners.stream().forEach(PagerListener::onPaging);
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public List<Long> getPageSizeOptions() {
        return pageSizeOptions;
    }

    public void setPageSizeOptions(List<Long> pageSizeOptions) {
        this.pageSizeOptions = pageSizeOptions;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
    }

    public List<Long> getPageOptions() {
        final List<Long> pageOtions = new ArrayList<>();

        for (long i = 1; i <= pages; i++) {
            pageOtions.add(i);
        }

        return pageOtions;
    }

    public Long getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Long itemsCount) {
        this.itemsCount = itemsCount;
    }

    public void setContext(PagerListener context) {
        this.context = context;
    }

    public String getPageSizeLabel() {
        return pageSizeLabel;
    }

    public void setPageSizeLabel(String pageSizeLabel) {
        this.pageSizeLabel = pageSizeLabel;
    }

    public String getOffsetLabel() {
        return offsetLabel;
    }

    public void setOffsetLabel(String offsetLabel) {
        this.offsetLabel = offsetLabel;
    }

    public Boolean getForward() {
        return forward;
    }

    public void setForward(Boolean forward) {
        this.forward = forward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
