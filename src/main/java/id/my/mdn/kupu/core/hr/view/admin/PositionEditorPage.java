/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.hr.view.admin;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.hr.dao.PositionFacade;
import id.my.mdn.kupu.core.hr.entity.Position;
import id.my.mdn.kupu.core.party.entity.BusinessEntity;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "positionEditorPage")
@ConversationScoped
public class PositionEditorPage extends FormPage<Position> {
    
    @Inject
    private PositionFacade dao;
    
    @Bookmark
    private Position parent;
    
    @Bookmark
    private BusinessEntity entitas;

    @Override
    public void load() {
        super.load();
    }

    @Override
    protected Position newEntity() {        
        return Position.builder()
                .businessEntity(entitas)
                .withParent(parent)
                .get();
    }

    @Override
    protected Result<String> save(Position entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(Position entity) {
        return dao.edit(entity);
    }

    public Position getParent() {
        return parent;
    }

    public void setParent(Position parent) {
        this.parent = parent;
    }

    public BusinessEntity getEntitas() {
        return entitas;
    }

    public void setEntitas(BusinessEntity entitas) {
        this.entitas = entitas;
    }
    
}
