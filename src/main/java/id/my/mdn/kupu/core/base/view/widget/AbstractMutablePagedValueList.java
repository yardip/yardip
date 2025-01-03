/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.view.widget;

import static id.my.mdn.kupu.core.base.view.widget.Selector.SINGLE;
import java.util.List;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author aphasan
 * @param <E>
 */
public abstract class AbstractMutablePagedValueList<E>
        extends AbstractPagedValueList<E> implements ICreate<E>, IEdit<E>, IDelete<E> {

    private PageCaller creator;

    private PageCaller editor;

    private PageCaller deleter;

    public AbstractMutablePagedValueList() {
        super();
    }

    public E getSelected() {
        if (getSelectionMode().equals(Selector.SINGLE)) {
            return getSelection();
        } else {
            return getSelections() != null ? getSelections().getLast() : null;
        }
    }

    public void onCellEdit(CellEditEvent event) {
        int index = event.getRowIndex();
        E entity = getFetchedItems().get(index);

        if (entity != null) {

            String field = (String) event.getColumn().getExportValue();
            Object value = event.getNewValue();
            if (field != null) {
                edit(update(entity, field, value));
            }
        }
    }

    private E update(E entity, String field, Object newValue) {
        updateInternal(entity, field, newValue);
        return entity;
    }

    protected void updateInternal(E entity, String field, Object newValue) {

    }

    protected E findEntity(String id) {
        return null;
    }

    public String[] getCreatePermission() {
        return new String[]{};
    }

    public String[] getUpdatePermission() {
        return new String[]{};
    }

    public String[] getDeletePermission() {
        return new String[]{};
    }

    //<editor-fold defaultstate="collapsed" desc="ICreate implementation">
    @Override
    public PageCaller getCreator() {
        return creator;
    }

    @Override
    public void setCreator(PageCaller creator) {
        this.creator = creator;
    }

    @Override
    public void create(E entity) {
        createInternal(entity);
        if (selector.getSelectionMode().equals(SINGLE)) {
            selector.setSelection(entity);
        } else {
            selector.setSelections(List.of(entity));
        }
        invalidate();
    }

    protected abstract void createInternal(E entity);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="IEdit implementation">
    @Override
    public PageCaller getEditor() {
        return editor;
    }

    @Override
    public void setEditor(PageCaller editor) {
        this.editor = editor;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="IDelete implementation">
    @Override
    public PageCaller getDeleter() {
        return deleter;
    }

    @Override
    public void setDeleter(PageCaller deleter) {
        this.deleter = deleter;
    }

    @Override
    public void delete(E entity) {
        deleteInternal(entity);
        invalidate();
    }

    public void deleteSelected() {
        if (getSelectionMode().equals(Selector.SINGLE)) {
            deleteSelection();
        } else {
            deleteSelections();
        }
    }

    public void deleteSelection() {
        delete(selector.getSelection());
        selector.setSelection(null);

    }

    public void deleteSelections() {
        selector.getSelections().stream().forEach(e -> delete(e));
        selector.setSelections(null);

    }

    protected abstract void deleteInternal(E entity);
//</editor-fold>
}
