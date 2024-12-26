package id.my.mdn.kupu.core.base.view.widget;

import id.my.mdn.kupu.core.base.util.RequestUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aphasan
 * @param <E>
 */
public class Selector<E> implements IBookmarkable, Serializable {

    public static final String SINGLE = "single";
    public static final String MULTIPLE = "multiple";
    public static final String CHECKBOX = "checkbox";

    public static interface SelectorListener {

        void onSelect(Object selected);
    }

    @FunctionalInterface
    public static interface SelectionModeSelector {

        String apply();
    }

    private String name;

    private String selectionsLabel = "s";

    private List<E> selections;

    private E selection;

//    private String selectionMode = MULTIPLE;
    private SelectionModeSelector selectionMode = () -> MULTIPLE;

    protected final List<SelectorListener> selectorListeners = new ArrayList<>();

    protected final List<SelectorListener> selectorInternalListeners = new ArrayList<>();

    public void setSelections(List<E> selections) {
        setSelectionsInternal(selections);
        notifySelectListener(selections);
    }

    public void setSelectionsInternal(List<E> selections) {
        this.selections = selections;
        notifySelectInternalListener(selections);
    }

    public List<E> getSelections() {
        return getSelectionsInternal();
    }

    public List<E> getSelectionsInternal() {
        return selections;
    }

    public void setSelection(E selection) {
        setSelectionInternal(selection);
        notifySelectListener(selection);
    }

    public void setSelectionInternal(E selection) {
        this.selection = selection;
        notifySelectInternalListener(selection);
    }

    public E getSelection() {
        return getSelectionInternal();
    }

    public E getSelectionInternal() {
        return selection;
    }

    public boolean addSelection(E e) {
        if (!getSelections().contains(e)) {
            getSelections().add(e);
            return true;
        }

        return false;
    }

    public void removeSelection(E e) {
        getSelections().remove(e);
    }

    public void clearListener() {
        if (selectorListeners != null) {
            selectorListeners.clear();
        }
    }

    public void addListener(SelectorListener listener) {
        selectorListeners.add(listener);
    }

    public void addListenerInternal(SelectorListener listener) {
        selectorInternalListeners.add(listener);
    }

    public void notifySelectListener(Object selected) {
        if (selectorListeners != null) {
            selectorListeners.stream().forEach(it -> it.onSelect(selected));
        }
    }

    public void notifySelectInternalListener(Object selected) {
        if (selectorInternalListeners != null) {
            selectorInternalListeners.stream().forEach(it -> it.onSelect(selected));
        }
    }

    public boolean isOnSelection() {
        switch (selectionMode.apply()) {
            case SINGLE:
                return selection != null;
            default:
                return selections != null && !selections.isEmpty();
        }
    }

    @Override
    public Map<String, List<String>> getStates() {

        Map<String, List<String>> states = new HashMap<>();

        switch (selectionMode.apply()) {

            case MULTIPLE:
            case CHECKBOX:
                if (getSelections() != null && !getSelections().isEmpty()) {

                    String encodedString = RequestUtil.encode(getSelections());

                    if (encodedString != null) {
                        states.put(selectionsLabel, Arrays.asList(encodedString));
                    }

                }
                break;
            case SINGLE:
                if (getSelection() != null) {
                    states.put(selectionsLabel, Arrays.asList(getSelection().toString()));
                }
                break;
            default:
                break;
        }

        return states;
    }

    public void clear() {
        if (selectionMode.apply().equals(SINGLE)) {
            setSelectionInternal(null);
        } else {
            setSelectionsInternal(null);
        }
    }

    public String getSelectionsLabel() {
        return selectionsLabel;
    }

    public void setSelectionsLabel(String selectionsLabel) {
        this.selectionsLabel = selectionsLabel;
    }

    public void setSelectionMode(SelectionModeSelector selectionMode) {
        this.selectionMode = selectionMode;
    }

    public String getSelectionMode() {
        return selectionMode.apply();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
