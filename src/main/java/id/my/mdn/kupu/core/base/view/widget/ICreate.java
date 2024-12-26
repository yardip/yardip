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
public interface ICreate<T> {

    void create(T entity);

    PageCaller getCreator();

    void setCreator(PageCaller creator);

    default void onCreate() {
        getCreator().open();
    }

}
