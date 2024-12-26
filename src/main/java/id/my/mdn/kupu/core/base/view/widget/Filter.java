package id.my.mdn.kupu.core.base.view.widget;

import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterListener;
import id.my.mdn.kupu.core.base.util.FilterTypes.StaticFilter;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.annotation.Default;
import id.my.mdn.kupu.core.base.view.util.ConverterUtil;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.convert.Converter;
import jakarta.faces.event.AjaxBehaviorEvent;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author aphasan
 */
public final class Filter implements IBookmarkable, Serializable {

    private String name = "filter";

    private boolean filtering = false;

    private String filteringLabel = "filtering";

    private FilterContent content;

    public StaticFilter staticFilter = () -> new ArrayList<>();

    private FilterListener context;

    private final List<FilterListener> filterListeners = new ArrayList<>();

    public Filter() {
    }

    public Filter(FilterContent content, FilterListener context) {
        this.content = content;
        this.context = context;
        if (content != null) {
            content.setContainer(this);
        }
    }

    public Filter(FilterContent content) {
        this(content, null);
    }

    public Filter(FilterListener context) {
        this(null, context);
    }

    @Override
    public Map<String, List<String>> getStates() {

        Map<String, List<String>> states = new HashMap<>();

        for (FilterData filterData : getBookmarkedTerms()) {

            Object value = filterData.value;

            Converter converter = ConverterUtil.findConverter(CDI.current(), value.getClass());

            List<String> stateValue = null;

            if (converter != null) {
                stateValue = Arrays.asList(converter.getAsString(null, null, value));
            } else {
                stateValue = Arrays.asList(String.valueOf(value));
            }

            states.put(filterData.name, stateValue);
        }

        if (!states.isEmpty()) {
            states.put(name, Arrays.asList(Boolean.toString(filtering)));
        }

        return states;
    }

    public List<FilterData> getValues() {

        List<FilterData> filters = getContentValues();

        filters.addAll(staticFilter.get());

        return filters;
    }

    private Object getContentFieldValue(Field contentField) {
        Object contentFieldValue;

        try {
            contentField.setAccessible(true);
            contentFieldValue = contentField.get(content);

        } catch (IllegalAccessException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        if (contentFieldValue == null) {

            Default defaultAnnotation = contentField.getAnnotation(Default.class);

            if (defaultAnnotation == null || defaultAnnotation.value().isEmpty()) {
                return null;
            }

            Converter converter = ConverterUtil.findConverter(CDI.current(), contentField.getType());

            if (converter == null) {
                return null;
            }

            contentFieldValue = converter.getAsObject(null, null, defaultAnnotation.value());

        } else if (contentFieldValue instanceof String && ((String) contentFieldValue).isEmpty()) {
            return null;
        }

        return contentFieldValue;
    }

    private List<FilterData> getContentValues() {

        List<FilterData> filters = new ArrayList<>();

        if (content == null) {
            return filters;
        }

        for (Field contentField : content.getClass().getDeclaredFields()) {
            try {
                contentField.setAccessible(true);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
            }

            Object contentFieldValue = getContentFieldValue(contentField);

            Bookmark bookmarkAnnotation = contentField.getAnnotation(Bookmark.class);
            if (bookmarkAnnotation != null) {
                boolean fieldIsNotNullable = !bookmarkAnnotation.nullable();
                if (fieldIsNotNullable && contentFieldValue == null) {
                    continue;
                }
            }

            String contentFieldName = contentField.getName();

            filters.add(FilterData.by(contentFieldName, contentFieldValue));

        }

        return filters;
    }

    private List<FilterData> getBookmarkedTerms() {

        List<FilterData> filters = new ArrayList<>();

        if (content == null) {
            return filters;
        }

        for (Field contentField : content.getClass().getDeclaredFields()) {
            contentField.setAccessible(true);

            Object contentFieldValue = getContentFieldValue(contentField);

            if (contentFieldValue == null) {
                continue;
            }

            String contentFieldName = contentField.getName();

            Bookmark bookmarkAnnotation = contentField.getAnnotation(Bookmark.class);
            String filterName = bookmarkAnnotation != null ? bookmarkAnnotation.name() : contentFieldName;

            filters.add(FilterData.by(filterName, contentFieldValue));

        }

        return filters;
    }

    public void addListener(FilterListener filterListener) {
        filterListeners.add(filterListener);
    }

    public void notifyListener() {
        filterListeners.stream().forEach(listener -> listener.onFilter(null));
    }

    public void clear() {
        if (content == null) {
            return;
        }
        Stream.of(content.getClass().getDeclaredFields()).peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    try {
                        field.set(content, null);
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
    }

    public void setStaticFilter(StaticFilter staticFilter) {
        this.staticFilter = staticFilter;
    }

    public void setStaticFilter(FilterData... staticFilters) {
        this.staticFilter = () -> List.of(staticFilters);
    }

    public void doUnfilter() {
        clear();
        setFiltering(false);
        notifyListener();
        if (context != null) {
            context.onFilter(null);
        }
    }

    public void doFilter() {
        setFiltering(true);
        notifyListener();
        if (context != null) {
            context.onFilter(null);
        }
    }

    public void doFilter(AjaxBehaviorEvent evt) {
        doFilter();
    }

    public boolean isFiltering() {
        return filtering;
    }

    public void setFiltering(boolean filtering) {
        this.filtering = filtering;
    }

    public String getFilteringLabel() {
        return filteringLabel;
    }

    public void setFilteringLabel(String filteringLabel) {
        this.filteringLabel = filteringLabel;
    }

    public <T extends FilterContent> T getContent() {
        return (T) content;
    }

    public void setContent(FilterContent content) {
        this.content = content;
        if (content != null) {
            content.setContainer(this);
        }
    }

    public void onContentChanged(Object content) {
        context.onFilter(content);
    }

    public void setContext(FilterListener context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
