/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view.widget;

/**
 *
 * @author aphasan
 * @param <T>
 */
public interface IEdit<T> {

    void edit(T entity);
    
    PageCaller getEditor();
    
    void setEditor(PageCaller editor);

    default void onEdit() {
        getEditor().open();
    }

}
