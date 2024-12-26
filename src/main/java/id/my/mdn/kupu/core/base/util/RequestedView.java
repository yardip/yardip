package id.my.mdn.kupu.core.base.util;

import id.my.mdn.kupu.core.base.view.Page;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author aphasan
 */
public class RequestedView {

    private final String view;

    private final Map<String, List<String>> params;

    private String currentKey;

    public RequestedView(String view) {
        this.view = view;
        this.params = new HashMap<>();
    }

    public RequestedView(Class<? extends Page> clsPage) {
        this.view = ModuleUtil.getPage(clsPage);
        this.params = new HashMap<>();
    }

    public RequestedView addParam(String key) {
        currentKey = key;
        return this;
    }

//    public RequestedView withValues(String... values) {
//        params.put(currentKey, Arrays.asList(values));
//        return this;
//    }
    public RequestedView withValues(Object... values) {
        List<String> paramValue = Stream.of(values)
                .filter(value -> value != null)
                .map(Object::toString)
                .collect(toList());
        if (!paramValue.isEmpty()) {
            params.put(currentKey, paramValue);
        }
        return this;
    }

    public void open() {
        RequestUtil.goToView(this);
    }

    @Override
    public String toString() {        
        return RequestUtil.encode(getView(), getParams());
    }

    public String getView() {
        return view;
    }

    public String getCurrentKey() {
        return currentKey;
    }

    public Map<String, List<String>> getParams() {
        return params;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.view);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RequestedView other = (RequestedView) obj;
        return Objects.equals(this.toString(), other.toString());
    }

}
