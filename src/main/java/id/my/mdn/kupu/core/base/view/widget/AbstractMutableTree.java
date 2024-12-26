/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.view.widget;

import id.my.mdn.kupu.core.base.model.HierarchicalEntity;
import static id.my.mdn.kupu.core.base.view.widget.Selector.SINGLE;
import java.util.List;

/**
 *
 * @author aphasan
 * @param <E>
 */
public abstract class AbstractMutableTree<E extends HierarchicalEntity<E>> 
        extends AbstractValueTree<E> 
        implements ICreate<E>, IEdit<E>, IDelete<E> {    
    
    private PageCaller creator;
     
    private PageCaller editor;
     
    private PageCaller deleter;

    @Override
    public PageCaller getCreator() {
        return creator;
    }

    @Override
    public PageCaller getEditor() {
        return editor;
    }

    @Override
    public PageCaller getDeleter() {
        return deleter;
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

    @Override
    public void setEditor(PageCaller editor) {
        this.editor = editor;
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
    
    public void deleteSelections() {
        selector.getSelections().stream().forEach(e -> delete(e));
        selector.setSelections(null);        
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

    public String[] getCreatePermission() {
        return new String[]{};
    }

    public String[] getUpdatePermission() {
        return new String[]{};
    }

    public String[] getDeletePermission() {
        return new String[]{};
    }
    
    protected abstract void deleteInternal(E entity) ;
}
