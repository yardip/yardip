/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view.widget;

import java.util.List;

/**
 *
 * @author aphasan
 * @param <T>
 */
public interface IDelete<T> {

    void delete(T entity);
    
    default void delete(List<T> entities) {
        entities.stream().forEach(e -> delete(e));
    }

    PageCaller getDeleter();

    void setDeleter(PageCaller deleter);

    default void onDelete() {
        getDeleter().open();
    }

}
