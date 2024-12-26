/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.model;

/**
 *
 * @author aphasan
 * @param <T>
 */
public abstract class EntityBuilder<T> {

    protected final T entity;

    public EntityBuilder(T entity) {
        this.entity = entity;
    }

    public T get() {
        return entity;
    }
}
